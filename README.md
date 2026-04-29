# StockFlow – Backend Case Study

## Overview

This is a basic backend design and implementation for an inventory management system used by small businesses. The goal was to model products, warehouses, suppliers, and inventory tracking, and to build a simple API for low-stock alerts.

The focus was more on structure and reasoning rather than building a fully production-ready system.

---

## Tech Stack

* Java (Spring Boot)
* Spring Web (REST APIs)
* Spring Data JPA (ORM)
* MySQL (can be swapped with PostgreSQL)

---

## Features Implemented

### 1. Product Creation API

* Adds a new product
* Initializes inventory for a warehouse
* Ensures SKU uniqueness
* Uses transaction to avoid partial data issues

---

### 2. Database Design

The schema includes:

* **Company** → owns warehouses and products
* **Warehouse** → belongs to a company
* **Product** → belongs to a company
* **Inventory** → maps product to warehouse with quantity
* **InventoryTransaction** → tracks stock changes (sales/restock)
* **Supplier** → provides products
* **ProductSupplier** → many-to-many mapping
* **ProductBundle** → supports bundled products

---

### 3. Low Stock Alerts API

**Endpoint:**
GET /api/companies/{companyId}/alerts/low-stock

**What it does:**

* Finds products with stock below threshold
* Filters only those with recent sales activity
* Calculates estimated days until stockout
* Includes supplier info for reordering

---

## Assumptions

* SKU is unique (not fully clarified if global or per company)
* “Recent sales” = last 30 days
* Each product has a low stock threshold field
* One supplier is enough for alert response (simplified)
* Inventory cannot go negative (not enforced strictly)

---

## Design Decisions

* Inventory is stored separately to support multiple warehouses
* Inventory changes are tracked in a separate table instead of overwriting values
* Used simple relationships instead of over-optimizing joins
* Bundle support is implemented using self-referencing products

---

## Trade-offs

* Some queries (especially alerts) are not optimized and may cause multiple DB calls
* Supplier logic is simplified (real systems may need pricing, lead time, etc.)
* Inventory history table can grow large over time
* No caching or pagination added for alerts

---

## What I’d Improve With More Time

* Optimize low-stock query using joins or aggregation
* Add proper validation using annotations
* Handle edge cases like missing supplier data more cleanly
* Add pagination and filtering for alerts API
* Improve bundle handling (nested bundles, pricing rules)

---

## How to Run

1. Configure database in `application.properties`
2. Run the Spring Boot application
3. Test APIs using Postman or curl

---

## Notes

Some requirements were not fully defined, so a few assumptions were made. I tried to keep the design flexible enough to adjust later without major changes.

---
