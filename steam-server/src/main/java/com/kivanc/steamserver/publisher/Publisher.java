package com.kivanc.steamserver.publisher;

import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "publishers")
@PrimaryKeyJoinColumn(name = "id")
public class Publisher extends User {
    @OneToMany
    private List<Product> products;
}
