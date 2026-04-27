#!/bin/bash
# Build script for frontend - creates production build
# Run this from the frontend directory

echo "Building frontend for production..."

# Set the API URL (replace with your actual backend URL after deploying backend)
export VITE_API_BASE_URL="${VITE_API_BASE_URL:-https://YOUR_BACKEND_URL/api}"

npm install
npm run build

echo ""
echo "Build complete! Production files are at:"
echo "  frontend/dist/"
echo ""
echo "Upload the contents of frontend/dist/ to your AWS S3 bucket."
