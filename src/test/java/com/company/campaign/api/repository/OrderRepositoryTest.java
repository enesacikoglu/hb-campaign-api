package com.company.campaign.api.repository;

import com.company.campaign.api.builder.OrderBuilder;
import com.company.campaign.api.builder.ProductBuilder;
import com.company.campaign.api.domain.Order;
import com.company.campaign.api.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OrderRepository orderRepository;


    @Test
    public void it_should_get_order_by_product_code() {
        //given
        Product product = ProductBuilder
                .aProduct()
                .productCode(61L)
                .price(BigInteger.ONE)
                .stock(3L)
                .build();

        Order order = OrderBuilder
                .anOrder()
                .product(product)
                .quantity(3L)
                .build();

        testEntityManager.persistAndFlush(product);
        testEntityManager.persist(order);

        //when
        Optional<Order> optionalExpectedOrder = orderRepository.findByProduct_ProductCode(61L);

        //then
        assertThat(optionalExpectedOrder).isPresent();
        Order expected = optionalExpectedOrder.get();
        assertThat(expected.getOrderId()).isNotNull();
        assertThat(expected.getQuantity()).isEqualTo(3L);
        assertThat(expected.getProduct().getProductCode()).isEqualTo(61L);
        assertThat(expected.getProduct().getPrice()).isEqualTo(1);
        assertThat(expected.getProduct().getStock()).isEqualTo(3L);
    }
}