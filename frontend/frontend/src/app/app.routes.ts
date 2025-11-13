import { Routes } from '@angular/router';
import { MainBodyComponent } from './main-body/main-body.component';
import { XboxComponent } from './xbox/xbox.component';
import { ProductCategoryComponent } from './product-category/product-category.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { CompanyHomeComponent } from './company-home/company-home.component';
import { ProductOrderComponent } from './product-order/product-order.component';
import { CustomerServiceComponent } from './customer-service/customer-service.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

export const routes: Routes = [
  {path:'',component:MainBodyComponent}, 
  {path:'login',component:LoginComponent}, 
  {path:'register',component:RegisterComponent}, 
  {path:'cart',component:ShoppingCartComponent}, 
  {path:'product',component:ProductCategoryComponent}, 
  {path:'order',component:ProductOrderComponent}, 
  {path:'customer',component:CustomerServiceComponent}, 
  {path:'contact',component:ContactUsComponent}
];
