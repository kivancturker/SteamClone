package com.kivanc.steamserver.order;

import com.kivanc.steamserver.customer.CustomerService;
import com.kivanc.steamserver.order.requests.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

// TODO: Make addOrder transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final OrderDao orderDao;
    private final CustomerService customerService;



    @Override
    public void addOrder(OrderRequest orderRequest) {
        Order order = modelMapper.map(orderRequest, Order.class);

        orderDao.save(order);
    }
}
