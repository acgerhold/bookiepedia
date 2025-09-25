# Bookiepedia 🏆

A comprehensive sports betting analytics platform that consolidates odds from multiple bookmakers and provides detailed betting history tracking with real-time event data integration.

## 📋 Overview

Bookiepedia solves the problem of **odds shopping** across multiple sportsbooks by consolidating betting information into a single platform. Instead of cycling through each bookmaker's app to compare odds and track bets, users can monitor odds from major US bookmakers and maintain detailed betting records with automatic win/loss updates.

### Key Features
- 📊 **Real-time Sports Data**: Integration with ESPN API for live event information
- 💰 **Multi-Bookmaker Odds**: Consolidated odds from major US sportsbooks via The Odds API  
- 📈 **Betting Analytics**: Comprehensive tracking with profit/loss insights and trend analysis
- 🔒 **Secure Authentication**: AWS Cognito integration for user management
- 📱 **Responsive Design**: Modern React frontend with mobile-optimized UI

## 🏗️ Architecture

### Current Implementation (Full-Stack Serverless)

```
┌─────────────────┐    ┌──────────────┐    ┌─────────────────┐
│   React SPA     │────│ CloudFront   │────│   S3 Bucket     │
│   (Frontend)    │    │ (CDN)        │    │  (Static Host)  │
└─────────────────┘    └──────────────┘    └─────────────────┘
         │
         ▼
┌─────────────────┐    ┌──────────────┐    ┌─────────────────┐
│  API Gateway    │────│ AWS Lambda   │────│   DynamoDB      │
│  (REST API)     │    │ (Java 11)    │    │  (NoSQL DB)     │
└─────────────────┘    └──────────────┘    └─────────────────┘
         │                       │
         ▼                       ▼
┌─────────────────┐    ┌─────────────────┐
│ AWS Cognito     │    │ External APIs   │
│ (Auth)          │    │ ESPN + Odds API │
└─────────────────┘    └─────────────────┘
```

### Future Data Engineering Vision 🚀

This project represents an ideal candidate for **modern data engineering transformation**:

#### Current Limitations → DE Solutions
- **On-demand API calls** → **Kafka streaming pipelines**
- **Manual refresh triggers** → **Apache Airflow orchestration** 
- **High DynamoDB read/write costs** → **Delta Lake with batch processing**
- **Real-time bottlenecks** → **Apache Spark structured streaming**

#### Proposed DE Architecture
```
ESPN/Odds APIs → Kafka Topics → Spark Streaming → Delta Lake → Analytics Dashboard
                      ↓              ↓              ↓
                 Data Quality    Feature Store   ML Predictions
                 Monitoring      (MLflow)        (Betting Models)
```

This evolution would enable:
- **Stream processing** for real-time odds updates
- **Data lake architecture** for historical analysis  
- **ML-powered insights** for betting predictions
- **Cost-optimized storage** with intelligent data tiering

## 🛠️ Technology Stack

### Backend
- **Language**: Java 11
- **Framework**: AWS Lambda + API Gateway
- **Database**: Amazon DynamoDB
- **Authentication**: AWS Cognito
- **External APIs**: ESPN API, The Odds API
- **Dependency Injection**: Dagger 2
- **Build Tool**: Gradle

### Frontend  
- **Framework**: React 18 + Vite
- **Styling**: CSS3 with mobile-responsive design
- **State Management**: React Hooks
- **HTTP Client**: Axios
- **Authentication**: AWS Amplify

### Infrastructure
- **Hosting**: Amazon S3 + CloudFront CDN
- **API**: AWS API Gateway + Lambda
- **Database**: DynamoDB with GSI optimization
- **CI/CD**: AWS SAM (Serverless Application Model)
- **Monitoring**: CloudWatch

### Data Sources
- **ESPN API**: Live sports events, scores, team information
- **The Odds API**: Real-time betting odds from major US sportsbooks
- **Supported Leagues**: NBA, NHL (expandable architecture)

## 📊 Data Models

### Core Entities
- **Bet**: User betting records with automatic win/loss calculation
- **Event**: Sports events with live scores and status updates  
- **Schedule**: League schedules with event relationships
- **League**: Sports league metadata and configurations
- **Odds**: Multi-bookmaker odds for money line, spread, and totals

