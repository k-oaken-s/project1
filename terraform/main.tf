provider "aws" {
    region = var.aws_region
}

module "ecs" {
    source = "terraform-aws-modules/ecs/aws"

    cluster_name = "ecs-integrated"

    cluster_configuration = {
        execute_command_configuration = {
            logging = "OVERRIDE"
            log_configuration = {
                cloud_watch_log_group_name = "/aws/ecs/aws-ec2"
            }
        }
    }

    fargate_capacity_providers = {
        FARGATE = {
            default_capacity_provider_strategy = {
                weight = 50
            }
        }
        FARGATE_SPOT = {
            default_capacity_provider_strategy = {
                weight = 50
            }
        }
    }

    services = {
        ecsdemo-frontend = {
            cpu    = 1024
            memory = 4096

            # Container definition(s)
            container_definitions = {

                fluent-bit = {
                    cpu       = 512
                    memory    = 1024
                    essential = true
                    image     = "906394416424.dkr.ecr.us-west-2.amazonaws.com/aws-for-fluent-bit:stable"
                    firelens_configuration = {
                        type = "fluentbit"
                    }
                    memory_reservation = 50
                }

                ecs-sample = {
                    cpu       = 512
                    memory    = 1024
                    essential = true
                    image     = "public.ecr.aws/aws-containers/ecsdemo-frontend:776fd50"
                    port_mappings = [
                        {
                            name          = "ecs-sample"
                            containerPort = 80
                            protocol      = "tcp"
                        }
                    ]

                    # Example image used requires access to write to root filesystem
                    readonly_root_filesystem = false

                    dependencies = [{
                        containerName = "fluent-bit"
                        condition     = "START"
                    }]

                    enable_cloudwatch_logging = false
                    log_configuration = {
                        logDriver = "awsfirelens"
                        options = {
                            Name                    = "firehose"
                            region                  = "eu-west-1"
                            delivery_stream         = "my-stream"
                            log-driver-buffer-limit = "2097152"
                        }
                    }
                    memory_reservation = 100
                }
            }

            service_connect_configuration = {
                namespace = "rankify-hub-cluster"
                service = {
                    client_alias = {
                        port     = 80
                        dns_name = "ecs-sample"
                    }
                    port_name      = "ecs-sample"
                    discovery_name = "ecs-sample"
                }
            }

            load_balancer = {
                service = {
                    target_group_arn = "arn:aws:elasticloadbalancing:ap-northeast-1:020873189351:targetgroup/ecs-rankif-rankify-hub-service/180c9be972d0c707"
                    container_name   = "ecs-sample"
                    container_port   = 80
                }
            }

            subnet_ids = ["subnet-05235cf5f5c3ae2b9", "subnet-0de85aed7ef074a4d", "subnet-00b1e29a466f21e5d"]
            security_group_rules = {
                alb_ingress_3000 = {
                    type                     = "ingress"
                    from_port                = 80
                    to_port                  = 80
                    protocol                 = "tcp"
                    description              = "Service port"
                    source_security_group_id = "sg-0065921bcb0b25ca7"
                }
                egress_all = {
                    type        = "egress"
                    from_port   = 0
                    to_port     = 0
                    protocol    = "-1"
                    cidr_blocks = ["0.0.0.0/0"]
                }
            }
        }
    }

    tags = {
        Environment = "Development"
        Project     = "Example"
    }
}

resource "aws_security_group" "ecs" {
    name        = "${var.service_name}-security-group"
    description = "Allow HTTP traffic for ECS"
    vpc_id      = var.vpc_id

    ingress {
        from_port   = 80
        to_port     = 80
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    egress {
        from_port   = 0
        to_port     = 0
        protocol    = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
}
