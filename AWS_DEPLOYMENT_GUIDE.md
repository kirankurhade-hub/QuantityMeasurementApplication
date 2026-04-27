# AWS Deployment Guide - Quantity Measurement App

## Architecture Overview

```
User Browser
    |
    v
+-------------------+        +-------------------+        +-------------------+
|  S3 + CloudFront  | -----> |  Elastic Beanstalk| -----> |    AWS RDS        |
|  (React Frontend) |        |  (Spring Backend) |        |    (MySQL)        |
+-------------------+        +-------------------+        +-------------------+
```

- **Frontend**: React app hosted on S3 + CloudFront (static hosting, CDN)
- **Backend**: Spring Boot API on Elastic Beanstalk (auto-managed EC2)
- **Database**: AWS RDS MySQL (already configured: database-1.c6fy08wce2qz.us-east-1.rds.amazonaws.com)

---

## STEP 1: Deploy Backend to Elastic Beanstalk

### 1.1 Prerequisites
- Install AWS CLI: https://aws.amazon.com/cli/
- Install EB CLI: `pip install awsebcli`
- Run `aws configure` and enter your AWS Access Key ID, Secret Access Key, region (us-east-1)

### 1.2 Build the JAR

```bash
# From project root
mvn clean package -DskipTests
```

This creates `target/QuantityMeasurementApp-0.0.1-SNAPSHOT.jar`

### 1.3 Initialize Elastic Beanstalk

```bash
# From project root
eb init -p "Java 17" quantity-measurement-app --region us-east-1
```

### 1.4 Create Environment

```bash
eb create quantity-measurement-backend --single --instance-type t3.small
```

### 1.5 Set Environment Variables

Run these commands one by one:

```bash
eb setenv SPRING_PROFILES_ACTIVE=production
eb setenv RDS_HOSTNAME=database-1.c6fy08wce2qz.us-east-1.rds.amazonaws.com
eb setenv RDS_PORT=3306
eb setenv RDS_DB_NAME=QMA
eb setenv RDS_USERNAME=admin
eb setenv RDS_PASSWORD=admin123456
eb setenv CORS_ALLOWED_ORIGINS=http://localhost:3000
eb setenv FRONTEND_URL=http://localhost:3000
eb setenv JWT_SECRET=dGhpc0lzQVlTY3JlZXRLZXlGb3JKV1RUb2tlbkdlbmVyYXRpb25BbmRWYWxpZGF0aW9u
eb setenv GOOGLE_CLIENT_ID=YOUR_GOOGLE_CLIENT_ID
eb setenv GOOGLE_CLIENT_SECRET=YOUR_GOOGLE_CLIENT_SECRET
```

### 1.6 Deploy

```bash
eb deploy
```

### 1.7 Get Backend URL

```bash
eb status
```

Copy the **CNAME** value, e.g.:
```
CNAME: quantity-measurement-backend.eba-abc123.us-east-1.elasticbeanstalk.com
```

Your backend API is now at: `https://quantity-measurement-backend.eba-abc123.us-east-1.elasticbeanstalk.com`

Test it:
```bash
curl https://quantity-measurement-backend.eba-abc123.us-east-1.elasticbeanstalk.com/actuator/health
```

---

## STEP 2: Update CORS with Actual Backend URL

