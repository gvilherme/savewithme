variable "region"        { type = string }
variable "project"       { type = string }
variable "env"           { type = string }

variable "instance_type" { type = string }
variable "key_name"      { type = string }

variable "allowed_http_cidr" { type = string }
variable "allowed_ssh_cidr"  { type = string }

# Imagem do app por DIGEST (imutável): <acct>.dkr.ecr.<reg>.amazonaws.com/repo@sha256:...
variable "image_ref"     { type = string }

# Bucket de backups (já existente ou gerenciado por este TF)
variable "backup_bucket" { type = string }

# Scheduler (liga/desliga EC2). Se desabilitar em prod, set enabled=false
variable "scheduling_enabled" { type = bool }
variable "schedule_timezone"  { type = string }  # ex: "America/Sao_Paulo"
variable "schedule_cron_start" { type = string } # ex: "cron(0 8 ? * MON-FRI *)"
variable "schedule_cron_stop"  { type = string } # ex: "cron(0 20 ? * MON-FRI *)"

variable "tags" {
  type    = map(string)
  default = {}
}
