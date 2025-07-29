# 🛠️ Technical Architecture & Setup

This document provides comprehensive technical architecture diagrams and setup instructions for the GitHub Copilot Workshop infrastructure.

## 🏗️ Overall Technical Stack

```mermaid
graph TB
    subgraph "Development Environment"
        DE1[Visual Studio Code]
        DE2[GitHub Copilot Extensions]
        DE3[Git Client]
        DE4[Docker Desktop]
    end

    subgraph "GitHub Copilot Features"
        GC1[Basic Chat]
        GC2[Chat Participants]
        GC3[Slash Commands]
        GC4[Edit Mode]
        GC5[Agent Mode]
    end

    subgraph "Languages & Frameworks"
        LF1[Node.js/TypeScript]
        LF2[Java/Spring Boot]
        LF3[Python/Flask]
        LF4[Go/Gin]
        LF5[Infrastructure as Code]
    end

    subgraph "Cloud & Infrastructure"
        CI1[Azure Cloud Platform]
        CI2[Kubernetes/AKS]
        CI3[Terraform]
        CI4[GitHub Actions]
    end

    subgraph "Data & ML"
        DM1[PostgreSQL/MongoDB]
        DM2[Redis Cache]
        DM3[Jupyter Notebooks]
        DM4[ML Libraries]
    end

    DE1 --> GC1
    DE2 --> GC2
    DE3 --> GC3
    DE4 --> GC4

    GC1 --> LF1
    GC2 --> LF2
    GC3 --> LF3
    GC4 --> LF4
    GC5 --> LF5

    LF5 --> CI1
    CI1 --> CI2
    CI2 --> CI3
    CI3 --> CI4

    LF1 --> DM1
    LF2 --> DM2
    LF3 --> DM3
    LF4 --> DM4

    classDef development fill:#e8f5e8,stroke:#2e7d32
    classDef copilot fill:#e3f2fd,stroke:#1565c0
    classDef languages fill:#fff3e0,stroke:#ef6c00
    classDef cloud fill:#f3e5f5,stroke:#7b1fa2
    classDef data fill:#fce4ec,stroke:#c2185b

    class DE1,DE2,DE3,DE4 development
    class GC1,GC2,GC3,GC4,GC5 copilot
    class LF1,LF2,LF3,LF4,LF5 languages
    class CI1,CI2,CI3,CI4 cloud
    class DM1,DM2,DM3,DM4 data
```

## 💻 Development Environment Setup

### Required Software Installation Flow

```mermaid
graph LR
    A[System Requirements Check] --> B[VS Code Installation]
    B --> C[GitHub Copilot Extensions]
    C --> D[Git Configuration]
    D --> E[Docker Setup]
    E --> F[Language Runtimes]
    F --> G[Cloud CLI Tools]
    G --> H[Validation Tests]

    A1[Operating System: Windows 10+, macOS 10.15+, Ubuntu 18.04+]
    A2[RAM: 8GB minimum, 16GB recommended]
    A3[Storage: 10GB free space]
    A4[Internet: Stable connection required]

    A --> A1
    A --> A2
    A --> A3
    A --> A4

    classDef requirement fill:#ffecb3,stroke:#f57f17
    classDef installation fill:#c8e6c9,stroke:#388e3c
    classDef validation fill:#bbdefb,stroke:#1976d2

    class A,A1,A2,A3,A4 requirement
    class B,C,D,E,F,G installation
    class H validation
```

### VS Code Extensions Architecture

