package com.gabriel.controller;

import com.gabriel.entity.RiceProductData;
import com.gabriel.repository.RiceProductDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/riceproduct", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class RiceProductController {

    @Autowired
    private RiceProductDataRepository riceProductDataRepository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<RiceProductData> riceProducts = new ArrayList<>();
            riceProductDataRepository.findAll().forEach(riceProducts::add);
            log.info("Retrieved {} rice products", riceProducts.size());
            return ResponseEntity.ok(riceProducts);
        } catch (Exception ex) {
            log.error("Failed to retrieve rice products: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Optional<RiceProductData> riceProduct = riceProductDataRepository.findById(id);
            if (riceProduct.isPresent()) {
                log.info("Retrieved rice product with ID: {}", id);
                return ResponseEntity.ok(riceProduct.get());
            } else {
                log.warn("Rice product not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Rice product not found with ID: " + id));
            }
        } catch (Exception ex) {
            log.error("Failed to retrieve rice product with ID {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RiceProductData riceProductData) {
        try {
            log.info("Creating rice product: {}", riceProductData.getRiceName());
            RiceProductData savedRiceProduct = riceProductDataRepository.save(riceProductData);
            log.info("Rice product created successfully with ID: {}", savedRiceProduct.getRiceID());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRiceProduct);
        } catch (Exception ex) {
            log.error("Failed to create rice product: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody RiceProductData riceProductData) {
        try {
            Optional<RiceProductData> existingRiceProduct = riceProductDataRepository.findById(id);
            if (existingRiceProduct.isPresent()) {
                riceProductData.setRiceID(id);
                RiceProductData updatedRiceProduct = riceProductDataRepository.save(riceProductData);
                log.info("Rice product updated successfully with ID: {}", id);
                return ResponseEntity.ok(updatedRiceProduct);
            } else {
                log.warn("Rice product not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Rice product not found with ID: " + id));
            }
        } catch (Exception ex) {
            log.error("Failed to update rice product with ID {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Optional<RiceProductData> riceProduct = riceProductDataRepository.findById(id);
            if (riceProduct.isPresent()) {
                riceProductDataRepository.deleteById(id);
                log.info("Rice product deleted successfully with ID: {}", id);
                return ResponseEntity.ok().body(Map.of("message", "Rice product deleted successfully"));
            } else {
                log.warn("Rice product not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Rice product not found with ID: " + id));
            }
        } catch (Exception ex) {
            log.error("Failed to delete rice product with ID {}: {}", id, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        try {
            List<RiceProductData> riceProducts = new ArrayList<>();
            riceProductDataRepository.findAll().forEach(riceProduct -> {
                if (riceProduct.getRiceName() != null && riceProduct.getRiceName().contains(name)) {
                    riceProducts.add(riceProduct);
                }
            });
            log.info("Retrieved {} rice products with name containing: {}", riceProducts.size(), name);
            return ResponseEntity.ok(riceProducts);
        } catch (Exception ex) {
            log.error("Failed to retrieve rice products by name: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving rice products: " + ex.getMessage());
        }
    }
}

