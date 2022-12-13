// NOTIFICATION SERVICE
variable "notification_service_filename" {
  default = "notification-service-1.0.0-SNAPSHOT.zip"
}

variable "notification_service_zip_path" {
  default = "../../notification-service/build/distributions/notification-service-1.0.0-SNAPSHOT.zip"
}

variable "notification_service_handler" {
  default = "com.iot.workshop.lambda.notification.Handler"
}