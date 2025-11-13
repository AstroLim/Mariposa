# Complete List of All API Endpoints

**Base URL:** `http://localhost:8082`

---

## Quick Reference - All Endpoint Links

### Product Endpoints
- `GET http://localhost:8082/api/product`
- `GET http://localhost:8082/api/product/{id}`
- `PUT http://localhost:8082/api/product`
- `POST http://localhost:8082/api/product`
- `DELETE http://localhost:8082/api/product/{id}`

### Rice Product Endpoints
- `GET http://localhost:8082/api/riceproduct`
- `GET http://localhost:8082/api/riceproduct/{id}`
- `GET http://localhost:8082/api/riceproduct/name/{name}`
- `POST http://localhost:8082/api/riceproduct`
- `PUT http://localhost:8082/api/riceproduct/{id}`
- `DELETE http://localhost:8082/api/riceproduct/{id}`

### User Data Endpoints
- `GET http://localhost:8082/api/userdata`
- `GET http://localhost:8082/api/userdata/{id}`
- `GET http://localhost:8082/api/userdata/email/{email}`
- `GET http://localhost:8082/api/userdata/username/{username}`
- `POST http://localhost:8082/api/userdata`
- `PUT http://localhost:8082/api/userdata/{id}`
- `DELETE http://localhost:8082/api/userdata/{id}`

### Authentication Endpoints
- `POST http://localhost:8082/api/auth/register`
- `POST http://localhost:8082/api/auth/login`
- `GET http://localhost:8082/api/auth/check-email/{email}`
- `GET http://localhost:8082/api/auth/check-username/{username}`
- `PUT http://localhost:8082/api/auth/update/{userId}`

### Menu Endpoints
- `GET http://localhost:8082/api/menu`
- `PUT http://localhost:8082/api/menu`

### Order Item Endpoints
- `GET http://localhost:8082/api/orderitem`
- `GET http://localhost:8082/api/orderitem/{customerId}?status={status}`
- `PUT http://localhost:8082/api/orderitem`
- `PUT http://localhost:8082/api/orderitems`
- `POST http://localhost:8082/api/orderitem`

### Checkout Orders Endpoints
- `GET http://localhost:8082/api/checkout-orders`
- `GET http://localhost:8082/api/checkout-orders/{id}`
- `GET http://localhost:8082/api/checkout-orders/email/{email}`
- `GET http://localhost:8082/api/checkout-orders/email/{email}/status/{status}`
- `GET http://localhost:8082/api/checkout-orders/status/{status}`
- `POST http://localhost:8082/api/checkout-orders`
- `PUT http://localhost:8082/api/checkout-orders/{id}`
- `PATCH http://localhost:8082/api/checkout-orders/{id}/status`
- `DELETE http://localhost:8082/api/checkout-orders/{id}`

### Static Resources
- `GET http://localhost:8082/images/{path}`

---

## Total Count
- **Total Endpoints:** 37 endpoints
- **Product:** 5 endpoints
- **Rice Product:** 6 endpoints
- **User Data:** 7 endpoints
- **Authentication:** 5 endpoints
- **Menu:** 2 endpoints
- **Order Items:** 5 endpoints
- **Checkout Orders:** 9 endpoints
- **Static Resources:** 1 endpoint

---

For detailed documentation, see `API_ENDPOINTS.md`



