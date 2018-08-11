package com.company.campaign.api.repository;

import com.company.campaign.api.domain.Campaign;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CampaignRepository extends CrudRepository<Campaign, Long> {
    Optional<Campaign> findByName(String name);
}
