locals {
  name_prefix = "${var.project}-${var.env}"
  common_tags = merge({
    Project = var.project
    Env     = var.env
  }, var.tags)
}

data "aws_caller_identity" "me" {}

# VPC default (baixo custo)
data "aws_vpc" "default" { default = true }
data "aws_subnets" "default" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.default.id]
  }
}