### DynamoDB Schema Design
- **Composite Keys**: Efficient querying with partition/sort key combinations
- **GSI Optimization**: Global Secondary Indexes for complex query patterns
- **Data Denormalization**: Strategic duplication for read performance

## 🎯 Use Cases

### Primary User Flows
1. **Odds Comparison**: View live odds across multiple bookmakers for upcoming games
2. **Bet Tracking**: Record detailed bet slips with automatic outcome updates
3. **Analytics Dashboard**: Analyze betting performance with profit/loss insights
4. **League Filtering**: Focus on specific sports leagues (NBA, NHL)
5. **Historical Analysis**: Review past betting patterns and trends

### Business Value
- **Odds Shopping**: Users can find the best available odds across platforms
- **Centralized Tracking**: Eliminate manual bet tracking across multiple apps  
- **Performance Insights**: Data-driven betting decision support
- **Risk Management**: Historical analysis for better bankroll management

## 🔧 Setup & Development

### Prerequisites
- Java 11+
- Node.js 16+
- AWS CLI configured
- AWS SAM CLI

### Backend Setup
```bash
# Build Java application
./gradlew build

# Deploy to AWS
sam build
sam deploy --guided
```

### Frontend Setup  
```bash
cd web/
npm install
npm run dev:local  # Local development
npm run build      # Production build
```

## 📈 Performance Characteristics

### Current Metrics
- **API Response Time**: < 500ms for event data
- **Database Operations**: Optimized single-table design
- **Concurrent Users**: Serverless auto-scaling
- **Data Freshness**: On-demand refresh (user-triggered)

### Scalability Considerations
- **Lambda Cold Starts**: Optimized with connection pooling
- **DynamoDB Throttling**: Managed through adaptive capacity
- **API Rate Limits**: Intelligent caching and request batching

## 🛣️ Evolution Roadmap

### Phase 1: Current State ✅
- [x] Full-stack serverless application
- [x] Real-time odds integration
- [x] User authentication and bet tracking
- [x] Responsive web interface

### Phase 2: Data Engineering Transformation 🔄
- [ ] **Kafka Integration**: Replace on-demand calls with streaming
- [ ] **Apache Airflow**: Orchestrate data pipeline workflows  
- [ ] **Spark Processing**: Handle high-volume data transformation
- [ ] **Delta Lake**: Implement modern data lake architecture
- [ ] **MLOps Pipeline**: Predictive betting models

### Phase 3: Advanced Analytics 🔮
- [ ] **Real-time Dashboards**: Live odds visualization
- [ ] **Machine Learning**: Betting outcome predictions  
- [ ] **Data Quality Monitoring**: Automated data validation
- [ ] **Cost Optimization**: Intelligent data tiering strategies

## 💡 Key Learning Outcomes

### Full-Stack Development
- **Serverless Architecture**: Cost-effective, auto-scaling cloud solutions
- **API Integration**: Complex external data source management  
- **Authentication**: Secure user management with AWS Cognito
- **Responsive Design**: Mobile-first web application development

### Data Engineering Insights  
- **Real-time Challenges**: Understanding limitations of on-demand data fetching
- **Cost Optimization**: DynamoDB read/write cost considerations
- **Scalability Planning**: Identifying bottlenecks in current architecture
- **Modern DE Tools**: Vision for Kafka, Spark, and Delta Lake integration

## 🏆 Resume Highlights

- **Complex Domain Modeling**: Sports betting requires sophisticated business logic
- **AWS Ecosystem Expertise**: Comprehensive serverless solution implementation
- **External API Integration**: Real-time data processing from multiple sources
- **Performance Optimization**: Single-table DynamoDB design for efficiency
- **Modern Frontend**: React with Vite build optimization
- **Future-Ready Thinking**: Clear evolution path toward modern data engineering

---

**Note**: This project demonstrates both current full-stack capabilities and strategic thinking about modern data engineering solutions. The transition from request-driven to stream-processing architecture represents real-world scalability challenges that data engineers face daily.
