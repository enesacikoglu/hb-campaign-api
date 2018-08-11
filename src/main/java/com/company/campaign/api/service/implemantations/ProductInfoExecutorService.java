package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.domain.Product;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    private final ProductRepository productRepository;

    public ProductInfoExecutorService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product executeCommand(String command) {
        String[] commands = command.split(" ");
        Product product = productRepository.findById(Long.valueOf(commands[1]))
                .orElse(null);//TODO exception fırlat....
        print("Product info; " + product.toString());
        return product;
    }
}
