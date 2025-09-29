# AMI Amazon Linux 2023 (ARM64)
data "aws_ami" "al2023_arm" {
  most_recent = true
  owners      = ["137112412989"] # Amazon
  filter { 
            name = "name"
            values = ["al2023-ami-*-kernel-*-arm64"] 
        }
}

resource "aws_instance" "app" {
  ami                    = data.aws_ami.al2023_arm.id
  instance_type          = var.instance_type
  subnet_id              = data.aws_subnets.default.ids[0]
  vpc_security_group_ids = [aws_security_group.app_sg.id]
  key_name               = var.key_name
  iam_instance_profile   = aws_iam_instance_profile.app_profile.name

  root_block_device { 
    volume_type = "gp3" 
    volume_size = 20 
    }

  user_data = base64encode(templatefile("${path.module}/user_data.sh.tftpl", {
    region        = var.region
    image_ref     = var.image_ref
    backup_bucket = var.backup_bucket
  }))

  tags = merge(local.common_tags, { Name = local.name_prefix })
}

output "instance_id" { value = aws_instance.app.id }
output "public_ip"   { value = aws_instance.app.public_ip }
