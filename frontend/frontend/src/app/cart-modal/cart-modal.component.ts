import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartService } from '../service/cart.service';
import { CartItem } from '../model/cart-item';
import { Subscription } from 'rxjs';
import { NotificationService } from '../service/notification.service';
import { CheckoutOrderService } from '../service/checkout-order.service';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-cart-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart-modal.component.html',
  styleUrls: ['./cart-modal.component.css']
})
export class CartModalComponent implements OnInit, OnDestroy {
  cartItems: CartItem[] = [];
  total: number = 0;
  isOpen: boolean = false;
  private cartSubscription?: Subscription;
  private modalSubscription?: Subscription;

  constructor(
    private cartService: CartService,
    private notificationService: NotificationService,
    private checkoutOrderService: CheckoutOrderService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.cartSubscription = this.cartService.cartItems$.subscribe(items => {
      this.cartItems = items;
      this.total = this.cartService.getTotal();
    });

    this.modalSubscription = this.cartService.modalOpen$.subscribe(isOpen => {
      this.isOpen = isOpen;
    });
  }

  ngOnDestroy(): void {
    if (this.cartSubscription) {
      this.cartSubscription.unsubscribe();
    }
    if (this.modalSubscription) {
      this.modalSubscription.unsubscribe();
    }
  }

  closeModal(): void {
    this.cartService.closeModal();
  }

  removeItem(productId: number): void {
    this.cartService.removeFromCart(productId);
  }

  updateQuantity(productId: number, change: number): void {
    const item = this.cartItems.find(i => i.product.id === productId);
    if (item) {
      const newQuantity = item.quantity + change;
      this.cartService.updateQuantity(productId, newQuantity);
    }
  }

  onOverlayClick(event: Event): void {
    if (event.target === event.currentTarget) {
      this.closeModal();
    }
  }

  checkout(): void {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser || !currentUser.email) {
      this.notificationService.showError('Please login to checkout');
      return;
    }

    // Extract product names from cart items
    const checkoutedItems = this.cartItems.map(item => item.product.name);

    // Create checkout order
    this.checkoutOrderService.create({
      email: currentUser.email,
      checkoutedItems: checkoutedItems,
      status: 'pending'
    }).subscribe({
      next: () => {
        this.cartService.clearCart();
        this.notificationService.showSuccess('Order checked out successfully!');
        this.closeModal();
      },
      error: (error) => {
        console.error('Error creating checkout order:', error);
        this.notificationService.showError('Failed to checkout. Please try again.');
      }
    });
  }
}

