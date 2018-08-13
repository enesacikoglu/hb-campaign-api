package com.company.campaign.api.repository;

import com.company.campaign.api.domain.CampaignProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CampaignProductRepository extends CrudRepository<CampaignProduct, Long> {
    Optional<CampaignProduct> findByCampaign_NameAndProduct_ProductCode(String name, Long productCode);

    Optional<CampaignProduct> findByCampaign_Name(String name);

    Optional<CampaignProduct> findByProduct_ProductCode(Long productCode);

}
