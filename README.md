# Store Application

A modern e-commerce store application built with Java and Spring Boot, demonstrating the evolution from monolith to microservices architecture.

## Architecture Overview

This project showcases two implementations:

- **Monolith**: Traditional single-service architecture located in `store-monolith/`
- **Microservices**: Distributed architecture with the following services:
  - **Eureka Server**: Service discovery
  - **Config Server**: Centralized configuration management
  - **API Gateway**: Request routing and filtering
  - **User Service**: User management and authentication
  - **Product Service**: Product catalog management
  - **Order Service**: Order processing
  - **Notification Service**: Event-driven notifications

### Key Technologies

- **Java & Spring Boot**: Core application framework
- **Kafka**: Event-driven messaging (migrated from RabbitMQ)
- **Docker & Docker Compose**: Containerization
- **PostgreSQL**: Primary database
- **Keycloak**: Authentication and authorization
- **Observability Stack**:
  - Grafana: Dashboards and visualization
  - Loki: Log aggregation
  - Zipkin: Distributed tracing
  - Prometheus: Metrics collection

## Prerequisites

- **Docker**: Latest version with Docker Compose
- **Java 17+**: For local development (optional if running via Docker)
- **Maven**: For building services locally (optional)

## Quick Start

### 1. Build Service Images

Navigate to the infrastructure directory and run one of the build scripts:

```bash
cd store-microservices/store-microservices-infra/

# Option 1: Using Buildpacks (recommended)
./build-images-buildpacks.sh

# Option 2: Using Jib
./build-images-jib.sh
```

### 2. Start Infrastructure Services

Start the core infrastructure (databases, message queues, monitoring):

```bash
# In the store-microservices-infra directory
docker-compose up -d postgres pgadmin keycloak
```

### 3. Configure Environment Variables

Each microservice requires environment variables for database connections. Create a `docker.env` file for each service or set them in your environment:

**Required for all services (user, product, order, notification):**
```env
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=your_db_name
DB_USERNAME=your_username
DB_PASSWORD=your_password

# Additional service-specific variables
# Check each service's docker.env file for specific requirements
```

### 4. Start All Services

```bash
# Start all microservices
docker-compose up -d
```

### 5. Verify Services

Check that all services are running:

```bash
docker-compose ps
```

**Service Endpoints:**
- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8080
- PgAdmin: http://localhost:5050
- Keycloak: http://localhost:8443

## Testing the Application

### Postman Collections

Import the provided Postman collections to test the APIs:

1. Open Postman
2. Import collections from `postman/collections/`:
   - `Store - Microservices.postman_collection.json` (for microservices)
   - `Store - Monolith.postman_collection.json` (for monolith)
3. Import the workspace globals from `postman/globals/workspace.postman_globals.json`
4. Start testing the endpoints

### Manual Testing

You can also test the endpoints directly:

```bash
# Health check
curl http://localhost:8080/actuator/health

# Product endpoints (via gateway)
curl http://localhost:8080/api/v1/products

# User endpoints (via gateway)  
curl http://localhost:8080/api/v1/users
```

## Development Setup

### Running Individual Services

To run a single service for development:

```bash
cd store-microservices/[service-name]
./mvnw spring-boot:run
```

### Building Projects Locally

```bash
cd store-microservices/store-microservices-infra/
./build-projects.sh
```

## Monitoring and Observability

Access the monitoring stack:

- **Grafana**: http://localhost:3000 (admin/admin)
- **Prometheus**: http://localhost:9090  
- **Zipkin**: http://localhost:9411

Log aggregation with Loki is available through Grafana's explore section.

## Environment Configuration

### Database Setup

The PostgreSQL container automatically creates multiple databases using `init-multi-db.sql`. Default credentials:
- Username: `malcolm`
- Password: `malcolm`

### Service-Specific Environment Variables

Each service directory contains a `docker.env` file with required environment variables. Key variables include:

- Database connection settings
- Message queue configuration
- External service URLs
- Authentication settings

## Troubleshooting

### Common Issues

1. **Port Conflicts**: Ensure ports 5432, 8080, 8761, etc., are available
2. **Memory Issues**: Increase Docker memory limits if services fail to start
3. **Database Connection**: Verify database credentials and network connectivity
4. **Service Discovery**: Check Eureka dashboard for service registration status

### Logs

View service logs:
```bash
docker-compose logs [service-name]
docker-compose logs -f [service-name]  # Follow logs
```

## Project Structure

```
├── store-monolith/          # Original monolith implementation
├── store-microservices/     # Microservices implementation
│   ├── eureka/             # Service discovery
│   ├── gateway/            # API gateway
│   ├── config-server/      # Configuration server
│   ├── user/              # User management service
│   ├── product/           # Product catalog service
│   ├── order/             # Order processing service
│   ├── notification/      # Notification service
│   └── store-microservices-infra/  # Docker configs and scripts
├── postman/                # API testing collections
└── logs/                   # Application logs
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.