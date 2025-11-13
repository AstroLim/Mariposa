import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CheckoutOrderService, CheckoutOrder } from '../service/checkout-order.service';
import { AuthService } from '../service/auth.service';
import { CartService } from '../service/cart.service';
import { NotificationService } from '../service/notification.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-orders-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './orders-modal.component.html',
  styleUrls: ['./orders-modal.component.css']
})
export class OrdersModalComponent implements OnInit, OnDestroy {
  orders: CheckoutOrder[] = [];
  isOpen: boolean = false;
  isLoading: boolean = false;
  userEmail: string = '';
  private modalSubscription?: Subscription;

  constructor(
    private checkoutOrderService: CheckoutOrderService,
    private authService: AuthService,
    private cartService: CartService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.modalSubscription = this.cartService.ordersModalOpen$.subscribe(isOpen => {
      this.isOpen = isOpen;
      if (isOpen) {
        this.loadOrders();
      }
    });

    // Get current user email
    const currentUser = this.authService.getCurrentUser();
    if (currentUser && currentUser.email) {
      this.userEmail = currentUser.email;
    }
  }

  ngOnDestroy(): void {
    if (this.modalSubscription) {
      this.modalSubscription.unsubscribe();
    }
  }

  loadOrders(): void {
    if (!this.userEmail) {
      const currentUser = this.authService.getCurrentUser();
      if (currentUser && currentUser.email) {
        this.userEmail = currentUser.email;
      } else {
        this.notificationService.showError('User email not found');
        return;
      }
    }

    this.isLoading = true;
    // Get pending orders by default, but can be extended to show all
    this.checkoutOrderService.getByEmailAndStatus(this.userEmail, 'pending').subscribe({
      next: (orders) => {
        this.orders = orders;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading orders:', error);
        this.notificationService.showError('Failed to load orders');
        this.isLoading = false;
      }
    });
  }

  closeModal(): void {
    this.cartService.closeOrdersModal();
  }

  onOverlayClick(event: Event): void {
    if (event.target === event.currentTarget) {
      this.closeModal();
    }
  }

  updateStatus(orderId: number | undefined, status: string): void {
    if (!orderId) {
      return;
    }

    this.checkoutOrderService.updateStatus(orderId, status).subscribe({
      next: () => {
        this.notificationService.showSuccess(`Order status updated to ${status}`);
        this.loadOrders(); // Reload orders
      },
      error: (error) => {
        console.error('Error updating order status:', error);
        this.notificationService.showError('Failed to update order status');
      }
    });
  }

  getCheckoutedItemsList(order: CheckoutOrder): string[] {
    try {
      if (order.checkoutedItems) {
        return JSON.parse(order.checkoutedItems);
      }
    } catch (e) {
      console.error('Error parsing checkoutedItems:', e);
    }
    return [];
  }

  getStatusClass(status: string): string {
    switch (status.toLowerCase()) {
      case 'received':
        return 'status-received';
      case 'not_received':
        return 'status-not-received';
      case 'cancelled':
        return 'status-cancelled';
      case 'pending':
      default:
        return 'status-pending';
    }
  }
}

