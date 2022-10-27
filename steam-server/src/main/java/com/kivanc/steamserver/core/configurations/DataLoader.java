package com.kivanc.steamserver.core.configurations;

import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.product.ProductDao;
import com.kivanc.steamserver.publisher.Publisher;
import com.kivanc.steamserver.publisher.PublisherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private PublisherDao publisherDao;
    private ProductDao productDao;

    @Autowired
    public DataLoader(PublisherDao publisherDao, ProductDao productDao) {
        this.publisherDao = publisherDao;
        this.productDao = productDao;
    }

    public void run(ApplicationArguments args) {
        // userRepository.save(new User("lala", "lala", "lala"));
        Publisher publisher1 = Publisher.builder().username("ubisoft").email("ubisoft@gmail.com").password("123456789").build();
        Publisher publisher2 = Publisher.builder().username("activision").email("activision@gmail.com").password("987654321").build();

        publisherDao.saveAndFlush(publisher1);
        publisherDao.saveAndFlush(publisher2);

        Product productOfUbisoft1 = Product.builder()
                .name("Assassins Creed")
                .releaseDate(LocalDate.of(2004, 7, 14))
                .price(BigDecimal.valueOf(69.69))
                .publisher(publisher1)
                .build();

        List<String> mediaUrlsForSixSiege = Arrays.asList("https://cdn.akamai.steamstatic.com/steam/apps/359550/capsule_616x353.jpg?t=1655223333",
                "www.google.com");
        Product productOfUbisoft2 = Product.builder()
                .name("Rainbow Six Siege")
                .releaseDate(LocalDate.of(2012, 3, 27))
                .price(BigDecimal.valueOf(30.5))
                .publisher(publisher1)
                .mediaUrls(mediaUrlsForSixSiege)
                .build();

        Product productOfActivision1 = Product.builder()
                .name("Call Of Duty 2")
                .releaseDate(LocalDate.of(2001, 1, 7))
                .price(BigDecimal.valueOf(12))
                .publisher(publisher2)
                .build();

        productDao.saveAndFlush(productOfUbisoft1);
        productDao.saveAndFlush(productOfUbisoft2);
        productDao.saveAndFlush(productOfActivision1);
    }
}