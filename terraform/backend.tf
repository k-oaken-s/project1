terraform {
    backend "s3" {
        bucket         = "koakens-terraform-state-bucket"
        key            = "ecs/terraform.tfstate"
        region         = "ap-northeast-1"
        dynamodb_table = "your-lock-table"
    }
}

