region  = "us-east-1"
project = "savewithme"
env     = "dev"

instance_type = "t4g.micro"
key_name      = "YOUR_KEYPAIR_NAME"

allowed_http_cidr = "177.181.4.242/32" 
allowed_ssh_cidr  = "177.181.4.242/32"

# fornecido pela pipeline (var extra -var image_ref=... também pode vir aqui se preferir)
# image_ref = "<acct>.dkr.ecr.sa-east-1.amazonaws.com/savewithme@sha256:..."

backup_bucket = "savewithme-db-backups-dev"

scheduling_enabled = true
schedule_timezone  = "America/Sao_Paulo"
schedule_cron_start = "cron(0 8 ? * MON-FRI *)"
schedule_cron_stop  = "cron(0 20 ? * MON-FRI *)"

tags = { Owner = "GTechnologia", Stage = "dev" }
