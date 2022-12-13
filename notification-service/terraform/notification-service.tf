resource "aws_lambda_function" "notification_service" {

  function_name = "NotificationService"

  s3_bucket = aws_s3_bucket.lambda_bucket.id
  s3_key    = aws_s3_object.lambda_notification_service.key

  runtime = var.lambda_runtime
  handler = var.notification_service_handler

  source_code_hash = base64sha256(filebase64(var.notification_service_zip_path))

  role = aws_iam_role.notification_service_role.arn

  memory_size = var.lambda_memory
  timeout = 15

  environment {
    variables = {
      TOPIC_ARN = "${aws_sns_topic.alarm_sns.arn}"
    }
  }
}

resource "aws_lambda_permission" "allow_invocation_from_sns_notification" {
  statement_id  = "AllowExecutionFromSNS"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.notification_service.function_name
  principal     = "sns.amazonaws.com"
  source_arn    = aws_sns_topic.telemetry_sns.arn
}

resource "aws_cloudwatch_log_group" "notification_service_clw" {
  name = "/aws/lambda/${aws_lambda_function.notification_service.function_name}"

  retention_in_days = 1
}

resource "aws_s3_object" "lambda_notification_service" {
  bucket = aws_s3_bucket.lambda_bucket.id

  key    = var.notification_service_filename
  source = var.notification_service_zip_path

  etag = filemd5(var.notification_service_zip_path)
}

resource "aws_sns_topic_subscription" "telemetry_sns_subscription_notification_service" {
  topic_arn = aws_sns_topic.telemetry_sns.arn
  protocol  = "lambda"
  endpoint  = aws_lambda_function.notification_service.arn
}

// crossed thresholds SNS
resource "aws_sns_topic" "alarm_sns" {
  name = "crossed_threshold_topic"
}

// Creating IAM role
resource "aws_iam_role" "notification_service_role" {

  name = "notification_service_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Sid    = ""
      Principal = {
        Service = "lambda.amazonaws.com"
      }
    }
    ]
  })
}

resource "aws_sns_topic_subscription" "topic_email_subscription" {
  topic_arn = aws_sns_topic.alarm_sns.arn
  protocol  = "email"
  endpoint  = "<insert your email in here!>"
}

// Allows Lambda to store logs to the CloudWatch
// For further read refer to: https://docs.aws.amazon.com/lambda/latest/dg/lambda-intro-execution-role.html
resource "aws_iam_role_policy_attachment" "notification_service_policy" {
  role       = aws_iam_role.notification_service_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

resource "aws_iam_role_policy" "lambda_get_config_policy" {

  name = "dynamoGetPolicy"
  role = aws_iam_role.notification_service_role.name

  policy = jsonencode({
    "Version": "2012-10-17",
    "Statement":[
      {
        "Effect": "Allow",
        "Action": "dynamodb:GetItem",
        "Resource": aws_dynamodb_table.threshold-config.arn
      }
    ]
  })
}

// Policy for sending SNS for crossed thresholds
resource "aws_iam_role_policy" "notification_service_sns_publish_policy" {

  name = "notification-service-sns-publish"
  role = aws_iam_role.notification_service_role.name

  policy = jsonencode({
    "Version": "2012-10-17",
    "Statement":[
      {
        "Effect": "Allow",
        "Action": "sns:Publish",
        "Resource": aws_sns_topic.alarm_sns.arn
      }
    ]
  })
}