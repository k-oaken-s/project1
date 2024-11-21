variable "aws_region" {
  description = "The AWS region to deploy the infrastructure"
  type        = string
}

variable "cluster_name" {
  description = "The name of the ECS cluster"
  type        = string
}

variable "service_name" {
  description = "The name of the ECS service"
  type        = string
}

variable "container_image" {
  description = "The container image to deploy"
  type        = string
}

variable "cpu" {
  description = "The number of CPU units for the ECS task"
  type        = number
  default     = 256
}

variable "memory" {
  description = "The amount of memory for the ECS task"
  type        = number
  default     = 512
}

variable "desired_count" {
  description = "The number of desired tasks in the ECS service"
  type        = number
  default     = 1
}

variable "vpc_id" {
  description = "The VPC ID where the ECS service will be deployed"
  type        = string
}

variable "subnets" {
  description = "The list of subnets for the ECS service"
  type        = list(string)
}