```mermaid
graph TB
    subgraph "Core Extensions"
        CE1[GitHub Copilot]
        CE2[GitHub Copilot Chat]
        CE3[GitLens]
        CE4[Live Share]
    end

    subgraph "Language Support"
        LS1[TypeScript/JavaScript]
        LS2[Java Extension Pack]
        LS3[Python]
        LS4[Go]
        LS5[Docker]
    end

    subgraph "Cloud & DevOps"
        CD1[Azure Tools]
        CD2[Kubernetes]
        CD3[Terraform]
        CD4[GitHub Actions]
    end

    subgraph "Data Science"
        DS1[Jupyter]
        DS2[Python Data Science]
        DS3[SQL Tools]
    end

    CE1 --> LS1
    CE2 --> LS2
    CE3 --> LS3
    CE4 --> LS4

    LS1 --> CD1
    LS2 --> CD2
    LS3 --> CD3
    LS4 --> CD4

    LS3 --> DS1
    DS1 --> DS2
    DS2 --> DS3

    classDef core fill:#e3f2fd,stroke:#1565c0
    classDef language fill:#e8f5e8,stroke:#2e7d32
    classDef cloud fill:#fff3e0,stroke:#ef6c00
    classDef data fill:#f3e5f5,stroke:#7b1fa2

    class CE1,CE2,CE3,CE4 core
    class LS1,LS2,LS3,LS4,LS5 language
    class CD1,CD2,CD3,CD4 cloud
    class DS1,DS2,DS3 data
```

## 🌐 Network Architecture & Requirements

### Network Connectivity Requirements

```mermaid
graph TB
    subgraph "Participant Workstation"
        PW1[VS Code]
        PW2[Docker Desktop]
        PW3[Git Client]
        PW4[Web Browser]
    end

    subgraph "GitHub Services"
        GS1[GitHub.com]
        GS2[GitHub Copilot API]
        GS3[GitHub Actions]
        GS4[GitHub Packages]
    end

    subgraph "Azure Services"
        AS1[Azure Portal]
        AS2[Azure CLI]
        AS3[AKS Clusters]
        AS4[Container Registry]
    end

    subgraph "External Dependencies"
        ED1[Docker Hub]
        ED2[NPM Registry]
        ED3[Maven Central]
        ED4[PyPI]
        ED5[Go Modules Proxy]
    end

    PW1 --> GS1
    PW1 --> GS2
    PW2 --> GS3
    PW3 --> GS4

    PW1 --> AS1
    PW2 --> AS2
    PW3 --> AS3
    PW4 --> AS4

    PW1 --> ED1
    PW2 --> ED2
    PW3 --> ED3
    PW4 --> ED4
    PW1 --> ED5

    classDef workstation fill:#e8f5e8,stroke:#2e7d32
    classDef github fill:#e3f2fd,stroke:#1565c0
    classDef azure fill:#fff3e0,stroke:#ef6c00
    classDef external fill:#f3e5f5,stroke:#7b1fa2

    class PW1,PW2,PW3,PW4 workstation
    class GS1,GS2,GS3,GS4 github
    class AS1,AS2,AS3,AS4 azure
    class ED1,ED2,ED3,ED4,ED5 external
```

### Firewall & Security Requirements

```mermaid
graph LR
    subgraph "Outbound Connections Required"
        OC1[HTTPS/443 - GitHub APIs]
        OC2[HTTPS/443 - Azure Services]
        OC3[HTTPS/443 - Package Registries]
        OC4[SSH/22 - Git over SSH]
        OC5[HTTP/80 - Package Downloads]
    end

    subgraph "Corporate Network Considerations"
        CN1[Proxy Configuration]
        CN2[SSL Certificate Trust]
        CN3[DNS Resolution]
        CN4[VPN Compatibility]
    end

    subgraph "Security Controls"
        SC1[GitHub Copilot Access]
        SC2[Azure Subscription]
        SC3[Docker Registry Access]
        SC4[External API Access]
    end

    OC1 --> CN1 --> SC1
    OC2 --> CN2 --> SC2
    OC3 --> CN3 --> SC3
    OC4 --> CN4 --> SC4
    OC5 --> CN1

    classDef outbound fill:#e8f5e8,stroke:#2e7d32
    classDef corporate fill:#fff3e0,stroke:#ef6c00
    classDef security fill:#fce4ec,stroke:#c2185b

    class OC1,OC2,OC3,OC4,OC5 outbound
    class CN1,CN2,CN3,CN4 corporate
    class SC1,SC2,SC3,SC4 security
```

## 🔧 Environment Validation Process

