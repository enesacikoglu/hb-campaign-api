package com.company.campaign.api.service;


import com.company.campaign.api.builder.OrderBuilder;
import com.company.campaign.api.domain.Order;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.ProductRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCreateExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order executeCommand(String command) {
        String[] commands = command.split(" ");
        Product product = productRepository.findById(Long.valueOf(commands[1]))
                .orElse(null);//TODO exception fırlat...
        Order order = OrderBuilder
                .anOrder()
                .orderId(RandomUtils.nextLong())
                .product(product)
                .quantity(Long.valueOf(commands[2]))
                .build();
        print("Order created ;" + order.toString());
        return order;
    }
}
