package com.kivanc.steamserver.core.configurations;

import com.kivanc.steamserver.customer.Customer;
import com.kivanc.steamserver.customer.CustomerDao;
import com.kivanc.steamserver.customer.CustomerService;
import com.kivanc.steamserver.customer.requests.CustomerRequest;
import com.kivanc.steamserver.customer.requests.ProductAdditionRequest;
import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.product.ProductDao;
import com.kivanc.steamserver.product.ProductService;
import com.kivanc.steamserver.product.requests.AddProductRequest;
import com.kivanc.steamserver.publisher.Publisher;
import com.kivanc.steamserver.publisher.PublisherDao;
import com.kivanc.steamserver.publisher.PublisherService;
import com.kivanc.steamserver.publisher.requests.PublisherRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final CustomerService customerService;
    private final ProductService productService;
    private final PublisherService publisherService;

    public void run(ApplicationArguments args) {
        publisherService.addPublisher(PublisherRequest.builder().username("Ubisoft").email("ubisoft@gmail.com").password("123456789").build());
        publisherService.addPublisher(PublisherRequest.builder().username("Activision").email("activision@gmail.com").password("987654321").build());
        publisherService.addPublisher(PublisherRequest.builder().username("Electronic Arts").email("info@ea.com").password("verySecretPassword123").build());
        publisherService.addPublisher(PublisherRequest.builder().username("Nintendo").email("nintendo@wow.com").password("justWow").build());

        productService.addProduct(AddProductRequest.builder().name("Rainbow Six Siege").publisherId(1).releaseDate(LocalDate.of(2012, 3, 27)).price(BigDecimal.valueOf(30.5)).build());
        productService.addProduct(AddProductRequest.builder().name("Assassins Creed").publisherId(1).releaseDate(LocalDate.of(2004, 7, 14)).price(BigDecimal.valueOf(70.70)).build());
        productService.addProduct(AddProductRequest.builder().name("Ghost Recon").publisherId(1).releaseDate(LocalDate.of(2013, 4, 22)).price(BigDecimal.valueOf(51)).build());
        productService.addProduct(AddProductRequest.builder().name("Call Of Duty 2").publisherId(2).releaseDate(LocalDate.of(2001, 1, 7)).price(BigDecimal.valueOf(12)).build());
        productService.addProduct(AddProductRequest.builder().name("Fifa 2022").publisherId(3).releaseDate(LocalDate.of(2022, 1, 1)).price(BigDecimal.valueOf(99.99)).build());
        productService.addProduct(AddProductRequest.builder().name("Plant vs Zombies").publisherId(3).releaseDate(LocalDate.of(2019, 3, 13)).price(BigDecimal.valueOf(29.9)).build());

        customerService.addCustomer(CustomerRequest.builder().username("kivanc17").email("kivanc@gmail.com").password("wowNice123456").build());
        customerService.addCustomer(CustomerRequest.builder().username("kivancsBestie").email("adafgac@adadfas.com").password("galatasary2000").build());
        customerService.addCustomer(CustomerRequest.builder().username("MorbidAngel").email("morbid@angle.com").password("g51afsaf8872aa").build());
        customerService.addCustomer(CustomerRequest.builder().username("average lincoln park fan").email("yasin444@gmail.com").password("4af4g1asd6af1").build());

    }
}