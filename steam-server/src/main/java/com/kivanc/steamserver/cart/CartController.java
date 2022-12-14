package com.kivanc.steamserver.cart;

import com.kivanc.steamserver.cart.dtos.CartDTO;
import com.kivanc.steamserver.product.dtos.ProductDTO;
import com.kivanc.steamserver.productincart.dtos.ProductInCartDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("{id}")
    public ResponseEntity<CartDTO> getProductById(@PathVariable long id) {
        CartDTO cartDTO = cartService.getCardById(id);
        return ResponseEntity.ok(cartDTO);
    }

    @PutMapping("/addProductToCart")
    public ResponseEntity<HttpStatus> addProductToCart(@RequestParam long cartId, @RequestParam long productId) {
        cartService.addProductToCart(cartId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/emptyCart/{id}")
    public ResponseEntity<HttpStatus> emptyTheCart(@PathVariable("id")  long cartId) {
        cartService.emptyTheCart(cartId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/removeProductFromCart/{id}")
    public ResponseEntity<ProductInCartDTO> removeProductFromCart(@PathVariable("id") long productInCartId) {
        ProductInCartDTO productInCartDTO = cartService.removeProductFromCart(productInCartId);
        return ResponseEntity.ok(productInCartDTO);
    }

}
