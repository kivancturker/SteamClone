package com.kivanc.steamserver.order;

import com.kivanc.steamserver.cart.Cart;
import com.kivanc.steamserver.cart.CartService;
import com.kivanc.steamserver.cart.dtos.CartDTO;
import com.kivanc.steamserver.customer.Customer;
import com.kivanc.steamserver.customer.CustomerService;
import com.kivanc.steamserver.customer.dtos.CustomerDTO;
import com.kivanc.steamserver.order.dtos.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final OrderDao orderDao;
    private final CustomerService customerService;
    private final CartService cartService;



    @Override
    public OrderDTO placeOrder(long customerId) {
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        long cartId = customerDTO.getCartId();
        CartDTO cartDTO = cartService.getCardById(cartId);
        Cart cart = modelMapper.map(cartDTO, Cart.class);

        Order order = Order.builder()
                .customer(customer)
                .products(cart.getProducts())
                .price(cart.getPrice())
                .orderDate(LocalDateTime.now())
                .build();

        cartService.emptyTheCart(cartId);


        return null;
    }

    @Override
    public List<Order> getOrdersByCustomerId(long customerId) {
        return null;
    }
}
