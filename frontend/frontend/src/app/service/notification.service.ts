import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface Notification {
  message: string;
  type: 'success' | 'error' | 'info';
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notificationSubject = new BehaviorSubject<Notification | null>(null);
  public notification$: Observable<Notification | null> = this.notificationSubject.asObservable();

  showSuccess(message: string): void {
    this.notificationSubject.next({ message, type: 'success' });
    setTimeout(() => this.hide(), 3000);
  }

  showError(message: string): void {
    this.notificationSubject.next({ message, type: 'error' });
    setTimeout(() => this.hide(), 3000);
  }

  showInfo(message: string): void {
    this.notificationSubject.next({ message, type: 'info' });
    setTimeout(() => this.hide(), 3000);
  }

  hide(): void {
    this.notificationSubject.next(null);
  }
}

