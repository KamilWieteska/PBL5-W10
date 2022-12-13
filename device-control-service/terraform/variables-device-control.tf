// DEVICE CONTROL SERVICE
variable "device_control_service_filename" {
  default = "device-control-service-1.0.0-SNAPSHOT.zip"
}

variable "device_control_service_zip_path" {
  default = "../../device-control-service/build/distributions/device-control-service-1.0.0-SNAPSHOT.zip"
}

variable "device_control_service_handler" {
  default = "com.iot.workshop.lambda.control.Handler"
}