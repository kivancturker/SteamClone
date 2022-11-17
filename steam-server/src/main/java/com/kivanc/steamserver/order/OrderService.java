package com.kivanc.steamserver.order;

import com.kivanc.steamserver.order.dtos.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(long customerId);
    List<Order> getOrdersByCustomerId(long customerId);
}
