# TechBank Multi-Region Banking Platform
# Terraform Infrastructure as Code Template
#
# This is a starter template for building enterprise-grade banking infrastructure.
# Use GitHub Copilot to help implement comprehensive Azure architecture.
#
# ARCHITECTURE REQUIREMENTS:
# - Multi-region deployment (US East, West Europe, Southeast Asia)
# - Auto-scaling AKS clusters for microservices
# - Financial-grade security and compliance
# - 99.99% SLA with disaster recovery
# - Zero-trust network architecture
#
# TODO: Use GitHub Copilot to implement these features:
# 
# COPILOT PROMPTS TO USE:
# - "@azure create enterprise Azure architecture for banking platform"
# - "@workspace generate Terraform modules for multi-region deployment"
# - "@azure implement zero-trust security with Azure services"
# - "@workspace add disaster recovery and backup strategies"

terraform {
  required_version = ">= 1.5"

  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.0"
    }
    random = {
      source  = "hashicorp/random"
      version = "~> 3.1"
    }
    time = {
      source  = "hashicorp/time"
      version = "~> 0.9"
    }
  }

  # TODO: Configure remote state backend
  # Use Copilot prompt: "@azure configure Terraform remote state with Azure Storage"
  backend "azurerm" {
    # storage_account_name = "techbankterraformstate"
    # container_name      = "tfstate"
    # key                = "banking-platform.tfstate"
  }
}

provider "azurerm" {
  features {
    key_vault {
      purge_soft_delete_on_destroy    = true
      recover_soft_deleted_key_vaults = true
    }
    resource_group {
      prevent_deletion_if_contains_resources = false
    }
  }
}

# Generate random suffix for unique resource names
resource "random_string" "suffix" {
  length  = 6
  special = false
  upper   = false
}

# Local values for consistent naming and tagging
locals {
  # TODO: Use Copilot to enhance naming conventions
  project_name = "techbank"
  environment  = var.environment

  # Resource naming
  resource_suffix = random_string.suffix.result

  # Common tags - TODO: Expand with Copilot for compliance
  common_tags = {
    Project     = local.project_name
    Environment = local.environment
    ManagedBy   = "Terraform"
    Owner       = "TechBank-DevOps"
    # TODO: Add compliance tags (SOX, PCI DSS, etc.)
  }

  # Network configuration
  # TODO: Use Copilot to design comprehensive network architecture
  address_space = {
    primary   = "10.0.0.0/16"
    secondary = "10.1.0.0/16"
    tertiary  = "10.2.0.0/16"
  }
}

# Primary Resource Group
resource "azurerm_resource_group" "primary" {
  name     = "rg-${local.project_name}-${local.environment}-${var.primary_region}-${local.resource_suffix}"
  location = var.primary_region
  tags     = local.common_tags
}

# TODO: Add resource groups for other regions
# Use Copilot prompt: "@workspace create resource groups for multi-region deployment"

# Virtual Network for primary region
resource "azurerm_virtual_network" "primary" {
  name                = "vnet-${local.project_name}-${local.environment}-${var.primary_region}"
  address_space       = [local.address_space.primary]
  location            = azurerm_resource_group.primary.location
  resource_group_name = azurerm_resource_group.primary.name
  tags                = local.common_tags

  # TODO: Add DDoS protection
  # Use Copilot prompt: "@azure add DDoS protection to virtual network"
}

# Subnets for microservices architecture
# TODO: Use Copilot to create comprehensive subnet design
resource "azurerm_subnet" "aks" {
  name                 = "snet-aks-${local.environment}"
  resource_group_name  = azurerm_resource_group.primary.name
  virtual_network_name = azurerm_virtual_network.primary.name
  address_prefixes     = ["10.0.1.0/24"]

  # TODO: Add service endpoints and delegation
  # Use Copilot prompt: "@azure configure subnet for AKS with proper security"
}

resource "azurerm_subnet" "database" {
  name                 = "snet-database-${local.environment}"
  resource_group_name  = azurerm_resource_group.primary.name
  virtual_network_name = azurerm_virtual_network.primary.name
  address_prefixes     = ["10.0.2.0/24"]

  # TODO: Configure private endpoints
  # Use Copilot prompt: "@azure add private endpoint configuration for databases"
}

# TODO: Create Application Gateway subnet
# TODO: Create Azure Firewall subnet
# TODO: Create Management subnet

# Network Security Groups
# TODO: Use Copilot to implement zero-trust network security
resource "azurerm_network_security_group" "aks" {
  name                = "nsg-aks-${local.environment}"
  location            = azurerm_resource_group.primary.location
  resource_group_name = azurerm_resource_group.primary.name
  tags                = local.common_tags

  # TODO: Add comprehensive security rules
  # Use Copilot prompt: "@azure create NSG rules for banking security compliance"
}

# TODO: Implement the following with Copilot assistance:
# 1. AKS Cluster Configuration
# 2. Azure SQL Database with geo-replication
# 3. Azure Key Vault for secrets management
# 4. Application Gateway with WAF
# 5. Azure Monitor and Log Analytics
# 6. Azure Service Bus for messaging
# 7. Azure Cache for Redis
# 8. Azure Storage accounts
# 9. Azure Backup and Site Recovery
# 10. Azure Policy for compliance

# Output values for other modules
output "resource_group_name" {
  description = "Name of the primary resource group"
  value       = azurerm_resource_group.primary.name
}

output "vnet_id" {
  description = "ID of the primary virtual network"
  value       = azurerm_virtual_network.primary.id
}

output "aks_subnet_id" {
  description = "ID of the AKS subnet"
  value       = azurerm_subnet.aks.id
}

output "database_subnet_id" {
  description = "ID of the database subnet"
  value       = azurerm_subnet.database.id
}

# TODO: Add more outputs for comprehensive infrastructure
# Use Copilot prompt: "@workspace generate comprehensive Terraform outputs"
