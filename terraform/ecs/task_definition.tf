resource "aws_ecs_task_definition" "main" {
    family                   = var.service_name
    container_definitions    = jsonencode([
        {
            name      = "app"
            image     = var.container_image
            cpu       = var.cpu
            memory    = var.memory
            essential = true
            portMappings = [
                {
                    containerPort = 80
                    hostPort      = 80
                    protocol      = "tcp"
                }
            ]
        }
    ])
    requires_compatibilities = ["FARGATE"]
    network_mode             = "awsvpc"
    memory                   = var.memory
    cpu                      = var.cpu
    execution_role_arn       = aws_iam_role.execution_role.arn
    task_role_arn            = aws_iam_role.execution_role.arn
}
