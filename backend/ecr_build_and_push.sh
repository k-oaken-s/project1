#!/bin/bash

# 必要な環境変数
AWS_ACCOUNT_ID="020873189351"
REGION="ap-northeast-1"
REPOSITORY_NAME="backend"
TAG="latest"

# AWS ECR ログイン
echo "Logging into AWS ECR..."
aws ecr get-login-password --region $REGION | docker login --username AWS --password-stdin "$AWS_ACCOUNT_ID.dkr.ecr.$REGION.amazonaws.com"

# Docker イメージのビルド
echo "Building Docker image..."
docker build -t "$REPOSITORY_NAME:$TAG" ./backend

# タグ付け
echo "Tagging the image for ECR..."
docker tag "$REPOSITORY_NAME:$TAG" "$AWS_ACCOUNT_ID.dkr.ecr.$REGION.amazonaws.com/$REPOSITORY_NAME:$TAG"

# ECR にプッシュ
echo "Pushing the image to ECR..."
docker push "$AWS_ACCOUNT_ID.dkr.ecr.$REGION.amazonaws.com/$REPOSITORY_NAME:$TAG"

echo "Docker image pushed to ECR successfully!"
