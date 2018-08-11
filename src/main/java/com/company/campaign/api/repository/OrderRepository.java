package com.company.campaign.api.repository;

import com.company.campaign.api.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByProduct_ProductCode(Long productCode);
}
