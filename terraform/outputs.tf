output "ecs_cluster_id" {
    description = "The ECS cluster ID"
    value       = module.ecs.cluster_id
}

output "ecs_service_name" {
    value = module.ecs.services["ecsdemo-frontend"].name
}


output "task_definition_arn" {
    description = "The ARN of the ECS task definition"
    value       = module.ecs.task_exec_iam_role_arn
}
