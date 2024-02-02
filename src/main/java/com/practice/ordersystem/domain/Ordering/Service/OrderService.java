package com.practice.ordersystem.domain.Ordering.Service;

import com.practice.ordersystem.domain.Item.Item;
import com.practice.ordersystem.domain.Item.Repository.ItemRepository;
import com.practice.ordersystem.domain.Member.Member;
import com.practice.ordersystem.domain.Member.Repository.MemberRepository;
import com.practice.ordersystem.domain.OrderItem.OrderItem;
import com.practice.ordersystem.domain.OrderItem.Repository.OrderItemRepository;
import com.practice.ordersystem.domain.Ordering.DTO.OrderReqDto;
import com.practice.ordersystem.domain.Ordering.DTO.OrderResDto;
import com.practice.ordersystem.domain.Ordering.OrderStatus;
import com.practice.ordersystem.domain.Ordering.Ordering;
import com.practice.ordersystem.domain.Ordering.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(MemberRepository memberRepository, OrderRepository orderRepository, ItemRepository itemRepository, OrderItemRepository orderItemRepository) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderResDto> findAll() {
        List<Ordering> orderings = orderRepository.findAll();
        List<OrderResDto> orderResDtos = new ArrayList<>();
        for(Ordering ordering : orderings){
            String status = "";
            if(ordering.getStatus().equals(OrderStatus.CANCELED)) status = "취소";
            else status = "진행중";
            OrderResDto orderResDto = OrderResDto.builder()
                    .memberId(ordering.getMember().getId())
                    .status(status)
                    .build();
            orderResDtos.add(orderResDto);
        }
        return orderResDtos;
    }

    public Ordering save(OrderReqDto orderReqDto, String email) throws EntityNotFoundException, IllegalArgumentException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("등록되지 않은 유저입니다."));
        Ordering ordering = Ordering.builder()
                .status(OrderStatus.ORDERED)
                .member(member)
                .build();
        orderRepository.save(ordering);

        for(OrderReqDto.OrderReqItemDto orderReqItemDto : orderReqDto.getOrderReqItemDtos()) {
            Item item = itemRepository.findById(orderReqItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
            if(!item.updateItem(orderReqItemDto.getCount())) {
                throw new IllegalArgumentException("수량이 부족합니다");
            }
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .quantity(orderReqItemDto.getCount())
                    .ordering(ordering)
                    .build();
            orderItemRepository.save(orderItem);
        }
        return ordering;
    }

    public void cancelOrder(Long id) throws EntityNotFoundException{
        Ordering ordering = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        ordering.changeStatus();
        List<OrderItem> orderItemList = ordering.getOrderItemList();
        for(OrderItem orderItem : orderItemList){
            orderItem.getItem().rollbackItem(orderItem.getQuantity());
        }
    }
}
