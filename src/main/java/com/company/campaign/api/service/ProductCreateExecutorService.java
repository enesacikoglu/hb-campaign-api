package com.company.campaign.api.service;


import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

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
                .price(BigInteger.valueOf(Long.valueOf(commands[2])))
                .stock(Long.valueOf(commands[3]))
                .build();
        print("Product created ;" + product.toString());
        productRepository.save(product);
        return product;
    }

}
