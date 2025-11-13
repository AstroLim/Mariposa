# API Endpoints Documentation

**Base URL:** `http://localhost:8082`

All endpoints return JSON and support CORS from `http://localhost:4200`

---

## 1. Product Endpoints (`/api/product`)

### GET `/api/product`
- **Description:** Get all products with variations grouped by name
- **Method:** GET
- **Link:** `http://localhost:8082/api/product`
- **Response:** Array of `ProductWithVariations` objects

### GET `/api/product/{id}`
- **Description:** Get a single product by ID
- **Method:** GET
- **Link:** `http://localhost:8082/api/product/{id}`
- **Example:** `http://localhost:8082/api/product/1`
- **Response:** `Product` object

### PUT `/api/product`
- **Description:** Create a new product
- **Method:** PUT
- **Link:** `http://localhost:8082/api/product`
- **Body:** `Product` object (JSON)
- **Response:** Created `Product` object

### POST `/api/product`
- **Description:** Update an existing product
- **Method:** POST
- **Link:** `http://localhost:8082/api/product`
- **Body:** `Product` object (JSON)
- **Response:** Updated `Product` object

### DELETE `/api/product/{id}`
- **Description:** Delete a product by ID
- **Method:** DELETE
- **Link:** `http://localhost:8082/api/product/{id}`
- **Example:** `http://localhost:8082/api/product/1`
- **Response:** null on success

---

## 2. Rice Product Endpoints (`/api/riceproduct`)

### GET `/api/riceproduct`
- **Description:** Get all rice products
- **Method:** GET
- **Link:** `http://localhost:8082/api/riceproduct`
- **Response:** Array of `RiceProductData` objects

### GET `/api/riceproduct/{id}`
- **Description:** Get a rice product by ID
- **Method:** GET
- **Link:** `http://localhost:8082/api/riceproduct/{id}`
- **Example:** `http://localhost:8082/api/riceproduct/1`
- **Response:** `RiceProductData` object

### GET `/api/riceproduct/name/{name}`
- **Description:** Search rice products by name (contains search)
- **Method:** GET
- **Link:** `http://localhost:8082/api/riceproduct/name/{name}`
- **Example:** `http://localhost:8082/api/riceproduct/name/Basmati`
- **Response:** Array of `RiceProductData` objects matching the name

### POST `/api/riceproduct`
- **Description:** Create a new rice product
- **Method:** POST
- **Link:** `http://localhost:8082/api/riceproduct`
- **Body:** `RiceProductData` object (JSON)
- **Response:** Created `RiceProductData` object (HTTP 201)

### PUT `/api/riceproduct/{id}`
- **Description:** Update an existing rice product
- **Method:** PUT
- **Link:** `http://localhost:8082/api/riceproduct/{id}`
- **Example:** `http://localhost:8082/api/riceproduct/1`
- **Body:** `RiceProductData` object (JSON)
- **Response:** Updated `RiceProductData` object

### DELETE `/api/riceproduct/{id}`
- **Description:** Delete a rice product by ID
- **Method:** DELETE
- **Link:** `http://localhost:8082/api/riceproduct/{id}`
- **Example:** `http://localhost:8082/api/riceproduct/1`
- **Response:** `{"message": "Rice product deleted successfully"}`

---

## 3. User Data Endpoints (`/api/userdata`)

### GET `/api/userdata`
- **Description:** Get all users (passwords are removed from response)
- **Method:** GET
- **Link:** `http://localhost:8082/api/userdata`
- **Response:** Array of `UserData` objects (without passwords)

### GET `/api/userdata/{id}`
- **Description:** Get a user by ID (password removed from response)
- **Method:** GET
- **Link:** `http://localhost:8082/api/userdata/{id}`
- **Example:** `http://localhost:8082/api/userdata/1`
- **Response:** `UserData` object (without password)

### GET `/api/userdata/email/{email}`
- **Description:** Get a user by email (password removed from response)
- **Method:** GET
- **Link:** `http://localhost:8082/api/userdata/email/{email}`
- **Example:** `http://localhost:8082/api/userdata/email/user@example.com`
- **Response:** `UserData` object (without password)

### GET `/api/userdata/username/{username}`
- **Description:** Get a user by username (password removed from response)
- **Method:** GET
- **Link:** `http://localhost:8082/api/userdata/username/{username}`
- **Example:** `http://localhost:8082/api/userdata/username/johndoe`
- **Response:** `UserData` object (without password)

### POST `/api/userdata`
- **Description:** Create a new user (validates email/username uniqueness)
- **Method:** POST
- **Link:** `http://localhost:8082/api/userdata`
- **Body:** `UserData` object (JSON)
- **Response:** Created `UserData` object (without password, HTTP 201)
- **Error:** 400 if email or username already exists

### PUT `/api/userdata/{id}`
- **Description:** Update an existing user (validates email/username uniqueness)
- **Method:** PUT
- **Link:** `http://localhost:8082/api/userdata/{id}`
- **Example:** `http://localhost:8082/api/userdata/1`
- **Body:** `UserData` object (JSON)
- **Response:** Updated `UserData` object (without password)
- **Error:** 400 if email or username already exists

