import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';

export interface User {
  id?: number;
  username?: string;
  email?: string;
  password?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isLoggedInSubject = new BehaviorSubject<boolean>(false);
  public isLoggedIn$: Observable<boolean> = this.isLoggedInSubject.asObservable();
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private http: HttpClient
  ) {
    // Only check login status if we're in the browser
    if (isPlatformBrowser(this.platformId)) {
      this.isLoggedInSubject.next(this.checkLoginStatus());
      const savedUser = this.getSavedUser();
      if (savedUser) {
        this.currentUserSubject.next(savedUser);
      }
    }
  }

  private checkLoginStatus(): boolean {
    // Check if user is logged in (from localStorage or session)
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('isLoggedIn') === 'true';
    }
    return false;
  }

  private getSavedUser(): User | null {
    if (isPlatformBrowser(this.platformId)) {
      const userStr = localStorage.getItem('currentUser');
      if (userStr) {
        try {
          return JSON.parse(userStr);
        } catch (e) {
          return null;
        }
      }
    }
    return null;
  }

  private saveUser(user: User): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('currentUser', JSON.stringify(user));
      if (user.email) {
        localStorage.setItem('userEmail', user.email);
      }
      if (user.username) {
        localStorage.setItem('username', user.username);
      }
    }
  }

  login(email: string, password: string): Observable<User> {
    return this.http.post<User>(`${environment.apiBaseUrl}/api/auth/login`, {
      email,
      password
    }).pipe(
      tap((user: User) => {
        // Mark user as logged in
        if (isPlatformBrowser(this.platformId)) {
          localStorage.setItem('isLoggedIn', 'true');
        }
        this.saveUser(user);
        this.isLoggedInSubject.next(true);
        this.currentUserSubject.next(user);
      }),
      catchError((error) => {
        console.error('Login error:', error);
        return throwError(() => error);
      })
    );
  }

  register(user: User): Observable<User> {
    return this.http.post<User>(`${environment.apiBaseUrl}/api/auth/register`, user).pipe(
      tap((registeredUser: User) => {
        // Mark user as logged in after registration
        if (isPlatformBrowser(this.platformId)) {
          localStorage.setItem('isLoggedIn', 'true');
        }
        this.saveUser(registeredUser);
        this.isLoggedInSubject.next(true);
        this.currentUserSubject.next(registeredUser);
      }),
      catchError((error) => {
        console.error('Registration error:', error);
        return throwError(() => error);
      })
    );
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('isLoggedIn');
      localStorage.removeItem('currentUser');
      localStorage.removeItem('userEmail');
      localStorage.removeItem('username');
    }
    this.isLoggedInSubject.next(false);
    this.currentUserSubject.next(null);
  }

  isLoggedIn(): boolean {
    return this.isLoggedInSubject.value;
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  updateCurrentUser(user: User): void {
    if (isPlatformBrowser(this.platformId)) {
      this.saveUser(user);
    }
    this.currentUserSubject.next(user);
  }
}

