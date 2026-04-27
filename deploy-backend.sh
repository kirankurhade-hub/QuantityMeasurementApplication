#!/bin/bash
# Build script for backend - creates deployable JAR
# Run this from the project root directory

echo "Building backend..."
mvn clean package -DskipTests

echo ""
echo "Build complete! JAR file is at:"
echo "  target/QuantityMeasurementApp-0.0.1-SNAPSHOT.jar"
echo ""
echo "This JAR can be deployed to AWS Elastic Beanstalk."
