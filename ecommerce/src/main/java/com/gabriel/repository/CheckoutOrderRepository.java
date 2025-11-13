package com.gabriel.repository;

import com.gabriel.entity.CheckoutOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckoutOrderRepository extends JpaRepository<CheckoutOrder, Integer> {
    List<CheckoutOrder> findByEmail(String email);
    List<CheckoutOrder> findByEmailAndStatus(String email, String status);
    List<CheckoutOrder> findByStatus(String status);
    Optional<CheckoutOrder> findByCheckoutID(Integer checkoutID);
}