Now update the environment variables with the real frontend URL (S3/CloudFront URL you'll get in Step 3):

```bash
eb setenv CORS_ALLOWED_ORIGINS=https://YOUR_CLOUDFRONT_URL,http://localhost:3000
eb setenv FRONTEND_URL=https://YOUR_CLOUDFRONT_URL
eb deploy
```

---

## STEP 3: Deploy Frontend to S3 + CloudFront

### 3.1 Update Frontend API URL

Edit `frontend/.env.production`:
```
VITE_API_BASE_URL=https://quantity-measurement-backend.eba-abc123.us-east-1.elasticbeanstalk.com/api
```

Replace the URL with your actual EB backend URL from Step 1.7.

### 3.2 Build Frontend

```bash
cd frontend
npm install
npm run build
```

This creates the `frontend/dist/` folder.

### 3.3 Create S3 Bucket

1. Go to AWS Console → S3 → Create bucket
2. Bucket name: `quantity-measurement-app` (must be globally unique)
3. Region: `us-east-1`
4. **Uncheck** "Block all public access"
5. Click "Create bucket"

### 3.4 Enable Static Website Hosting

1. Open the bucket → Properties tab
2. Scroll to "Static website hosting" → Click "Edit"
3. Enable it
4. Index document: `index.html`
5. Error document: `index.html` (for SPA routing)
6. Save changes

### 3.5 Set Bucket Policy

Go to Permissions → Bucket policy → Edit, paste:

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "PublicReadGetObject",
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::quantity-measurement-app/*"
        }
    ]
}
```

Replace `quantity-measurement-app` with your actual bucket name.

### 3.6 Upload Frontend Build

Option A - AWS CLI:
```bash
aws s3 sync frontend/dist/ s3://quantity-measurement-app --delete
```

Option B - AWS Console:
1. Open bucket → Objects tab
2. Upload → Add all files from `frontend/dist/`
3. Upload

### 3.7 Set Up CloudFront (Recommended for HTTPS + CDN)

1. Go to AWS Console → CloudFront → Create distribution
2. Origin domain: Select your S3 bucket website endpoint (e.g., `quantity-measurement-app.s3-website-us-east-1.amazonaws.com`)
3. Origin access: "Public"
4. Viewer protocol: "Redirect HTTP to HTTPS"
5. Default root object: `index.html`
6. **Error pages**: Add custom error response:
   - HTTP error code: `403` and `404`
   - Response page path: `/index.html`
   - Response code: `200`
   - (This is needed for React Router SPA routing)
7. Create distribution

Wait 5-10 minutes for deployment. Your CloudFront URL will be like:
```
https://d1234567890abc.cloudfront.net
```

### 3.8 Update Backend CORS with CloudFront URL

```bash
eb setenv CORS_ALLOWED_ORIGINS=https://d1234567890abc.cloudfront.net,http://localhost:3000
eb setenv FRONTEND_URL=https://d1234567890abc.cloudfront.net
eb deploy
```

### 3.9 Update Google OAuth2 Redirect URI

Go to Google Cloud Console → APIs & Services → Credentials → Your OAuth Client:
- Add authorized redirect URI: `https://YOUR_ELB_URL/oauth2/code/google`
  (e.g., `https://quantity-measurement-backend.eba-abc123.us-east-1.elasticbeanstalk.com/oauth2/code/google`)

---

## STEP 4: Share the Link

Your app is now live at: **https://d1234567890abc.cloudfront.net**

Share this CloudFront URL with anyone.

---

## Quick Reference

| Component | Service | URL Format |
|-----------|---------|------------|
| Frontend | S3 + CloudFront | `https://dxxxxxxx.cloudfront.net` |
| Backend API | Elastic Beanstalk | `https://xxx.eba-xxx.us-east-1.elasticbeanstalk.com` |
| Database | RDS MySQL | `database-1.c6fy08wce2qz.us-east-1.rds.amazonaws.com` |

## Updating After Deployment

### Update Frontend:
```bash
cd frontend
npm run build
aws s3 sync dist/ s3://quantity-measurement-app --delete
# CloudFront may cache; create invalidation if needed
aws cloudfront create-invalidation --distribution-id YOUR_DIST_ID --paths "/*"
```

### Update Backend:
```bash
mvn clean package -DskipTests
eb deploy
```

## Cost Estimate (Free Tier)

- **Elastic Beanstalk (t3.micro)**: ~$8-15/month (or free tier for 750hrs/month for 12 months)
- **S3**: ~$1-2/month for static hosting
- **CloudFront**: Free tier includes 1TB/month transfer
- **RDS**: You already have this

**Total estimated cost**: $0-15/month with free tier

## Troubleshooting

- **CORS errors**: Make sure `CORS_ALLOWED_ORIGINS` includes your exact CloudFront URL (no trailing slash)
- **404 on page refresh**: Ensure CloudFront error pages are configured for 403/404 → /index.html
- **OAuth2 redirect fails**: Check Google Cloud Console redirect URI matches your EB URL + `/oauth2/code/google`
- **Backend not starting**: Check logs: `eb logs` or `eb ssh` and check `/var/log/eb-engine.log`
