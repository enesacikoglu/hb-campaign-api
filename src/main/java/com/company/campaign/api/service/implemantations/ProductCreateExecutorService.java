package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductCreateExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product executeCommand(String command) {
        String[] commands = command.split(" ");
        Product product = ProductBuilder
                .aProduct()
                .productCode(Long.valueOf(commands[1]))
                .price(BigDecimal.valueOf(Long.valueOf(commands[2])))
                .stock(Long.valueOf(commands[3]))
                .build();
        product = productRepository.save(product);
        print("Product created " + product);
        return product;
    }

}
