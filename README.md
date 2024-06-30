# Inventory Management System Micro-service

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16.2-blue)
![pgAdmin4](https://img.shields.io/badge/pgAdmin4-8.4-blue)
![Maven](https://img.shields.io/badge/Maven-3.9.7-red)
![MapStruct](https://img.shields.io/badge/MapStruct-1.5.5.Final-ff69b4)

## Overview
This project is a micro service developed in Java and Spring Boot framework, aiming to provide a robust solution to e-commerce inventory management.

The system allows users to manage suppliers, stocks, and orders. Its email service can notice users when stock level is low while clients whose order is shipped will also get notification. It also enables admin and suppliers to generate reports of stock levels and sales records.
## Table of Content
1. [Getting Started](#getting-started)
2. [System Design](#system-design)
3. [Features](#features)

## Getting Started
**Prerequisites**
- Java 21
- Maven 3.9 or higher
- PostgreSql 16 or higher

**Run**
1. Clone the repository:
    ```bash
    git clone git@github.com:monodara/java_inventory_service.git
    ```
2. Navigate to the project `application.property` file.
3. Add application properties:
    ```
    spring.application.name=inventory-management
    spring.datasource.url=your database url
    spring.datasource.username=your database user name
    spring.datasource.password=your database password
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.format_sql=true
    logging.level.org.hibernate.SQL=DEBUG
    logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

    SUPERADMIN_SECRET_KEY=your key for super admin
    ORDER_API_KEY=your key for order placer
    JWT_SECRET_KEY=your key for Jwt

    spring.mail.host=your eamil host
    spring.mail.port=465
    spring.mail.username=your email address
    spring.mail.password=your email password
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
   
   stock.threshold=a number you define

    ```

4. Build the project using Maven
    ```bash
    mvn clean install
    ```
5. Open `InventoryManagementApplication` and run it.
## System Design
The development followed CLEAN architecture to minimarize dependency.

*Domain Layer* : Houses all the Entities, Aggregates, ValueObjects & interfaces for the Repository.

*Service Layer* : Houses DTO,Service Interfaces & their implementation including the Authentication Service.

*Controller Layer* : Houses the endpoints that communicates with front-end.

*Infrastructure Layer* : Contains external services like Email Serviec, and Repository Implementation.

*Configurations* : Contains filters and security configurations.

*Scheduler* : Houses scheduled tasks, e.g., send daily email of low-level stocks.
### API Design
You can find details of endpoint design [here](https://github.com//monodara/java_inventory_service/endpoints)
There are 4 main entries:
- `api/v1/suppliers`
- `api/v1/stocks`
- `api/v1/orders`
- `api/v1/reports`

For supplier, stocks and orders, CRUD requests are supported. 
Entries return well-structured and meaningful successful/faild response.

**Response Examples**

OK:
```
{
    "data": [
        {
            "id": "e4dd7ec4-1dc7-414f-9667-048e6ae4de14",
            "name": "Supplier 0",
            "address": "Address 0",
            "email": "supplier0@example.com",
            "phone": "1234567890"
        },
        {
            "id": "1b0b238f-42b8-4e6e-b9bc-9cf3e1094f51",
            "name": "Supplier 1",
            "address": "Address 1",
            "email": "supplier1@example.com",
            "phone": "1234567891"
        },
      ... 
    ]
  },
  "errors": null
}
```

Error:
```
{
    "data": null,
    "errors": [
        {
            "field": "resource",
            "message": "Supplier not found"
        }
    ]
}
```
### Business Logic
The super admin role who has a `ADMIN_KEY` is primarily responsible for managing suppliers and stock, updating order status.

The `ORDER_API_KEY` is designed for integrating with the e-commerce system that would use the key to create, cancel or delete orders.

As the super admin registers new suppliers, the username and password can be shared with the supplier who can later access a stock level report that includes the inventory of the products they supply.

## Features
### CRUD
CRUD operations for all entities.
### Security
API Key (for super admin and order placer) and Jwt (for suppliers) based authentication strategies are utilised to restrict access to API endpoints.
```
.requestMatchers(HttpMethod.POST, "/api/v1/orders").hasAnyAuthority("ORDERPLACER")
.requestMatchers(HttpMethod.DELETE, "/api/v1/orders/**").hasAnyAuthority("ORDERPLACER")
.requestMatchers(HttpMethod.PATCH, "/api/v1/orders/cancel/**").hasAnyAuthority("ORDERPLACER")

.requestMatchers("/api/v1/reports/**").hasAnyAuthority("SUPERADMIN", "SUPPLIER")
// super admin can create/update/delete supplier and stock
.requestMatchers("/api/v1/suppliers/**","/api/v1/stocks/**").hasAnyAuthority("SUPERADMIN")
.requestMatchers(HttpMethod.GET, "/api/v1/orders/**").hasAnyAuthority("SUPERADMIN")
.requestMatchers(HttpMethod.PATCH, "/api/v1/orders/**").hasAnyAuthority("SUPERADMIN")
```
### Logger
The system leverages `logback` to record requests and exceptions and store the log file in a folder called `logs`.

<img width="880" alt="screenshot of log file" src="https://github.com/monodara/java_inventory_service/assets/screenshot_logger">

### Email Notification
The email notification service is designed to notify users if a stock level is below the predefined threshold on order creating and on a daily basis which implemented by scheduler.

<img width="880" alt="screenshot of log file" src="https://github.com/monodara/java_inventory_service/assets/screenshot_email_stocklevel">

The client who has made an order of which status is changed to SHIPPED would get email notification as well.

<img width="880" alt="screenshot of log file" src="https://github.com/monodara/java_inventory_service/assets/screenshot_email_shiporder">

### Report
The application supports csv format reports so that users can review stock levels and sales trends during a certain period.

<img width="880" alt="screenshot of log file" src="https://github.com/monodara/java_inventory_service/assets/screenshot_report_stocklevel">

<img width="880" alt="screenshot of log file" src="https://github.com/monodara/java_inventory_service/assets/screenshot_report_sales">
