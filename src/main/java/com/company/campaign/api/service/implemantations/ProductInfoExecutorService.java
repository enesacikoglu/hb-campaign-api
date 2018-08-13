package com.company.campaign.api.service.implemantations;


import com.company.campaign.api.domain.CampaignProduct;
import com.company.campaign.api.domain.Product;
import com.company.campaign.api.domain.enums.StatusType;
import com.company.campaign.api.repository.CampaignProductRepository;
import com.company.campaign.api.repository.ProductRepository;
import com.company.campaign.api.service.interfaces.ICommandExecutor;
import com.company.campaign.api.service.interfaces.ICommandOutPutPrinter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductInfoExecutorService implements ICommandExecutor, ICommandOutPutPrinter {

    private final ProductRepository productRepository;
    private final CampaignProductRepository campaignProductRepository;

    public ProductInfoExecutorService(ProductRepository productRepository,
                                      CampaignProductRepository campaignProductRepository) {
        this.productRepository = productRepository;
        this.campaignProductRepository = campaignProductRepository;
    }

    @Override
    public Product executeCommand(String command) throws Exception {
        String[] commands = command.split(" ");
        Product product = productRepository.findById(Long.valueOf(commands[1]))
                .orElseThrow(() -> new Exception("Product Not Found!"));//TODO exception fırlat....

        Optional<CampaignProduct> campaignProduct = campaignProductRepository.findByProduct_ProductCode(product.getProductCode());

        if (campaignProduct.isPresent()) {
            CampaignProduct campaign = campaignProduct.get();
            if (campaign.getStatus() != StatusType.ENDED) {
                product.setPrice(campaignProduct.get().getCampaignPrice());
            }else {
                product.setPrice(campaignProduct.get().getRealPrice());
            }
        }
        print("Product info; " + product.toString());
        return product;
    }
}
