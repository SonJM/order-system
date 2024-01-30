package com.practice.ordersystem.domain.Ordering.Controller;

import com.practice.ordersystem.domain.OrderItem.Service.OrderItemService;
import com.practice.ordersystem.domain.Ordering.DTO.OrderCreateReqDto;
import com.practice.ordersystem.domain.Ordering.DTO.OrderListResDto;
import com.practice.ordersystem.domain.Ordering.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<Object> orderList(){
        try{
            List<OrderListResDto> listResDtoList = orderService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(listResDtoList);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @PostMapping("/order/new")
    public HttpStatus newOrder(@RequestBody OrderCreateReqDto orderCreateReqDto){
        try{
            log.info(orderCreateReqDto.toString());
            orderService.save(orderCreateReqDto);
            return HttpStatus.OK;
        } catch(Exception e){
            return HttpStatus.BAD_REQUEST;
        }
    }

    @GetMapping("/order/{id}/cancel")
    public HttpStatus cancleOrder(@PathVariable Long id){
        try{
            orderService.cancelOrder(id);
            return HttpStatus.OK;
        } catch(EntityNotFoundException e){
            return HttpStatus.NO_CONTENT;
        }
    }
}
