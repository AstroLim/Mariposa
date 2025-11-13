import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../service/auth.service';
import { CartService } from '../service/cart.service';
import { NotificationService } from '../service/notification.service';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { Subscription } from 'rxjs';
import { environment } from '../../environments/environment';
@Component({
  selector: 'app-account-modal',
  standalone: true,
  imports: [CommonModule, FormsModule, ConfirmationDialogComponent],
  templateUrl: './account-modal.component.html',
  styleUrls: ['./account-modal.component.css']
})
export class AccountModalComponent implements OnInit, OnDestroy {
  isOpen: boolean = false;
  username: string = 'User';
  email: string = 'user@example.com';
  password: string = '';
  showPassword: boolean = false;
  showConfirmationDialog: boolean = false;
  isUpdating: boolean = false;
  private originalUsername: string = '';
  private originalEmail: string = '';
  private modalSubscription?: Subscription;

  constructor(
    private authService: AuthService,
    private cartService: CartService,
    private router: Router,
    private http: HttpClient,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.modalSubscription = this.cartService.accountModalOpen$.subscribe(isOpen => {
      this.isOpen = isOpen;
      if (isOpen) {
        this.loadUserData();
      }
    });
  }

  ngOnDestroy(): void {
    if (this.modalSubscription) {
      this.modalSubscription.unsubscribe();
    }
  }

  loadUserData(): void {
    // Load user data from AuthService
    const currentUser = this.authService.getCurrentUser();
    if (currentUser) {
      if (currentUser.email) {
        this.email = currentUser.email;
        this.originalEmail = currentUser.email;
      }
      if (currentUser.username) {
        this.username = currentUser.username;
        this.originalUsername = currentUser.username;
      }
    } else {
      // Fallback to localStorage if user not in service
      if (typeof localStorage !== 'undefined') {
        const savedEmail = localStorage.getItem('userEmail');
        const savedUsername = localStorage.getItem('username');
        if (savedEmail) {
          this.email = savedEmail;
          this.originalEmail = savedEmail;
        }
        if (savedUsername) {
          this.username = savedUsername;
          this.originalUsername = savedUsername;
        }
      }
    }
    // Reset password field
    this.password = '';
  }

  closeModal(): void {
    this.cartService.closeAccountModal();
  }

  onOverlayClick(event: Event): void {
    if (event.target === event.currentTarget) {
      this.closeModal();
    }
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  updateAccount(): void {
    // Trim whitespace from inputs
    const trimmedUsername = this.username.trim();
    const trimmedEmail = this.email.trim();
    const trimmedPassword = this.password.trim();
    
    // Check if there are any changes
    const hasChanges = 
      trimmedUsername !== this.originalUsername ||
      trimmedEmail !== this.originalEmail ||
      (trimmedPassword && trimmedPassword.length > 0);
    
    if (!hasChanges) {
      this.notificationService.showInfo('No changes to update');
      return;
    }
    
    // Validate inputs
    if (!trimmedUsername || trimmedUsername.length === 0) {
      this.notificationService.showError('Username cannot be empty');
      return;
    }
    
    if (!trimmedEmail || trimmedEmail.length === 0) {
      this.notificationService.showError('Email cannot be empty');
      return;
    }
    
    // Basic email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(trimmedEmail)) {
      this.notificationService.showError('Please enter a valid email address');
      return;
    }
    
    // Show confirmation dialog
    this.showConfirmationDialog = true;
  }

  onConfirmUpdate(): void {
    this.showConfirmationDialog = false;
    this.isUpdating = true;
    
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser || !currentUser.id) {
      this.notificationService.showError('User not found. Please login again.');
      this.isUpdating = false;
      return;
    }
    
    // Trim whitespace from inputs
    const trimmedUsername = this.username.trim();
    const trimmedEmail = this.email.trim();
    const trimmedPassword = this.password.trim();
    
    // Prepare update data with trimmed values
    const updateData: any = {
      username: trimmedUsername,
      email: trimmedEmail
    };
    
    // Only include password if it's provided
    if (trimmedPassword && trimmedPassword.length > 0) {
      updateData.password = trimmedPassword;
    }
    
    console.log('Sending update request:', {
      userId: currentUser.id,
      updateData: { ...updateData, password: updateData.password ? '***' : undefined }
    });
    console.log('Full update data (without password mask):', updateData);
    console.log('API URL:', `${environment.apiBaseUrl}/api/auth/update/${currentUser.id}`);
    
    // Call backend API to update user
    this.http.put<any>(`${environment.apiBaseUrl}/api/auth/update/${currentUser.id}`, updateData)
      .subscribe({
        next: (updatedUser: any) => {
          console.log('Account updated successfully:', updatedUser);
          
          // Update local storage
          if (typeof localStorage !== 'undefined') {
            localStorage.setItem('currentUser', JSON.stringify(updatedUser));
            if (updatedUser.email) {
              localStorage.setItem('userEmail', updatedUser.email);
            }
            if (updatedUser.username) {
              localStorage.setItem('username', updatedUser.username);
            }
          }
          
          // Update AuthService user data
          this.authService.updateCurrentUser(updatedUser);
          
          // Update component values with response data
          if (updatedUser.username) {
            this.username = updatedUser.username;
            this.originalUsername = updatedUser.username;
          }
          if (updatedUser.email) {
            this.email = updatedUser.email;
            this.originalEmail = updatedUser.email;
          }
          
          // Clear password field
          this.password = '';
          
          this.notificationService.showSuccess('Account updated successfully!');
          this.isUpdating = false;
        },
        error: (error) => {
          console.error('Update error:', error);
          console.error('Error details:', JSON.stringify(error, null, 2));
          let errorMessage = 'Failed to update account. Please try again.';
          if (error.error && error.error.message) {
            errorMessage = error.error.message;
          } else if (error.message) {
            errorMessage = error.message;
          }
          this.notificationService.showError(errorMessage);
          this.isUpdating = false;
        }
      });
  }

  onCancelUpdate(): void {
    this.showConfirmationDialog = false;
  }

  logout(): void {
    this.authService.logout();
    this.closeModal();
    this.router.navigate(['/']);
  }
}

