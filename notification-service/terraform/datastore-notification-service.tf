resource "aws_dynamodb_table" "threshold-config" {

  name           = "ThresholdConfig"
  billing_mode   = "PAY_PER_REQUEST"

  hash_key       = "deviceId"

  attribute {
    name = "deviceId"
    type = "S"
  }

  tags = {
    Name        = "iot-config"
    Environment = "alpha"
  }
}