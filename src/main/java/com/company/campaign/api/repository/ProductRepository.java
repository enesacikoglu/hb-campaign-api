package com.company.campaign.api.repository;

import com.company.campaign.api.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
