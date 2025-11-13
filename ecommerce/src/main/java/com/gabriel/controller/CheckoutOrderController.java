package com.gabriel.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.entity.CheckoutOrder;
import com.gabriel.model.CheckoutOrderRequest;
import com.gabriel.repository.CheckoutOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/checkout-orders", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class CheckoutOrderController {

    @Autowired
    private CheckoutOrderRepository checkoutOrderRepository;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<CheckoutOrder> orders = checkoutOrderRepository.findAll();
            log.info("Retrieved {} checkout orders", orders.size());
            return ResponseEntity.ok(orders);
        } catch (Exception ex) {
            log.error("Failed to retrieve checkout orders: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Optional<CheckoutOrder> order = checkoutOrderRepository.findById(id);
            if (order.isPresent()) {
                log.info("Retrieved checkout order with ID: {}", id);
                return ResponseEntity.ok(order.get());
            } else {
                log.warn("Checkout order not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Checkout order not found with ID: " + id));
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve checkout order with ID {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        try {
            List<CheckoutOrder> orders = checkoutOrderRepository.findByEmail(email);
            log.info("Retrieved {} checkout orders for email: {}", orders.size(), email);
            return ResponseEntity.ok(orders);
        } catch (Exception ex) {
            log.error("Failed to retrieve checkout orders for email {}: {}", email, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @GetMapping("/email/{email}/status/{status}")
    public ResponseEntity<?> getByEmailAndStatus(@PathVariable String email, @PathVariable String status) {
        try {
            List<CheckoutOrder> orders = checkoutOrderRepository.findByEmailAndStatus(email, status);
            log.info("Retrieved {} checkout orders for email: {} with status: {}", orders.size(), email, status);
            return ResponseEntity.ok(orders);
        } catch (Exception ex) {
            log.error("Failed to retrieve checkout orders: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable String status) {
        try {
            List<CheckoutOrder> orders = checkoutOrderRepository.findByStatus(status);
            log.info("Retrieved {} checkout orders with status: {}", orders.size(), status);
            return ResponseEntity.ok(orders);
        } catch (Exception ex) {
            log.error("Failed to retrieve checkout orders: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CheckoutOrderRequest request) {
        try {
            CheckoutOrder checkoutOrder = new CheckoutOrder();
            checkoutOrder.setEmail(request.getEmail());
            checkoutOrder.setStatus(request.getStatus() != null ? request.getStatus() : "pending");
            
            // Convert list to JSON string
            if (request.getCheckoutedItems() != null) {
                String itemsJson = objectMapper.writeValueAsString(request.getCheckoutedItems());
                checkoutOrder.setCheckoutedItems(itemsJson);
            }
            
            CheckoutOrder savedOrder = checkoutOrderRepository.save(checkoutOrder);
            log.info("Checkout order created successfully with ID: {}", savedOrder.getCheckoutID());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
        } catch (Exception ex) {
            log.error("Failed to create checkout order: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CheckoutOrderRequest request) {
        try {
            Optional<CheckoutOrder> existingOrder = checkoutOrderRepository.findById(id);
            if (existingOrder.isPresent()) {
                CheckoutOrder order = existingOrder.get();
                
                if (request.getEmail() != null) {
                    order.setEmail(request.getEmail());
                }
                if (request.getStatus() != null) {
                    order.setStatus(request.getStatus());
                }
                if (request.getCheckoutedItems() != null) {
                    String itemsJson = objectMapper.writeValueAsString(request.getCheckoutedItems());
                    order.setCheckoutedItems(itemsJson);
                }
                
                CheckoutOrder updatedOrder = checkoutOrderRepository.save(order);
                log.info("Checkout order updated successfully with ID: {}", id);
                return ResponseEntity.ok(updatedOrder);
            } else {
                log.warn("Checkout order not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Checkout order not found with ID: " + id));
            }
        } catch (Exception ex) {
            log.error("Failed to update checkout order with ID {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestBody Map<String, String> request) {
        try {
            Optional<CheckoutOrder> existingOrder = checkoutOrderRepository.findById(id);
            if (existingOrder.isPresent()) {
                CheckoutOrder order = existingOrder.get();
                String newStatus = request.get("status");
                if (newStatus != null && (newStatus.equals("pending") || newStatus.equals("received") 
                    || newStatus.equals("not_received") || newStatus.equals("cancelled"))) {
                    order.setStatus(newStatus);
                    CheckoutOrder updatedOrder = checkoutOrderRepository.save(order);
                    log.info("Checkout order status updated to {} for ID: {}", newStatus, id);
                    return ResponseEntity.ok(updatedOrder);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Map.of("error", "Invalid status. Must be: pending, received, not_received, or cancelled"));
                }
            } else {
                log.warn("Checkout order not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Checkout order not found with ID: " + id));
            }
        } catch (Exception ex) {
            log.error("Failed to update status for checkout order with ID {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Optional<CheckoutOrder> order = checkoutOrderRepository.findById(id);
            if (order.isPresent()) {
                checkoutOrderRepository.deleteById(id);
                log.info("Checkout order deleted successfully with ID: {}", id);
                return ResponseEntity.ok().body(Map.of("message", "Checkout order deleted successfully"));
            } else {
                log.warn("Checkout order not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Checkout order not found with ID: " + id));
            }
        } catch (Exception ex) {
            log.error("Failed to delete checkout order with ID {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }
}

