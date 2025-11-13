import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { BaseHttpService } from './base-http.service';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { CartItem } from '../model/cart-item';
import { Product } from '../model/product';

@Injectable({
  providedIn: 'root'
})
export class CartService extends BaseHttpService {
  private cartItemsSubject = new BehaviorSubject<CartItem[]>([]);
  public cartItems$: Observable<CartItem[]> = this.cartItemsSubject.asObservable();
  
  private modalOpenSubject = new BehaviorSubject<boolean>(false);
  public modalOpen$: Observable<boolean> = this.modalOpenSubject.asObservable();

  private accountModalOpenSubject = new BehaviorSubject<boolean>(false);
  public accountModalOpen$: Observable<boolean> = this.accountModalOpenSubject.asObservable();

  private ordersModalOpenSubject = new BehaviorSubject<boolean>(false);
  public ordersModalOpen$: Observable<boolean> = this.ordersModalOpenSubject.asObservable();

  constructor(
    protected override http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    super(http, '/api/cart');
    if (isPlatformBrowser(this.platformId)) {
      this.loadCartFromStorage();
    }
  }

  private loadCartFromStorage(): void {
    if (isPlatformBrowser(this.platformId)) {
      const savedCart = localStorage.getItem('cart');
      if (savedCart) {
        try {
          const items = JSON.parse(savedCart);
          this.cartItemsSubject.next(items);
        } catch (e) {
          console.error('Error loading cart from storage:', e);
        }
      }
    }
  }

  private saveCartToStorage(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('cart', JSON.stringify(this.cartItemsSubject.value));
    }
  }

  addToCart(product: Product, quantity: number = 1): void {
    const currentItems = this.cartItemsSubject.value;
    const existingItemIndex = currentItems.findIndex(
      item => item.product.id === product.id
    );

    if (existingItemIndex >= 0) {
      // Update existing item
      const updatedItems = [...currentItems];
      updatedItems[existingItemIndex].quantity += quantity;
      updatedItems[existingItemIndex].total = 
        updatedItems[existingItemIndex].quantity * parseFloat(updatedItems[existingItemIndex].product.price);
      this.cartItemsSubject.next(updatedItems);
    } else {
      // Add new item
      const newItem: CartItem = {
        product: product,
        quantity: quantity,
        total: quantity * parseFloat(product.price)
      };
      this.cartItemsSubject.next([...currentItems, newItem]);
    }
    this.saveCartToStorage();
  }

  removeFromCart(productId: number): void {
    const currentItems = this.cartItemsSubject.value;
    const updatedItems = currentItems.filter(item => item.product.id !== productId);
    this.cartItemsSubject.next(updatedItems);
    this.saveCartToStorage();
  }

  updateQuantity(productId: number, quantity: number): void {
    if (quantity <= 0) {
      this.removeFromCart(productId);
      return;
    }

    const currentItems = this.cartItemsSubject.value;
    const updatedItems = currentItems.map(item => {
      if (item.product.id === productId) {
        return {
          ...item,
          quantity: quantity,
          total: quantity * parseFloat(item.product.price)
        };
      }
      return item;
    });
    this.cartItemsSubject.next(updatedItems);
    this.saveCartToStorage();
  }

  getTotal(): number {
    return this.cartItemsSubject.value.reduce((sum, item) => sum + item.total, 0);
  }

  getCartItems(): CartItem[] {
    return this.cartItemsSubject.value;
  }

  clearCart(): void {
    this.cartItemsSubject.next([]);
    this.saveCartToStorage();
  }

  getCartItemCount(): number {
    return this.cartItemsSubject.value.reduce((sum, item) => sum + item.quantity, 0);
  }

  openModal(): void {
    this.modalOpenSubject.next(true);
  }

  closeModal(): void {
    this.modalOpenSubject.next(false);
  }

  openAccountModal(): void {
    this.accountModalOpenSubject.next(true);
  }

  closeAccountModal(): void {
    this.accountModalOpenSubject.next(false);
  }

  openOrdersModal(): void {
    this.ordersModalOpenSubject.next(true);
  }

  closeOrdersModal(): void {
    this.ordersModalOpenSubject.next(false);
  }
}
