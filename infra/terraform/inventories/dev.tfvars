region  = "us-east-1"
project = "savewithme"
env     = "dev"

instance_type = "t4g.micro"
key_name      = "myslf"
rsa_public_key = """
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmHjTVWV1xhNL5gvoPmfe
/h6lQLpGr2plIJPwnfczephEuhVWxW8G5uwUd2PVmWBtdNRmNoruRoN3l6LicSqF
Nxtj2vqenmKBSSPzLoXx07K8FZCfJ/J15gBltfo6j+v17bsH2A5DT47o/TwcS6HV
qMzZ6myr278SVt6e0gUlqFgj7dLs3Bgy0KEpTYTvkXSiMGtXYzLiube/lPRsdHv9
d/7M14qj1FqLVmgugUWUWuzs51pouUOCDMxfAdiWuQ4na/MRijcxkdB1T7Zo/K3k
586yD6hM86ZTi+XAU37+KKeS6gToc6JJkbRkNITBl+h6Gv/nFDGQFwO261d1qcQc
GwIDAQAB
-----END PUBLIC KEY-----
"""
rsa_key_secret_arn = "arn:aws:secretsmanager:us-east-1:520218704533:secret:dev/savewithme/privatekey-avpskg"

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
