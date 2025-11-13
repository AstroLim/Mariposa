import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductWithVariations } from '../model/product-with-variations';
import { ProductVariation } from '../model/product-variation';
import { Product } from '../model/product';
import { ProductService } from '../service/product.service';
import { CartService } from '../service/cart.service';
import { NotificationService } from '../service/notification.service';
import { environment } from '../../environments/environment';


@Component({
  selector: 'app-product-category',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.css']
})

export class ProductCategoryComponent implements OnInit  {
      public productsWithVariations: ProductWithVariations[] = [];
      public isLoading: boolean = true;
      public hasError: boolean = false;
      public errorMessage: string = '';
      public selectedVariations: { [key: string]: ProductVariation } = {};
 
        constructor(
          private productService: ProductService,
          private cartService: CartService,
          private notificationService: NotificationService
        ) {
        }
        
        addToCart(product: ProductWithVariations): void {
          const selectedVariation = this.selectedVariations[product.name];
          if (!selectedVariation) {
            // Default to first variation if none selected
            if (product.variations && product.variations.length > 0) {
              this.selectedVariations[product.name] = product.variations[0];
              this.addToCart(product);
            }
            return;
          }
          
          console.log('Adding to cart:', product.name, selectedVariation);
          // Convert to Product format for cart
          const productToAdd: Product = {
            id: selectedVariation.id,
            name: product.name + ' - ' + selectedVariation.quantity,
            description: product.description,
            categoryName: product.name,
            unitOfMeasure: selectedVariation.quantity,
            price: selectedVariation.price.toString(),
            imageFile: product.imageFile || ''
          };
          
          this.cartService.addToCart(productToAdd, 1);
          this.notificationService.showSuccess(`${product.name} (${selectedVariation.quantity}) added to cart!`);
        }
        
        ngOnInit(): void {
          console.log("ngOnInit called");
          this.isLoading = true;
          this.hasError = false;
          this.errorMessage = '';
          
          this.productService.getData().subscribe({
            next: (data) => {
              console.log("Product data received:", data);
              console.log("Data type:", typeof data);
              console.log("Is array:", Array.isArray(data));
              
              if (data && Array.isArray(data)) {
                this.productsWithVariations = data;
                // Initialize selected variations to first variation for each product
                this.productsWithVariations.forEach(product => {
                  if (product.variations && product.variations.length > 0) {
                    this.selectedVariations[product.name] = product.variations[0];
                  }
                });
                console.log("Total products found:", this.productsWithVariations.length);
              } else {
                console.warn("Data is not an array or is null:", data);
                this.productsWithVariations = [];
              }
              this.isLoading = false;
            },
            error: (error) => {
              console.error("Error loading products:", error);
              console.error("Error details:", JSON.stringify(error));
              this.productsWithVariations = [];
              this.hasError = true;
              this.isLoading = false;
              
              // Extract meaningful error message
              if (error.status === 0) {
                this.errorMessage = 'Cannot connect to backend server. Please make sure the Spring Boot server is running on http://localhost:8082';
              } else if (error.status === 500) {
                this.errorMessage = `Server error (500): ${error.error?.error || error.message || 'Internal server error. Check if the database is configured correctly.'}`;
              } else if (error.error?.error) {
                this.errorMessage = `Error: ${error.error.error}`;
              } else {
                this.errorMessage = `Error ${error.status || 'Unknown'}: ${error.message || 'Failed to load products'}`;
              }
            }
          });
        }
        
        compareVariations(v1: ProductVariation, v2: ProductVariation): boolean {
          return v1 && v2 ? v1.id === v2.id : v1 === v2;
        }
        
        getImageUrl(imageFile: string): string {
          if (!imageFile) {
            console.log('No image file provided, using default');
            return '../../assets/rice-bag.png';
          }
          // If imageFile is already a full URL, return it
          if (imageFile.startsWith('http://') || imageFile.startsWith('https://')) {
            console.log('Using full URL:', imageFile);
            return imageFile;
          }
          // If imageFile is already a path starting with /, use it directly
          if (imageFile.startsWith('/')) {
            const url = `${environment.apiBaseUrl}${imageFile}`;
            console.log('Using absolute path:', url);
            return url;
          }
          // Images are served from resources/static/images/
          // Store path in database (e.g., "rice/basmati.jpg" or just "basmati.jpg")
          const url = `${environment.apiBaseUrl}/images/${imageFile}`;
          console.log('Image URL constructed:', url, 'from imageFile:', imageFile);
          return url;
        }
  }
