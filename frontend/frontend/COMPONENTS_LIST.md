# Complete List of All Angular Components

**Total Components:** 20 components

---

## 1. Root Component

### AppComponent
- **Selector:** `<app-root>`
- **File:** `app.component.ts`
- **Type:** Root component
- **Description:** Main application root component that contains the router outlet and shared components
- **Location:** `src/app/app.component.ts`

---

## 2. Routed Page Components (9 components)

These components are loaded via Angular Router based on the route.

### MainBodyComponent
- **Selector:** `<app-main-body>`
- **Route:** `/` (home page)
- **File:** `main-body/main-body.component.ts`
- **Description:** Main landing page/home page component
- **Location:** `src/app/main-body/`

### ProductCategoryComponent
- **Selector:** `<app-product-category>`
- **Route:** `/product`
- **File:** `product-category/product-category.component.ts`
- **Description:** Displays rice products with variations (1kg, 5kg, 25kg) grouped by name. Users can select variations and add to cart.
- **Location:** `src/app/product-category/`
- **Key Features:**
  - Fetches products from `/api/product` endpoint
  - Groups products by name with variations
  - Displays product images
  - Quantity selection dropdown
  - Add to cart functionality

### ShoppingCartComponent
- **Selector:** `<app-shopping-cart>`
- **Route:** `/cart`
- **File:** `shopping-cart/shopping-cart.component.ts`
- **Description:** Shopping cart page component
- **Location:** `src/app/shopping-cart/`

### ProductOrderComponent
- **Selector:** `<app-product-order>`
- **Route:** `/order`
- **File:** `product-order/product-order.component.ts`
- **Description:** Product order page component
- **Location:** `src/app/product-order/`

### CustomerServiceComponent
- **Selector:** `<app-customer-service>`
- **Route:** `/customer`
- **File:** `customer-service/customer-service.component.ts`
- **Description:** Customer service page component
- **Location:** `src/app/customer-service/`

### ContactUsComponent
- **Selector:** `<app-contact-us>`
- **Route:** `/contact`
- **File:** `contact-us/contact-us.component.ts`
- **Description:** Contact us page component
- **Location:** `src/app/contact-us/`

### LoginComponent
- **Selector:** `<app-login>`
- **Route:** `/login`
- **File:** `login/login.component.ts`
- **Description:** User login page component
- **Location:** `src/app/login/`

### RegisterComponent
- **Selector:** `<app-register>`
- **Route:** `/register`
- **File:** `register/register.component.ts`
- **Description:** User registration page component
- **Location:** `src/app/register/`

### CompanyHomeComponent
- **Selector:** `<app-company-home>`
- **Route:** Not directly routed (may be used as child component)
- **File:** `company-home/company-home.component.ts`
- **Description:** Company home information component
- **Location:** `src/app/company-home/`

---

## 3. Shared/Layout Components (3 components)

These components are used across multiple pages.

### HeaderComponent
- **Selector:** `<app-header>`
- **File:** `header/header.component.ts`
- **Description:** Application header/navigation bar component
- **Location:** `src/app/header/`
- **Key Features:**
  - Displays username when logged in
  - Contains cart button
  - Contains "My Orders" button
  - Contains account button
  - Includes CartModalComponent, AccountModalComponent, and OrdersModalComponent

### FooterComponent
- **Selector:** `<app-footer>`
- **File:** `footer/footer.component.ts`
- **Description:** Application footer component
- **Location:** `src/app/footer/`

### NotificationComponent
- **Selector:** `<app-notification>`
- **File:** `notification/notification.component.ts`
- **Description:** Global notification/toast message component
- **Location:** `src/app/notification/`

---

## 4. Modal Components (4 components)

These components are displayed as overlays/modals.

### CartModalComponent
- **Selector:** `<app-cart-modal>`
- **File:** `cart-modal/cart-modal.component.ts`
- **Description:** Shopping cart modal that displays cart items
- **Location:** `src/app/cart-modal/`
- **Key Features:**
  - Displays cart items
  - Remove items from cart
  - Checkout button (creates checkout order and empties cart)
  - Shows "Order checked out" popup

### AccountModalComponent
- **Selector:** `<app-account-modal>`
- **File:** `account-modal/account-modal.component.ts`
- **Description:** User account management modal
- **Location:** `src/app/account-modal/`
- **Key Features:**
  - User profile information
  - Logout functionality
  - Uses ConfirmationDialogComponent for confirmations

### OrdersModalComponent
- **Selector:** `<app-orders-modal>`
- **File:** `orders-modal/orders-modal.component.ts`
- **Description:** Modal displaying user's checkout orders with status management
- **Location:** `src/app/orders-modal/`
- **Key Features:**
  - Displays all orders for logged-in user
  - Filter by status (pending, received, not_received, cancelled)
  - Update order status
  - Shows order details and items

### ConfirmationDialogComponent
- **Selector:** `<app-confirmation-dialog>`
- **File:** `confirmation-dialog/confirmation-dialog.component.ts`
- **Description:** Reusable confirmation dialog component
- **Location:** `src/app/confirmation-dialog/`
- **Usage:** Used by AccountModalComponent for logout confirmation

---

## 5. Additional/Utility Components (3 components)

These components may be used as child components or in specific contexts.

### MainHeaderComponent
- **Selector:** `<app-main-header>`
- **File:** `main-header/main-header.component.ts`
- **Description:** Main header component (may be used on specific pages)
- **Location:** `src/app/main-header/`

### GalleryComponent
- **Selector:** `<app-gallery>`
- **File:** `gallery/gallery.component.ts`
- **Description:** Image gallery component
- **Location:** `src/app/gallery/`

### XboxComponent
- **Selector:** `<app-xbox>`
- **File:** `xbox/xbox.component.ts`
- **Description:** Xbox-related component (specific functionality)
- **Location:** `src/app/xbox/`

---

## Component Summary by Category

### Routed Components (9)
1. MainBodyComponent - `/`
2. ProductCategoryComponent - `/product`
3. ShoppingCartComponent - `/cart`
4. ProductOrderComponent - `/order`
5. CustomerServiceComponent - `/customer`
6. ContactUsComponent - `/contact`
7. LoginComponent - `/login`
8. RegisterComponent - `/register`
9. CompanyHomeComponent - (child component)

### Shared Components (3)
1. HeaderComponent
2. FooterComponent
3. NotificationComponent

### Modal Components (4)
1. CartModalComponent
2. AccountModalComponent
3. OrdersModalComponent
4. ConfirmationDialogComponent

### Utility Components (3)
1. MainHeaderComponent
2. GalleryComponent
3. XboxComponent

### Root Component (1)
1. AppComponent

---

## Component Relationships

### AppComponent Contains:
- HeaderComponent
- Router Outlet (for routed components)
- NotificationComponent
- FooterComponent

### HeaderComponent Contains:
- CartModalComponent
- AccountModalComponent
- OrdersModalComponent

### AccountModalComponent Uses:
- ConfirmationDialogComponent

---

## Component File Structure

Each component typically has:
- `{component-name}.component.ts` - TypeScript component class
- `{component-name}.component.html` - HTML template
- `{component-name}.component.css` - Component styles
- `{component-name}.component.spec.ts` - Unit tests (optional)

---

**Note:** All components are standalone components (Angular 17+ style) and use the `standalone: true` flag in their `@Component` decorator.


