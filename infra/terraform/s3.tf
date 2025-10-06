# Se você já criou o bucket fora, pode trocar por data "aws_s3_bucket" e remover os resources.
resource "aws_s3_bucket" "db_backups" {
  bucket = var.backup_bucket
  tags   = local.common_tags
}

resource "aws_s3_bucket_versioning" "db_v" {
  bucket = aws_s3_bucket.db_backups.id
  versioning_configuration { status = "Enabled" }
}

resource "aws_s3_bucket_server_side_encryption_configuration" "db_sse" {
  bucket = aws_s3_bucket.db_backups.id
  rule {
    apply_server_side_encryption_by_default { sse_algorithm = "AES256" }
  }
}

resource "aws_s3_bucket_lifecycle_configuration" "db_lc" {
  bucket = aws_s3_bucket.db_backups.id
  rule {
    id     = "pg-dumps-retention"
    status = "Enabled"
    filter { prefix = "postgres/" }
    transition {
      days          = 30
      storage_class = "GLACIER"
    }
    expiration { days = 180 }
  }
}
