package com.kivanc.steamserver.ownedproduct;

import com.kivanc.steamserver.ownedproduct.OwnedProduct;
import com.kivanc.steamserver.ownedproduct.OwnedProductService;
import com.kivanc.steamserver.ownedproduct.dtos.OwnedProductDTO;
import com.kivanc.steamserver.ownedproduct.requests.ManyOwnedProductRequest;
import com.kivanc.steamserver.ownedproduct.requests.OwnedProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ownedProducts")
@RequiredArgsConstructor
public class OwnedProductController {
    private final OwnedProductService ownedProductService;

    @PostMapping
    public ResponseEntity<OwnedProductDTO> addOwnedProduct(@RequestBody OwnedProductRequest ownedProductRequest) {
        OwnedProductDTO dto = ownedProductService.addOwnedProduct(ownedProductRequest);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/addManyOwnedProducts")
    public ResponseEntity<HttpStatus> addManyOwnedProducts(@RequestBody ManyOwnedProductRequest request) {
        ownedProductService.addManyOwnedProducts(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