### DELETE `/api/userdata/{id}`
- **Description:** Delete a user by ID
- **Method:** DELETE
- **Link:** `http://localhost:8082/api/userdata/{id}`
- **Example:** `http://localhost:8082/api/userdata/1`
- **Response:** `{"message": "User deleted successfully"}`

---

## 4. Authentication Endpoints (`/api/auth`)

### POST `/api/auth/register`
- **Description:** Register a new user
- **Method:** POST
- **Link:** `http://localhost:8082/api/auth/register`
- **Body:** `User` object with `username`, `email`, `password` (JSON)
- **Response:** Registered `User` object (without password)
- **Error:** 400 for validation errors

### POST `/api/auth/login`
- **Description:** Login with email and password
- **Method:** POST
- **Link:** `http://localhost:8082/api/auth/login`
- **Body:** `{"email": "user@example.com", "password": "password"}` (JSON)
- **Response:** `User` object (without password)
- **Error:** 401 for invalid credentials

### GET `/api/auth/check-email/{email}`
- **Description:** Check if an email already exists
- **Method:** GET
- **Link:** `http://localhost:8082/api/auth/check-email/{email}`
- **Example:** `http://localhost:8082/api/auth/check-email/user@example.com`
- **Response:** `{"exists": true}` or `{"exists": false}`

### GET `/api/auth/check-username/{username}`
- **Description:** Check if a username already exists
- **Method:** GET
- **Link:** `http://localhost:8082/api/auth/check-username/{username}`
- **Example:** `http://localhost:8082/api/auth/check-username/johndoe`
- **Response:** `{"exists": true}` or `{"exists": false}`

### PUT `/api/auth/update/{userId}`
- **Description:** Update user information
- **Method:** PUT
- **Link:** `http://localhost:8082/api/auth/update/{userId}`
- **Example:** `http://localhost:8082/api/auth/update/1`
- **Body:** `User` object (JSON)
- **Response:** Updated `User` object (without password)
- **Error:** 400 for validation errors

---

## 5. Menu Endpoints (`/api/menu`)

### GET `/api/menu`
- **Description:** Get all menus
- **Method:** GET
- **Link:** `http://localhost:8082/api/menu`
- **Response:** Array of `Menu` objects

### PUT `/api/menu`
- **Description:** Create a new menu
- **Method:** PUT
- **Link:** `http://localhost:8082/api/menu`
- **Body:** `Menu` object (JSON)
- **Response:** Created `Menu` object

---

## 6. Order Item Endpoints (`/api/orderitem`)

### GET `/api/orderitem`
- **Description:** Get all order items
- **Method:** GET
- **Link:** `http://localhost:8082/api/orderitem`
- **Response:** Array of `OrderItem` objects

### GET `/api/orderitem/{customerId}?status={status}`
- **Description:** Get order items for a customer by status
  - `status=0` returns cart items
  - `status!=0` returns order items
- **Method:** GET
- **Link:** `http://localhost:8082/api/orderitem/{customerId}?status={status}`
- **Example:** `http://localhost:8082/api/orderitem/1?status=0`
- **Response:** Array of `OrderItem` objects

### PUT `/api/orderitem`
- **Description:** Create a new order item
- **Method:** PUT
- **Link:** `http://localhost:8082/api/orderitem`
- **Body:** `OrderItem` object (JSON)
- **Response:** Created `OrderItem` object

### PUT `/api/orderitems`
- **Description:** Create multiple order items
- **Method:** PUT
- **Link:** `http://localhost:8082/api/orderitems`
- **Body:** Array of `OrderItem` objects (JSON)
- **Response:** Array of created `OrderItem` objects

### POST `/api/orderitem`
- **Description:** Update an existing order item
- **Method:** POST
- **Link:** `http://localhost:8082/api/orderitem`
- **Body:** `OrderItem` object (JSON)
- **Response:** Updated `OrderItem` object

---

## 7. Checkout Orders Endpoints (`/api/checkout-orders`)

### GET `/api/checkout-orders`
- **Description:** Get all checkout orders
- **Method:** GET
- **Link:** `http://localhost:8082/api/checkout-orders`
- **Response:** Array of `CheckoutOrder` objects

### GET `/api/checkout-orders/{id}`
- **Description:** Get a checkout order by ID
- **Method:** GET
- **Link:** `http://localhost:8082/api/checkout-orders/{id}`
- **Example:** `http://localhost:8082/api/checkout-orders/1`
- **Response:** `CheckoutOrder` object

### GET `/api/checkout-orders/email/{email}`
- **Description:** Get all orders for a specific email
- **Method:** GET
- **Link:** `http://localhost:8082/api/checkout-orders/email/{email}`
- **Example:** `http://localhost:8082/api/checkout-orders/email/user@example.com`
- **Response:** Array of `CheckoutOrder` objects

