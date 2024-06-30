# Endpoints Design

## All endpoints
URL: {domain}/api/v1/

### Suppliers

| Method | Endpoint        | Feature                                     |
|--------| --------------- | ------------------------------------------- |
| GET    | /suppliers      | Return a list of suppliers|
| GET    | /suppliers/{id} | Return one supplier by id                   |
| POST   | /suppliers      | Create a new supplier record                |
| PATCH  | /suppliers/{id} | Update supplier info                        |
| DELETE | /suppliers/{id} | Delete a supplier                           |

### Stocks

| Method | Endpoint     | Feature                   |
| ------ | ------------ | ------------------------- |
| GET    | /stocks      | Return a list of stocks   |
| GET    | /stocks/{id} | Return a stock            |
| POST   | /stocks      | Create a new stock record |
| PATCH    | /stocks/{id} | Update a stock record     |
| DELETE | /stocks/{id} | Delete a stock record     |

### Orders

| Method | Endpoint     | Feature                   |
| ------ | ------------ | ------------------------- |
| GET    | /orders      | Return a list of orders   |
| GET    | /orders/{id} | Return a order            |
| POST   | /orders      | Create a new order record |
| PATCH    | /orders/{id} | Update a order record     |
| DELETE | /orders/{id} | Delete a order record     |

### Reports
| Method | Endpoint                            | Feature                                            |
| ------ |-------------------------------------|----------------------------------------------------|
| GET    | /reports/stock-level                | Return a csv file of stocks level                  |
| GET    | /reports/sales/?startDate=&endDate= | Return a csv file of sales summary during a period |


## Reponse Status Code

### Get

- 200 - OK (with response body of found data)
- 404 - Not Found

### POST

- 201 - Created
- 400 - Bad Request (invalid request body data)
- 409 - Conflict (duplicated data)

### PATCH

- 200 - OK
- 400 - Bad request (invalid request body)
- 404 - Not found (cannot find the item to update)
- 409 - Conflict (duplicated data)

### DELETE

- 200 - OK
- 404 - Not Found (cannot find the item to delete)
