package com.kivanc.steamserver.order;

import com.kivanc.steamserver.order.requests.OrderRequest;

public interface OrderService {
    void addOrder(OrderRequest orderRequest);
}
