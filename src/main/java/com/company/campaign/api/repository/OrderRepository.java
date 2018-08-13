package com.company.campaign.api.repository;

import com.company.campaign.api.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<List<Order>> findByProduct_ProductCode(Long productCode);
    Optional<List<Order>> findByProduct_ProductCodeAndIsCampaignOrderTrue(Long productCode);

}
