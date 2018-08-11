package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.builder.OrderBuilder;
import com.company.campaign.api.domain.Order;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.OrderRepository;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderCreateExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order executeCommand(String command) throws Exception {
        String[] commands = command.split(" ");

        Product product = productRepository.findById(Long.valueOf(commands[1]))
                .orElseThrow(() -> new Exception("Product Not Found"));//TODO exception fırlat...

        Long remainingStock = calculateRemainingStock(product.getStock(), Long.valueOf(commands[2]))
                .orElseThrow(() -> new Exception("Stock Exceed!"));
        product.setStock(remainingStock);
        productRepository.save(product);

        Order order = OrderBuilder
                .anOrder()
                .product(product)
                .quantity(Long.valueOf(commands[2]))
                .build();

        order = orderRepository.save(order);

        print("Order created ;" + order.toString());

        return order;
    }

    private Optional<Long> calculateRemainingStock(Long currentStock, Long requestedCount) throws Exception {
        if (requestedCount.compareTo(currentStock) > 0)
            return Optional.empty();
        return Optional.of(currentStock - requestedCount);
    }
}