### Automated Validation Script Architecture

```mermaid
sequenceDiagram
    participant Script as Validation Script
    participant VS Code as VS Code
    participant Git as Git Client
    participant Docker as Docker
    participant GitHub as GitHub Services
    participant Azure as Azure CLI

    Script->>VS Code: Check installation & version
    VS Code-->>Script: Version info & extensions list

    Script->>Git: Verify configuration
    Git-->>Script: User config & authentication

    Script->>Docker: Test container operations
    Docker-->>Script: Engine status & connectivity

    Script->>GitHub: Test API connectivity
    GitHub-->>Script: Authentication & rate limits

    Script->>Azure: Verify subscription access
    Azure-->>Script: Account info & permissions

    Script->>Script: Generate validation report
    Script-->>Script: Pass/Fail status with remediation
```

### Validation Checklist Flow

```mermaid
graph TD
    A[Start Validation] --> B{VS Code Installed?}
    B -->|Yes| C{Extensions Installed?}
    B -->|No| B1[Install VS Code]
    B1 --> C

    C -->|Yes| D{Git Configured?}
    C -->|No| C1[Install Extensions]
    C1 --> D

    D -->|Yes| E{Docker Running?}
    D -->|No| D1[Configure Git]
    D1 --> E

    E -->|Yes| F{GitHub Access?}
    E -->|No| E1[Start Docker]
    E1 --> F

    F -->|Yes| G{Azure CLI Ready?}
    F -->|No| F1[Authenticate GitHub]
    F1 --> G

    G -->|Yes| H[✅ Validation Complete]
    G -->|No| G1[Login to Azure]
    G1 --> H

    B1 --> I[❌ Manual Setup Required]
    C1 --> I
    D1 --> I
    E1 --> I
    F1 --> I
    G1 --> I

    classDef check fill:#fff3e0,stroke:#ef6c00
    classDef action fill:#e3f2fd,stroke:#1565c0
    classDef success fill:#e8f5e8,stroke:#2e7d32
    classDef failure fill:#ffebee,stroke:#c62828

    class B,C,D,E,F,G check
    class B1,C1,D1,E1,F1,G1 action
    class H success
    class I failure
```

## 🎯 Session-Specific Technical Requirements

### Session 1: Payment Service Technical Stack

```mermaid
graph TB
    subgraph "Runtime Environment"
        RE1[Node.js 18+]
        RE2[npm/yarn]
        RE3[TypeScript Compiler]
    end

    subgraph "Development Tools"
        DT1[Express.js Framework]
        DT2[Jest Testing Framework]
        DT3[ESLint/Prettier]
        DT4[Nodemon for Development]
    end

    subgraph "External Integrations"
        EI1[Stripe Test API]
        EI2[GitHub Actions]
        EI3[Docker for Containerization]
    end

    RE1 --> DT1
    RE2 --> DT2
    RE3 --> DT3
    DT4 --> EI1
    DT1 --> EI2
    DT2 --> EI3

    classDef runtime fill:#e8f5e8,stroke:#2e7d32
    classDef development fill:#e3f2fd,stroke:#1565c0
    classDef external fill:#fff3e0,stroke:#ef6c00

    class RE1,RE2,RE3 runtime
    class DT1,DT2,DT3,DT4 development
    class EI1,EI2,EI3 external
```

### Session 2: Java Modernization Stack

```mermaid
graph TB
    subgraph "Java Environment"
        JE1[OpenJDK 21]
        JE2[Maven 3.8+]
        JE3[Spring Boot 3.2]
    end

    subgraph "Migration Tools"
        MT1[Java Upgrade Assistant]
        MT2[OpenRewrite]
        MT3[Spring Boot Migrator]
    end

    subgraph "Testing & Quality"
        TQ1[JUnit 5]
        TQ2[Mockito]
        TQ3[SonarQube]
        TQ4[SpotBugs]
    end

    JE1 --> MT1
    JE2 --> MT2
    JE3 --> MT3

    MT1 --> TQ1
    MT2 --> TQ2
    MT3 --> TQ3
    TQ1 --> TQ4

    classDef java fill:#fff3e0,stroke:#ef6c00
    classDef migration fill:#e3f2fd,stroke:#1565c0
    classDef testing fill:#e8f5e8,stroke:#2e7d32

    class JE1,JE2,JE3 java
    class MT1,MT2,MT3 migration
    class TQ1,TQ2,TQ3,TQ4 testing
```

