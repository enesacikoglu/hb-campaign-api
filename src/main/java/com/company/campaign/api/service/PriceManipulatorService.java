package com.company.campaign.api.service;

import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.Order;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.repository.OrderRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

@Service
public class PriceManipulatorService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CampaignProductRepository campaignProductRepository;

    public CampaignProduct calculate(CampaignProduct campaignProduct) throws Exception {
        List<Order> orders = orderRepository.findByProduct_ProductCodeAndIsCampaignOrderTrue(campaignProduct.getProduct().getProductCode())
                .orElse(Collections.emptyList());

        if (CollectionUtils.isEmpty(orders)) {
            return campaignProduct;
        }

        LongAdder adder = new LongAdder();
        orders.stream().forEach(order -> adder.add(order.getQuantity()));
        Long totalSales = adder.sumThenReset();
        campaignProduct.setTotalSalesCount(totalSales);

        int saleCount = orders.size();

        orders.stream().forEach(order -> adder.add(order.getProduct().getPrice().longValue()));
        Long totalPrice = adder.sumThenReset();
        campaignProduct.setAverageItemPrice(totalPrice / saleCount);
        campaignProduct.setTurnover(campaignProduct.getAverageItemPrice() * saleCount);
        return campaignProductRepository.save(campaignProduct);
    }
}
