import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface CheckoutOrder {
  checkoutID?: number;
  email: string;
  checkoutedItems: string; // JSON string
  status: string;
  created?: Date;
  lastUpdated?: Date;
}

export interface CheckoutOrderRequest {
  email: string;
  checkoutedItems: string[];
  status?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CheckoutOrderService {
  private apiUrl = `${environment.apiBaseUrl}/api/checkout-orders`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<CheckoutOrder[]> {
    return this.http.get<CheckoutOrder[]>(this.apiUrl);
  }

  getById(id: number): Observable<CheckoutOrder> {
    return this.http.get<CheckoutOrder>(`${this.apiUrl}/${id}`);
  }

  getByEmail(email: string): Observable<CheckoutOrder[]> {
    return this.http.get<CheckoutOrder[]>(`${this.apiUrl}/email/${email}`);
  }

  getByEmailAndStatus(email: string, status: string): Observable<CheckoutOrder[]> {
    return this.http.get<CheckoutOrder[]>(`${this.apiUrl}/email/${email}/status/${status}`);
  }

  getByStatus(status: string): Observable<CheckoutOrder[]> {
    return this.http.get<CheckoutOrder[]>(`${this.apiUrl}/status/${status}`);
  }

  create(order: CheckoutOrderRequest): Observable<CheckoutOrder> {
    return this.http.post<CheckoutOrder>(this.apiUrl, order);
  }

  update(id: number, order: CheckoutOrderRequest): Observable<CheckoutOrder> {
    return this.http.put<CheckoutOrder>(`${this.apiUrl}/${id}`, order);
  }

  updateStatus(id: number, status: string): Observable<CheckoutOrder> {
    return this.http.patch<CheckoutOrder>(`${this.apiUrl}/${id}/status`, { status });
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}