### Session 3: DevOps Infrastructure Stack

```mermaid
graph TB
    subgraph "Infrastructure as Code"
        IAC1[Terraform 1.5+]
        IAC2[Azure CLI 2.50+]
        IAC3[kubectl 1.28+]
        IAC4[Helm 3.12+]
    end

    subgraph "Container & Orchestration"
        CO1[Docker 24.0+]
        CO2[Azure Container Registry]
        CO3[Azure Kubernetes Service]
        CO4[ArgoCD for GitOps]
    end

    subgraph "Monitoring & Observability"
        MO1[Prometheus]
        MO2[Grafana]
        MO3[Azure Monitor]
        MO4[Application Insights]
    end

    IAC1 --> CO1
    IAC2 --> CO2
    IAC3 --> CO3
    IAC4 --> CO4

    CO1 --> MO1
    CO2 --> MO2
    CO3 --> MO3
    CO4 --> MO4

    classDef infrastructure fill:#e8f5e8,stroke:#2e7d32
    classDef container fill:#e3f2fd,stroke:#1565c0
    classDef monitoring fill:#fff3e0,stroke:#ef6c00

    class IAC1,IAC2,IAC3,IAC4 infrastructure
    class CO1,CO2,CO3,CO4 container
    class MO1,MO2,MO3,MO4 monitoring
```

## 📊 Resource Requirements & Scaling

### Compute Resource Requirements

```mermaid
graph TB
    subgraph "Minimum Requirements"
        MIN1[CPU: 4 cores]
        MIN2[RAM: 8GB]
        MIN3[Storage: 50GB SSD]
        MIN4[Network: 10 Mbps]
    end

    subgraph "Recommended Requirements"
        REC1[CPU: 8 cores]
        REC2[RAM: 16GB]
        REC3[Storage: 100GB NVMe]
        REC4[Network: 25 Mbps]
    end

    subgraph "Optimal Requirements"
        OPT1[CPU: 12+ cores]
        OPT2[RAM: 32GB]
        OPT3[Storage: 250GB NVMe]
        OPT4[Network: 50+ Mbps]
    end

    MIN1 --> REC1 --> OPT1
    MIN2 --> REC2 --> OPT2
    MIN3 --> REC3 --> OPT3
    MIN4 --> REC4 --> OPT4

    classDef minimum fill:#ffebee,stroke:#c62828
    classDef recommended fill:#fff3e0,stroke:#ef6c00
    classDef optimal fill:#e8f5e8,stroke:#2e7d32

    class MIN1,MIN2,MIN3,MIN4 minimum
    class REC1,REC2,REC3,REC4 recommended
    class OPT1,OPT2,OPT3,OPT4 optimal
```

### Cloud Resource Architecture

```mermaid
graph TB
    subgraph "Azure Subscription Structure"
        AS1[Production Subscription]
        AS2[Development Subscription]
        AS3[Shared Services Subscription]
    end

    subgraph "Resource Groups"
        RG1[workshop-prod-rg]
        RG2[workshop-dev-rg]
        RG3[workshop-shared-rg]
    end

    subgraph "Compute Resources"
        CR1[AKS Clusters]
        CR2[Virtual Machines]
        CR3[Container Instances]
    end

    subgraph "Storage & Data"
        SD1[Azure SQL Databases]
        SD2[Storage Accounts]
        SD3[Key Vaults]
    end

    subgraph "Networking"
        NET1[Virtual Networks]
        NET2[Load Balancers]
        NET3[Application Gateways]
    end

    AS1 --> RG1
    AS2 --> RG2
    AS3 --> RG3

    RG1 --> CR1
    RG2 --> CR2
    RG3 --> CR3

    CR1 --> SD1
    CR2 --> SD2
    CR3 --> SD3

    SD1 --> NET1
    SD2 --> NET2
    SD3 --> NET3

    classDef subscription fill:#e3f2fd,stroke:#1565c0
    classDef resourcegroup fill:#e8f5e8,stroke:#2e7d32
    classDef compute fill:#fff3e0,stroke:#ef6c00
    classDef storage fill:#f3e5f5,stroke:#7b1fa2
    classDef network fill:#fce4ec,stroke:#c2185b

    class AS1,AS2,AS3 subscription
    class RG1,RG2,RG3 resourcegroup
    class CR1,CR2,CR3 compute
    class SD1,SD2,SD3 storage
    class NET1,NET2,NET3 network
```

