# Checkout Orders Feature Setup

## Overview
This feature allows users to checkout orders and track their order status. Users can view pending orders and update their status (received, not_received, cancelled).

## Database Setup

### 1. Create the table
Run the SQL script to create the `checkout_orders` table:

```sql
USE ecom;
SOURCE ecom/ecom_checkout_orders.sql;
```

Or manually run:
```sql
USE ecom;

CREATE TABLE `checkout_orders` (
  `checkoutID` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `checkoutedItems` TEXT DEFAULT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'pending',
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `lastUpdated` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`checkoutID`),
  KEY `idx_email` (`email`),
  KEY `idx_status` (`status`),
  KEY `idx_email_status` (`email`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

## Backend Endpoints

### Base URL: `http://localhost:8082/api/checkout-orders`

#### GET `/api/checkout-orders`
- Get all checkout orders
- Returns: Array of CheckoutOrder objects

#### GET `/api/checkout-orders/{id}`
- Get checkout order by ID
- Returns: CheckoutOrder object

#### GET `/api/checkout-orders/email/{email}`
- Get all orders for a specific email
- Returns: Array of CheckoutOrder objects

#### GET `/api/checkout-orders/email/{email}/status/{status}`
- Get orders by email and status
- Returns: Array of CheckoutOrder objects
- Example: `/api/checkout-orders/email/user@example.com/status/pending`

#### GET `/api/checkout-orders/status/{status}`
- Get all orders by status
- Returns: Array of CheckoutOrder objects

#### POST `/api/checkout-orders`
- Create a new checkout order
- Body:
```json
{
  "email": "user@example.com",
  "checkoutedItems": ["sushiRice", "jasmineRice"],
  "status": "pending"
}
```

#### PUT `/api/checkout-orders/{id}`
- Update a checkout order
- Body: Same as POST

#### PATCH `/api/checkout-orders/{id}/status`
- Update order status only
- Body:
```json
{
  "status": "received"
}
```
- Valid statuses: `pending`, `received`, `not_received`, `cancelled`

#### DELETE `/api/checkout-orders/{id}`
- Delete a checkout order

## Frontend Components

### Orders Modal Component
- **Location**: `frontend/frontend/src/app/orders-modal/`
- **Purpose**: Displays pending orders and allows status updates
- **Features**:
  - Shows all pending orders for the logged-in user
  - Displays order ID, date, items, and status
  - Buttons to update status: Received, Not Received, Cancel

### Updated Components
1. **HeaderComponent**: Added "My Orders" button between username and cart
2. **CartModalComponent**: Updated checkout to save orders to backend
3. **CartService**: Added orders modal state management

## Status Values

- `pending` - Order is pending (default)
- `received` - Order has been received
- `not_received` - Order was not received
- `cancelled` - Order was cancelled

## How It Works

1. **Checkout Process**:
   - User adds items to cart
   - User clicks "Checkout" button
   - System creates a checkout order with:
     - User's email
     - List of product names from cart
     - Status: "pending"
   - Cart is cleared after successful checkout

2. **Viewing Orders**:
   - User clicks "My Orders" button in header
   - Modal opens showing all pending orders
   - Each order shows:
     - Order ID
     - Creation date
     - List of items
     - Current status

3. **Updating Order Status**:
   - User can click one of three buttons:
     - "Mark as Received" - Sets status to "received"
     - "Not Received" - Sets status to "not_received"
     - "Cancel" - Sets status to "cancelled"
   - Status updates are saved to backend immediately

## Testing

1. **Create an order**:
   - Login to the application
   - Add items to cart
   - Click checkout
   - Verify order is created in database

2. **View orders**:
   - Click "My Orders" button
   - Verify pending orders are displayed

3. **Update status**:
   - Click status update buttons
   - Verify status changes in database
   - Verify UI updates accordingly

## Notes

- Orders are filtered by user email (from logged-in user)
- Only pending orders are shown by default in the orders modal
- The `checkoutedItems` field stores a JSON array of product names
- All API responses are JSON format

