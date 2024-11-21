resource "aws_ecs_cluster" "main" {
    name = var.cluster_name
}

resource "aws_ecs_service" "main" {
    name            = var.service_name
    cluster         = aws_ecs_cluster.main.id
    task_definition = aws_ecs_task_definition.main.arn
    desired_count   = var.desired_count

    load_balancer {
        target_group_arn = aws_lb_target_group.main.arn
        container_name   = "app"
        container_port   = 80
    }

    launch_type = "FARGATE"
    network_configuration {
        subnets         = var.subnets
        security_groups = [aws_security_group.main.id]
    }
}
