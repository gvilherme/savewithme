terraform {
  backend "s3" {} # init com -backend-config=backend.hcl
  required_version = ">= 1.9.0"
  required_providers {
    aws = { source = "hashicorp/aws", version = "~> 6.0" }
  }
}

provider "aws" { region = var.region }

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
