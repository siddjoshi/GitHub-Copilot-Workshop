# TechBank Banking Platform - Terraform Variables
# 
# This file defines all the variables needed for the banking platform infrastructure.
# Use GitHub Copilot to help expand and optimize these variables.
#
# TODO: Use Copilot to add comprehensive variable validation and descriptions

# Environment Configuration
variable "environment" {
  description = "Environment name (dev, staging, prod)"
  type        = string
  default     = "dev"
  
  validation {
    condition     = contains(["dev", "staging", "prod"], var.environment)
    error_message = "Environment must be dev, staging, or prod."
  }
}

# Regional Configuration
variable "primary_region" {
  description = "Primary Azure region for deployment"
  type        = string
  default     = "East US"
  
  # TODO: Add validation for supported regions
  # Use Copilot prompt: "@azure validate Azure regions for banking compliance"
}

variable "secondary_region" {
  description = "Secondary Azure region for disaster recovery"
  type        = string
  default     = "West Europe"
}

variable "tertiary_region" {
  description = "Tertiary Azure region for global distribution"
  type        = string
  default     = "Southeast Asia"
}

# AKS Configuration
variable "aks_cluster_config" {
  description = "Configuration for AKS clusters"
  type = object({
    kubernetes_version    = string
    node_count           = number
    vm_size              = string
    enable_auto_scaling  = bool
    min_node_count       = number
    max_node_count       = number
    disk_size_gb         = number
  })
  
  default = {
    kubernetes_version   = "1.28"
    node_count          = 3
    vm_size             = "Standard_D4s_v3"
    enable_auto_scaling = true
    min_node_count      = 2
    max_node_count      = 10
    disk_size_gb        = 100
  }
  
  # TODO: Add validation for AKS configuration
  # Use Copilot prompt: "@azure validate AKS configuration for production workloads"
}

# Database Configuration
variable "database_config" {
  description = "Configuration for Azure SQL Database"
  type = object({
    sku_name                      = string
    max_size_gb                   = number
    zone_redundant                = bool
    backup_retention_days         = number
    geo_redundant_backup_enabled  = bool
    auto_pause_delay_in_minutes   = number
  })
  
  default = {
    sku_name                     = "GP_S_Gen5_2"
    max_size_gb                  = 100
    zone_redundant               = true
    backup_retention_days        = 35
    geo_redundant_backup_enabled = true
    auto_pause_delay_in_minutes  = 60
  }
  
  # TODO: Add banking-specific database requirements
  # Use Copilot prompt: "@azure configure database for banking compliance and performance"
}

# Security Configuration
variable "security_config" {
  description = "Security configuration for banking platform"
  type = object({
    enable_private_endpoints     = bool
    enable_network_isolation     = bool
    enable_ddos_protection      = bool
    enable_waf                  = bool
    key_vault_soft_delete_days  = number
    enable_audit_logging        = bool
  })
  
  default = {
    enable_private_endpoints    = true
    enable_network_isolation    = true
    enable_ddos_protection     = true
    enable_waf                 = true
    key_vault_soft_delete_days = 90
    enable_audit_logging       = true
  }
  
  # TODO: Add comprehensive security validations
  # Use Copilot prompt: "@azure add security validations for banking compliance"
}

# Monitoring Configuration
variable "monitoring_config" {
  description = "Monitoring and observability configuration"
  type = object({
    log_retention_days          = number
    enable_application_insights = bool
    enable_container_insights   = bool
    enable_vm_insights         = bool
    alert_email_addresses      = list(string)
  })
  
  default = {
    log_retention_days          = 365
    enable_application_insights = true
    enable_container_insights   = true
    enable_vm_insights         = true
    alert_email_addresses      = []
  }
}

# Backup and Disaster Recovery
variable "backup_config" {
  description = "Backup and disaster recovery configuration"
  type = object({
    backup_retention_days    = number
    geo_redundant_backup    = bool
    point_in_time_restore   = bool
    cross_region_restore    = bool
    backup_frequency        = string
  })
  
  default = {
    backup_retention_days   = 365
    geo_redundant_backup   = true
    point_in_time_restore  = true
    cross_region_restore   = true
    backup_frequency       = "Daily"
  }
}

# Network Configuration
variable "network_config" {
  description = "Network configuration for the banking platform"
  type = object({
    address_space           = list(string)
    enable_ddos_protection = bool
    dns_servers            = list(string)
    enable_vm_protection   = bool
  })
  
  default = {
    address_space           = ["10.0.0.0/16"]
    enable_ddos_protection = true
    dns_servers            = []
    enable_vm_protection   = true
  }
}

# Application Configuration
variable "app_config" {
  description = "Application-specific configuration"
  type = object({
    enable_https_only          = bool
    min_tls_version           = string
    enable_client_certificates = bool
    enable_cors               = bool
    allowed_origins           = list(string)
  })
  
  default = {
    enable_https_only          = true
    min_tls_version           = "1.2"
    enable_client_certificates = false
    enable_cors               = true
    allowed_origins           = []
  }
}

# Cost Optimization
variable "cost_config" {
  description = "Cost optimization settings"
  type = object({
    enable_auto_shutdown    = bool
    shutdown_time          = string
    enable_spot_instances  = bool
    reserved_instance_term = string
  })
  
  default = {
    enable_auto_shutdown   = false
    shutdown_time         = "19:00"
    enable_spot_instances = false
    reserved_instance_term = "1year"
  }
}

# Feature Flags
variable "feature_flags" {
  description = "Feature flags for enabling/disabling components"
  type = object({
    enable_redis_cache        = bool
    enable_service_bus        = bool
    enable_api_management     = bool
    enable_cognitive_services = bool
    enable_ml_workspace      = bool
  })
  
  default = {
    enable_redis_cache        = true
    enable_service_bus        = true
    enable_api_management     = true
    enable_cognitive_services = false
    enable_ml_workspace      = false
  }
}

# TODO: Add more comprehensive variables with Copilot assistance:
# - Compliance and governance settings
# - Performance tuning parameters
# - Integration configuration
# - CI/CD pipeline settings
# - External service configurations
#
# COPILOT PROMPTS TO USE:
# - "@azure add comprehensive variables for banking compliance"
# - "@workspace create validation rules for all variables"
# - "/optimize suggest variable optimizations for cost and performance"
# - "@azure add governance and policy variables"
