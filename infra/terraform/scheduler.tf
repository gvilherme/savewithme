resource "aws_scheduler_schedule" "start" {
  count                        = var.scheduling_enabled ? 1 : 0
  name                         = "${local.name_prefix}-start"
  schedule_expression          = var.schedule_cron_start
  schedule_expression_timezone = var.schedule_timezone
  flexible_time_window { mode  = "OFF" }
  target {
    arn      = "arn:aws:scheduler:::aws-sdk:ec2:startInstances"
    role_arn = aws_iam_role.sched_role.arn
    input    = jsonencode({ InstanceIds = [aws_instance.app.id] })
  }
}

resource "aws_scheduler_schedule" "stop" {
  count                        = var.scheduling_enabled ? 1 : 0
  name                         = "${local.name_prefix}-stop"
  schedule_expression          = var.schedule_cron_stop
  schedule_expression_timezone = var.schedule_timezone
  flexible_time_window { mode  = "OFF" }
  target {
    arn      = "arn:aws:scheduler:::aws-sdk:ec2:stopInstances"
    role_arn = aws_iam_role.sched_role.arn
    input    = jsonencode({ InstanceIds = [aws_instance.app.id] })
  }
}
