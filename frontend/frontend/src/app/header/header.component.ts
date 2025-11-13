import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService, User } from '../service/auth.service';
import { CartService } from '../service/cart.service';
import { CartModalComponent } from '../cart-modal/cart-modal.component';
import { AccountModalComponent } from '../account-modal/account-modal.component';
import { OrdersModalComponent } from '../orders-modal/orders-modal.component';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule, CartModalComponent, AccountModalComponent, OrdersModalComponent],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy  {
  isLoggedIn: boolean = false;
  username: string = '';
  private loginSubscription?: Subscription;
  private userSubscription?: Subscription;

  constructor(
    private authService: AuthService,
    private cartService: CartService
  ) {
  }

  ngOnInit(): void {
      // Subscribe to login status changes
      this.loginSubscription = this.authService.isLoggedIn$.subscribe(status => {
        this.isLoggedIn = status;
      });
      // Subscribe to current user changes
      this.userSubscription = this.authService.currentUser$.subscribe(user => {
        if (user && user.username) {
          this.username = user.username;
        } else {
          this.username = '';
        }
      });
      // Check initial login status
      this.isLoggedIn = this.authService.isLoggedIn();
      const currentUser = this.authService.getCurrentUser();
      if (currentUser && currentUser.username) {
        this.username = currentUser.username;
      }
  }

  ngOnDestroy(): void {
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe();
    }
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  openCart(): void {
    this.cartService.openModal();
  }

  openAccount(): void {
    this.cartService.openAccountModal();
  }

  openOrders(): void {
    this.cartService.openOrdersModal();
  }
}
