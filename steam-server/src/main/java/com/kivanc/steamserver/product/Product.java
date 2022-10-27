package com.kivanc.steamserver.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kivanc.steamserver.core.constants.LocalDateConstants;
import com.kivanc.steamserver.publisher.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private LocalDate releaseDate;
    private BigDecimal price;
    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "id"))
    private List<String> mediaUrls;
    @ManyToOne
    private Publisher publisher;
}
