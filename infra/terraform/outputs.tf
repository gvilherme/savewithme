output "instance_id"   { value = aws_instance.app.id }
output "public_ip"     { value = aws_instance.app.public_ip }
output "backup_bucket" { value = var.backup_bucket }
