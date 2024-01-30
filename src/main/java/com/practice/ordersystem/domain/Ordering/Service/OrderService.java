package com.practice.ordersystem.domain.Ordering.Service;

import com.practice.ordersystem.domain.Item.Item;
import com.practice.ordersystem.domain.Item.Repository.ItemRepository;
import com.practice.ordersystem.domain.Member.Member;
import com.practice.ordersystem.domain.Member.Repository.MemberRepository;
import com.practice.ordersystem.domain.OrderItem.OrderItem;
import com.practice.ordersystem.domain.OrderItem.Repository.OrderItemRepository;
import com.practice.ordersystem.domain.Ordering.DTO.OrderCreateReqDto;
import com.practice.ordersystem.domain.Ordering.DTO.OrderItemListReqDto;
import com.practice.ordersystem.domain.Ordering.DTO.OrderListResDto;
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

    public List<OrderListResDto> findAll() {
        List<Ordering> orderings = orderRepository.findAll();
        List<OrderListResDto> orderListResDtos = new ArrayList<>();
        for(Ordering ordering : orderings){
            String status = "";
            if(ordering.getStatus().equals(OrderStatus.CANCELED)) status = "취소";
            else status = "진행중";
            OrderListResDto orderListResDto = OrderListResDto.builder()
                    .memberId(ordering.getMember().getId())
                    .status(status)
                    .build();
            orderListResDtos.add(orderListResDto);
        }
        return orderListResDtos;
    }

    public void save(OrderCreateReqDto orderCreateReqDto) throws Exception {
        Member member = memberRepository.findById(orderCreateReqDto.getMemberId()).orElse(null);

        Ordering ordering = Ordering.builder()
                .status(OrderStatus.ORDERED)
                .member(member)
                .build();
        orderRepository.save(ordering);

        for(OrderItemListReqDto orderItemListReqDto : orderCreateReqDto.getItems()) {
            Item item = itemRepository.findById(orderItemListReqDto.getItemId()).orElseThrow(EntityNotFoundException::new);
            if(!item.updateItem(orderItemListReqDto.getCount())) {
                throw new Exception("수량이 부족합니다");
            }
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .quantity(orderItemListReqDto.getCount())
                    .ordering(ordering)
                    .build();
            orderItemRepository.save(orderItem);
        }
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
