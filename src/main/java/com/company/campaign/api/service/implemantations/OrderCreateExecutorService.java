package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.builder.OrderBuilder;
import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.Order;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.domain.enums.StatusType;
import com.company.campaign.api.exception.CampaignApiDomainNotFoundException;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.repository.OrderRepository;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import com.company.campaign.api.util.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.company.campaign.api.constant.MessageKeyConstants.MESSAGE_KEY_CAMPAIGN_STOCK_EXCEED_EXCEPTION;
import static com.company.campaign.api.constant.MessageKeyConstants.MESSAGE_KEY_PRODUCT_NOT_FOUND_EXCEPTION;

@Service
public class OrderCreateExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CampaignProductRepository campaignProductRepository;

    @Override
    public Order executeCommand(String command)  {
        String[] commands = command.split(" ");

        Long productCode = Long.valueOf(commands[1]);
        Long quantity = Long.valueOf(commands[2]);

        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new CampaignApiDomainNotFoundException(MESSAGE_KEY_PRODUCT_NOT_FOUND_EXCEPTION));

        CampaignProduct campaignProduct = campaignProductRepository.findByProduct_ProductCode(productCode)
                .orElse(null);

        Order order = OrderBuilder
                .anOrder()
                .build();

        if (ObjectHelper.isPresent(campaignProduct) && campaignProduct.getStatus() == StatusType.ACTIVE) {
            product.setPrice(campaignProduct.getCampaignPrice());
            order.setCampaignOrder(Boolean.TRUE);
        }

        Long remainingStock = calculateRemainingStock(product.getStock(), quantity)
                .orElseThrow(() -> new CampaignApiDomainNotFoundException(MESSAGE_KEY_CAMPAIGN_STOCK_EXCEED_EXCEPTION));

        product.setStock(remainingStock);
        productRepository.save(product);

        order.setProduct(product);
        order.setQuantity(quantity);
        order = orderRepository.save(order);

        print("Order created " + order);
        return order;
    }

    private Optional<Long> calculateRemainingStock(Long currentStock, Long requestedCount) {
        if (requestedCount.compareTo(currentStock) > 0)
            return Optional.empty();
        return Optional.of(currentStock - requestedCount);
    }
}
