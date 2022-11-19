package com.kivanc.steamserver.ownedproduct;

import com.kivanc.steamserver.core.exceptions.RecordAlreadyExistException;
import com.kivanc.steamserver.customer.Customer;
import com.kivanc.steamserver.ownedproduct.dtos.OwnedProductDTO;
import com.kivanc.steamserver.ownedproduct.requests.ManyOwnedProductRequest;
import com.kivanc.steamserver.ownedproduct.requests.OwnedProductRequest;
import com.kivanc.steamserver.ownedproduct.requests.ProductWithPrice;
import com.kivanc.steamserver.product.Product;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OwnedProductServiceImpl implements OwnedProductService {

    private final OwnedProductDao ownedProductDao;
    private final ModelMapper modelMapper;


    @Override
    public OwnedProductDTO addOwnedProduct(OwnedProductRequest ownedProductRequest) {
        long customerId = ownedProductRequest.getCustomerId();
        long productId = ownedProductRequest.getProductId();
        if (ownedProductDao.existsOwnedProductByCustomerIdAndProductId(customerId, productId))
        {
            throw new RecordAlreadyExistException("Customer with id: " + customerId + " already own the product of id: " + productId);
        }

        OwnedProduct ownedProduct = OwnedProduct.builder()
                .product(Product.builder().id(ownedProductRequest.getProductId()).build())
                .customer(Customer.builder().id(ownedProductRequest.getCustomerId()).build())
                .paidAmount(ownedProductRequest.getPaidAmount())
                .build();
        ownedProduct = OwnedProduct.builder()
                .customer(ownedProduct.getCustomer())
                .product(ownedProduct.getProduct())
                .purchaseDate(LocalDateTime.now())
                .totalHours(BigDecimal.ZERO)
                .paidAmount(ownedProductRequest.getPaidAmount())
                .build();
        ownedProductDao.save(ownedProduct);
        OwnedProductDTO ownedProductDTO = modelMapper.map(ownedProduct, OwnedProductDTO.class);
        return ownedProductDTO;
    }

    @Override
    public void addManyOwnedProducts(ManyOwnedProductRequest manyOwnedProductRequest) {
        long customerId = manyOwnedProductRequest.getCustomerId();
        List<ProductWithPrice> productWithPricePair = manyOwnedProductRequest.getProductWithPrices();

        Customer customer = Customer.builder().id(customerId).build();

        List<OwnedProduct> toAddOwnedProducts = new ArrayList<>();
        productWithPricePair.forEach(pair -> {
            Product toAddProduct = Product.builder().id(pair.getProductId()).build();
            BigDecimal paidAmount = pair.getPaidAmount();
            OwnedProduct toAddOwnedProduct = OwnedProduct.builder()
                    .product(toAddProduct)
                    .paidAmount(paidAmount)
                    .purchaseDate(LocalDateTime.now())
                    .customer(customer)
                    .totalHours(BigDecimal.ZERO)
                    .build();
            toAddOwnedProducts.add(toAddOwnedProduct);
        });

        ownedProductDao.saveAll(toAddOwnedProducts);
    }

}
