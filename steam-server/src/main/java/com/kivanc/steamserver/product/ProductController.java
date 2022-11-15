package com.kivanc.steamserver.product;

import com.kivanc.steamserver.product.requests.AddProductRequest;
import com.kivanc.steamserver.publisher.dtos.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable(name = "id") long id) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(productService.getProductById(id));
    }

    @GetMapping("/publisherId/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByPublisherId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(productService.getProductsByPublisherId(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addProduct(@RequestBody AddProductRequest productRequest) {
        productService.addProduct(productRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
