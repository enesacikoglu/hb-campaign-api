# hb-campaign-api
Spring boot Campaign Api

![swagger](https://user-images.githubusercontent.com/21153996/44146058-9d68124e-a096-11e8-9236-53b4da8368d0.png)

RestFul Service uses following Technologies:

* Spring-boot
* Hibernate,JPA
* JaCoCo
* H2 DB
* Swagger2

## Run Commands Samples:

* create_product 1 100 1000
* get_product_info 1
* create_order 1 100
* create_campaign NS 1 10 20 100
* increase_time 2
* get_campaign_info NS

Use swagger to run commands Whether file or direct commands as above

## Build

Run mvn clean install

## SpringBootService

Run spring-boot-run.

## Running Tests

Run mvn clean verify

Coverage 

![coverage](https://user-images.githubusercontent.com/21153996/44146191-3a593722-a097-11e8-8c90-be02f699dace.png)

## Documentation

To get more look at http://localhost:8080/swagger-ui.html#/
