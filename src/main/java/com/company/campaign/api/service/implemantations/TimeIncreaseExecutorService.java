package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.domain.Order;
import com.company.campaign.api.repository.OrderRepository;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimeIncreaseExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order executeCommand(String command) throws Exception {
        return new Order();
    }

    private Optional<Long> calculateRemainingStock(Long currentStock, Long requestedCount) throws Exception {
        if (requestedCount.compareTo(currentStock) > 0)
            return Optional.empty();
        return Optional.of(currentStock - requestedCount);
    }
}
