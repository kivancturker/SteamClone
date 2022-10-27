package com.kivanc.steamserver.product;

import com.kivanc.steamserver.core.exceptions.RecordNotFoundException;
import com.kivanc.steamserver.product.requests.AddProductRequest;
import com.kivanc.steamserver.publisher.PublisherService;
import com.kivanc.steamserver.publisher.dtos.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ModelMapper mapper;
    private final ProductDao productDao;
    private final PublisherService publisherService;



    @Override
    public ProductDTO getProduct(long id) {
        Optional<Product> maybeProduct = productDao.findById(id);
        if (maybeProduct.isEmpty())
        {
            throw new RecordNotFoundException("Product Not Found");
        }
        ProductDTO productDTO = mapper.map(maybeProduct.get(), ProductDTO.class);
        return productDTO;
    }

    @Override
    public List<ProductDTO> getProductsByUsername(String username) {
        Optional<List<Product>> maybeProducts = productDao.getProductsByPublisherUsername(username);
        if (maybeProducts.isEmpty())
        {
            throw new RecordNotFoundException("Products Not Found With the username: " + username);
        }
        List<ProductDTO> productDTOS = maybeProducts.get().stream()
                .map(product -> mapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        return productDTOS;
    }

    @Override
    public void addProduct(AddProductRequest productRequest) {
        Product product = mapper.map(productRequest, Product.class);
        log.info("Product: " + product.getName() + " added");
        productDao.save(product);
    }
}
