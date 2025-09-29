# Role EC2 (ECR pull + S3 backups)
data "aws_iam_policy_document" "ec2_assume" {
  statement {
    actions = ["sts:AssumeRole"]
    principals { 
        type = "Service"
     identifiers = ["ec2.amazonaws.com"]
      }
  }
}

resource "aws_iam_role" "ec2_role" {
  name               = "${local.name_prefix}-ec2-role"
  assume_role_policy = data.aws_iam_policy_document.ec2_assume.json
  tags               = local.common_tags
}

data "aws_iam_policy_document" "ec2_policy" {
  statement {
    sid     = "EcrPull"
    actions = [
      "ecr:GetAuthorizationToken","ecr:BatchGetImage",
      "ecr:GetDownloadUrlForLayer","ecr:BatchCheckLayerAvailability"
    ]
    resources = ["*"]
  }
  statement {
    sid     = "S3BackupsWrite"
    actions = [
      "s3:PutObject","s3:AbortMultipartUpload","s3:GetBucketLocation","s3:ListBucket"
    ]
    resources = [
      "arn:aws:s3:::${var.backup_bucket}",
      "arn:aws:s3:::${var.backup_bucket}/*"
    ]
  }
}

resource "aws_iam_policy" "ec2_inline" {
  name   = "${local.name_prefix}-ec2-policy"
  policy = data.aws_iam_policy_document.ec2_policy.json
  tags   = local.common_tags
}

resource "aws_iam_role_policy_attachment" "ec2_attach" {
  role       = aws_iam_role.ec2_role.name
  policy_arn = aws_iam_policy.ec2_inline.arn
}

resource "aws_iam_instance_profile" "app_profile" {
  name = "${local.name_prefix}-profile"
  role = aws_iam_role.ec2_role.name
  tags = local.common_tags
}

# Role EventBridge Scheduler
data "aws_iam_policy_document" "sched_assume" {
  statement {
    actions = ["sts:AssumeRole"]
    principals { 
        type = "Service"
         identifiers = ["scheduler.amazonaws.com"]
          }
  }
}

resource "aws_iam_role" "sched_role" {
  name               = "${local.name_prefix}-scheduler-role"
  assume_role_policy = data.aws_iam_policy_document.sched_assume.json
  tags               = local.common_tags
}

data "aws_iam_policy_document" "sched_actions" {
  statement {
    actions   = ["ec2:StartInstances","ec2:StopInstances","ec2:DescribeInstances"]
    resources = ["arn:aws:ec2:${var.region}:${data.aws_caller_identity.me.account_id}:instance/${aws_instance.app.id}"]
  }
}

resource "aws_iam_policy" "sched_policy" {
  name   = "${local.name_prefix}-scheduler-policy"
  policy = data.aws_iam_policy_document.sched_actions.json
  tags   = local.common_tags
}

resource "aws_iam_role_policy_attachment" "sched_attach" {
  role       = aws_iam_role.sched_role.name
  policy_arn = aws_iam_policy.sched_policy.arn
}