## 🔄 Disaster Recovery & Backup

### Backup Strategy Architecture

```mermaid
graph LR
    subgraph "Data Sources"
        DS1[Workshop Code]
        DS2[Participant Progress]
        DS3[Configuration Data]
        DS4[Certificates & Keys]
    end

    subgraph "Backup Mechanisms"
        BM1[Git Repository Backup]
        BM2[Database Snapshots]
        BM3[Configuration Export]
        BM4[Key Vault Backup]
    end

    subgraph "Storage Locations"
        SL1[Primary Git Remote]
        SL2[Azure Backup Vault]
        SL3[Cross-Region Storage]
        SL4[Local Offline Copies]
    end

    subgraph "Recovery Procedures"
        RP1[Automated Git Restore]
        RP2[Point-in-Time Recovery]
        RP3[Cross-Region Failover]
        RP4[Manual Recovery Process]
    end

    DS1 --> BM1 --> SL1 --> RP1
    DS2 --> BM2 --> SL2 --> RP2
    DS3 --> BM3 --> SL3 --> RP3
    DS4 --> BM4 --> SL4 --> RP4

    classDef data fill:#e8f5e8,stroke:#2e7d32
    classDef backup fill:#e3f2fd,stroke:#1565c0
    classDef storage fill:#fff3e0,stroke:#ef6c00
    classDef recovery fill:#f3e5f5,stroke:#7b1fa2

    class DS1,DS2,DS3,DS4 data
    class BM1,BM2,BM3,BM4 backup
    class SL1,SL2,SL3,SL4 storage
    class RP1,RP2,RP3,RP4 recovery
```

## 📈 Performance Monitoring & Optimization

### Performance Metrics Dashboard Architecture

```mermaid
graph TB
    subgraph "Data Collection Layer"
        DC1[Application Metrics]
        DC2[Infrastructure Metrics]
        DC3[User Experience Metrics]
        DC4[Business Metrics]
    end

    subgraph "Processing Layer"
        PL1[Metric Aggregation]
        PL2[Alert Processing]
        PL3[Anomaly Detection]
        PL4[Trend Analysis]
    end

    subgraph "Visualization Layer"
        VL1[Real-time Dashboards]
        VL2[Historical Reports]
        VL3[Alert Notifications]
        VL4[Performance Insights]
    end

    subgraph "Action Layer"
        AL1[Auto-scaling Triggers]
        AL2[Alert Responses]
        AL3[Performance Tuning]
        AL4[Capacity Planning]
    end

    DC1 --> PL1 --> VL1 --> AL1
    DC2 --> PL2 --> VL2 --> AL2
    DC3 --> PL3 --> VL3 --> AL3
    DC4 --> PL4 --> VL4 --> AL4

    classDef collection fill:#e8f5e8,stroke:#2e7d32
    classDef processing fill:#e3f2fd,stroke:#1565c0
    classDef visualization fill:#fff3e0,stroke:#ef6c00
    classDef action fill:#f3e5f5,stroke:#7b1fa2

    class DC1,DC2,DC3,DC4 collection
    class PL1,PL2,PL3,PL4 processing
    class VL1,VL2,VL3,VL4 visualization
    class AL1,AL2,AL3,AL4 action
```