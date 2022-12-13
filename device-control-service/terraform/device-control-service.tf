resource "aws_lambda_function" "device_control_service" {

  function_name = "DeviceControlService"

  s3_bucket = aws_s3_bucket.lambda_bucket.id
  s3_key    = aws_s3_object.lambda_device_control_service.key

  runtime = var.lambda_runtime
  handler = var.device_control_service_handler

  source_code_hash = base64sha256(filebase64(var.device_control_service_zip_path))

  role = aws_iam_role.device_control_service_role.arn

  memory_size = var.lambda_memory
  timeout = var.lambda_timeout
}

resource "aws_lambda_permission" "allow_invocation_from_sns_device_control" {
  statement_id  = "AllowExecutionFromSNS"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.device_control_service.function_name
  principal     = "sns.amazonaws.com"
  source_arn    = aws_sns_topic.telemetry_sns.arn
}

resource "aws_cloudwatch_log_group" "device_control_service_clw" {
  name = "/aws/lambda/${aws_lambda_function.device_control_service.function_name}"

  retention_in_days = 1
}

resource "aws_s3_object" "lambda_device_control_service" {
  bucket = aws_s3_bucket.lambda_bucket.id

  key    = var.device_control_service_filename
  source = var.device_control_service_zip_path

  etag = filemd5(var.device_control_service_zip_path)
}

resource "aws_sns_topic_subscription" "telemetry_sns_subscription_control_service" {
  topic_arn = aws_sns_topic.telemetry_sns.arn
  protocol  = "lambda"
  endpoint  = aws_lambda_function.device_control_service.arn
}

// Creating IAM role
resource "aws_iam_role" "device_control_service_role" {

  name = "device_control_service_role"

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

// Allows Lambda to store logs to the CloudWatch
// For further read refer to: https://docs.aws.amazon.com/lambda/latest/dg/lambda-intro-execution-role.html
resource "aws_iam_role_policy_attachment" "device_control_service_policy" {
  role       = aws_iam_role.device_control_service_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

resource "aws_iam_role_policy" "lambda_get_policy" {

  name = "dynamoGetPolicy"
  role = aws_iam_role.device_control_service_role.name

  policy = jsonencode({
    "Version": "2012-10-17",
    "Statement":[
      {
        "Effect": "Allow",
        "Action": "dynamodb:GetItem",
        "Resource": aws_dynamodb_table.telemetry-data.arn
      },
      {
        "Effect": "Allow",
        "Action": "dynamodb:Scan",
        "Resource": aws_dynamodb_table.telemetry-data.arn
      }
    ]
  })
}

resource "aws_iam_role_policy" "lambda_publish_to_iot_policy" {

  name = "iotPublish"
  role = aws_iam_role.device_control_service_role.name

  policy = jsonencode({
    "Version": "2012-10-17",
    "Statement":[
      {
        "Effect": "Allow",
        "Action": "iot:Publish",
        "Resource": "*"
      }
    ]
  })
}