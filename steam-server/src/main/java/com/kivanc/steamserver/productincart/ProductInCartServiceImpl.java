package com.kivanc.steamserver.productincart;

import com.kivanc.steamserver.cart.Cart;
import com.kivanc.steamserver.core.exceptions.RecordAlreadyExistException;
import com.kivanc.steamserver.core.exceptions.RecordNotFoundException;
import com.kivanc.steamserver.product.Product;
import com.kivanc.steamserver.productincart.dtos.ProductInCartDTO;
import com.kivanc.steamserver.productincart.requests.AddProductInCartRequest;
import com.kivanc.steamserver.productincart.requests.DeleteProductInCartRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductInCartServiceImpl implements ProductInCartService {
    private final ProductInCartDao dao;
    private final ModelMapper modelMapper;


    @Override
    public ProductInCartDTO getProductInCartById(long productInCartId) {
        Optional<ProductInCart> productInCart = dao.findById(productInCartId);
        if (productInCart.isEmpty())
        {
            throw new RecordNotFoundException("No such product in this cart");
        }
        ProductInCartDTO productInCartDTO = modelMapper.map(productInCart, ProductInCartDTO.class);
        return productInCartDTO;
    }

    @Override
    public ProductInCartDTO addProductInCart(AddProductInCartRequest request) {
        long productId = request.getProductId();
        long cartId = request.getCartId();
        if (dao.existsProductInCartByProductIdAndCartId(productId, cartId))
        {
            throw new RecordAlreadyExistException("Product with id: " + productId + " is already exist");
        }
        ProductInCart productInCart = ProductInCart.builder()
                .product(Product.builder().id(productId).build())
                .cart(Cart.builder().id(cartId).build())
                .build();
        ProductInCart addedProductInCart = dao.saveAndFlush(productInCart);
        ProductInCartDTO response = modelMapper.map(addedProductInCart, ProductInCartDTO.class);
        return response;
    }

    @Override
    public ProductInCartDTO deleteProductInCart(long productInCartId) {
        Optional<ProductInCart> maybeProductInCart = dao.findById(productInCartId);
        if (maybeProductInCart.isEmpty())
        {
            throw new RecordNotFoundException("Product not in the Cart");
        }
        dao.delete(maybeProductInCart.get());
        ProductInCartDTO deletedDTO = modelMapper.map(maybeProductInCart.get(), ProductInCartDTO.class);
        return deletedDTO;
    }
}
