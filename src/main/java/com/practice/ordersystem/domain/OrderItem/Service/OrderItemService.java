package com.practice.ordersystem.domain.OrderItem.Service;

import com.practice.ordersystem.domain.Item.Item;
import com.practice.ordersystem.domain.OrderItem.DTO.OrderItemListResDto;
import com.practice.ordersystem.domain.OrderItem.OrderItem;
import com.practice.ordersystem.domain.OrderItem.Repository.OrderItemRepository;
import com.practice.ordersystem.domain.Ordering.Ordering;
import com.practice.ordersystem.domain.Ordering.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderItemListResDto> findAllById(Long id) throws EntityNotFoundException{
        List<OrderItem> orderItemList = orderItemRepository.findAllById(id);
        List<OrderItemListResDto> orderItemListResDtos = new ArrayList<>();
        for(OrderItem orderItem : orderItemList){
            OrderItemListResDto orderItemListResDto = OrderItemListResDto.builder()
                    .itemName(orderItem.getItem().getName())
                    .orderId(orderItem.getOrdering().getId())
                    .quantity(orderItem.getQuantity())
                    .build();
            orderItemListResDtos.add(orderItemListResDto);
        }
        return orderItemListResDtos;
    }
}