### GET `/api/checkout-orders/email/{email}/status/{status}`
- **Description:** Get orders by email and status
- **Method:** GET
- **Link:** `http://localhost:8082/api/checkout-orders/email/{email}/status/{status}`
- **Example:** `http://localhost:8082/api/checkout-orders/email/user@example.com/status/pending`
- **Response:** Array of `CheckoutOrder` objects
- **Valid statuses:** `pending`, `received`, `not_received`, `cancelled`

### GET `/api/checkout-orders/status/{status}`
- **Description:** Get all orders by status
- **Method:** GET
- **Link:** `http://localhost:8082/api/checkout-orders/status/{status}`
- **Example:** `http://localhost:8082/api/checkout-orders/status/pending`
- **Response:** Array of `CheckoutOrder` objects

### POST `/api/checkout-orders`
- **Description:** Create a new checkout order
- **Method:** POST
- **Link:** `http://localhost:8082/api/checkout-orders`
- **Body:** 
```json
{
  "email": "user@example.com",
  "checkoutedItems": ["sushiRice", "jasmineRice"],
  "status": "pending"
}
```
- **Response:** Created `CheckoutOrder` object (HTTP 201)

### PUT `/api/checkout-orders/{id}`
- **Description:** Update an existing checkout order
- **Method:** PUT
- **Link:** `http://localhost:8082/api/checkout-orders/{id}`
- **Example:** `http://localhost:8082/api/checkout-orders/1`
- **Body:** `CheckoutOrderRequest` object (JSON)
- **Response:** Updated `CheckoutOrder` object

### PATCH `/api/checkout-orders/{id}/status`
- **Description:** Update order status only
- **Method:** PATCH
- **Link:** `http://localhost:8082/api/checkout-orders/{id}/status`
- **Example:** `http://localhost:8082/api/checkout-orders/1/status`
- **Body:** `{"status": "received"}`
- **Response:** Updated `CheckoutOrder` object
- **Valid statuses:** `pending`, `received`, `not_received`, `cancelled`

### DELETE `/api/checkout-orders/{id}`
- **Description:** Delete a checkout order by ID
- **Method:** DELETE
- **Link:** `http://localhost:8082/api/checkout-orders/{id}`
- **Example:** `http://localhost:8082/api/checkout-orders/1`
- **Response:** `{"message": "Checkout order deleted successfully"}`

---

## 8. Static Resources

### GET `/images/**`
- **Description:** Serve images from resources folder
- **Method:** GET
- **Link:** `http://localhost:8082/images/{path}`
- **Example:** `http://localhost:8082/images/rice/basmati.jpg`
- **Response:** Image file (JPG, PNG, etc.)
- **Note:** Images are served from `resources/rice-pics/` folder

---

## Summary by Category

### Product Management
- GET `/api/product` - Get all products with variations
- GET `/api/product/{id}` - Get product by ID
- PUT `/api/product` - Create product
- POST `/api/product` - Update product
- DELETE `/api/product/{id}` - Delete product

### Rice Product Management
- GET `/api/riceproduct` - Get all rice products
- GET `/api/riceproduct/{id}` - Get rice product by ID
- GET `/api/riceproduct/name/{name}` - Search rice products by name
- POST `/api/riceproduct` - Create rice product
- PUT `/api/riceproduct/{id}` - Update rice product
- DELETE `/api/riceproduct/{id}` - Delete rice product

### User Management
- GET `/api/userdata` - Get all users
- GET `/api/userdata/{id}` - Get user by ID
- GET `/api/userdata/email/{email}` - Get user by email
- GET `/api/userdata/username/{username}` - Get user by username
- POST `/api/userdata` - Create user
- PUT `/api/userdata/{id}` - Update user
- DELETE `/api/userdata/{id}` - Delete user

### Authentication
- POST `/api/auth/register` - Register new user
- POST `/api/auth/login` - Login
- GET `/api/auth/check-email/{email}` - Check if email exists
- GET `/api/auth/check-username/{username}` - Check if username exists
- PUT `/api/auth/update/{userId}` - Update user

### Menu
- GET `/api/menu` - Get all menus
- PUT `/api/menu` - Create menu

### Order Items
- GET `/api/orderitem` - Get all order items
- GET `/api/orderitem/{customerId}?status={status}` - Get order items by customer and status
- PUT `/api/orderitem` - Create order item
- PUT `/api/orderitems` - Create multiple order items
- POST `/api/orderitem` - Update order item

### Checkout Orders
- GET `/api/checkout-orders` - Get all checkout orders
- GET `/api/checkout-orders/{id}` - Get checkout order by ID
- GET `/api/checkout-orders/email/{email}` - Get orders by email
- GET `/api/checkout-orders/email/{email}/status/{status}` - Get orders by email and status
- GET `/api/checkout-orders/status/{status}` - Get orders by status
- POST `/api/checkout-orders` - Create checkout order
- PUT `/api/checkout-orders/{id}` - Update checkout order
- PATCH `/api/checkout-orders/{id}/status` - Update order status
- DELETE `/api/checkout-orders/{id}` - Delete checkout order

### Static Resources
- GET `/images/**` - Serve images

---

**Note:** All endpoints return JSON responses. Error responses follow the format: `{"error": "error message"}`

