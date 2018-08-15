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

import java.math.BigDecimal;
import java.util.List;
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
                .price(BigDecimal.ONE)
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
        Optional<List<Order>> optionalExpectedOrder = orderRepository.findByProduct_ProductCode(61L);

        //then
        assertThat(optionalExpectedOrder).isPresent();
        List<Order> expected = optionalExpectedOrder.get();
        assertThat(expected.get(0).getOrderId()).isNotNull();
        assertThat(expected.get(0).getQuantity()).isEqualTo(3L);
        assertThat(expected.get(0).getProduct().getProductCode()).isEqualTo(61L);
        assertThat(expected.get(0).getProduct().getPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(expected.get(0).getProduct().getStock()).isEqualTo(3L);
    }
}