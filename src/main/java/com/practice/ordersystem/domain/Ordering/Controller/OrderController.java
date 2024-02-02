package com.practice.ordersystem.domain.Ordering.Controller;

import com.practice.ordersystem.domain.Ordering.DTO.OrderReqDto;
import com.practice.ordersystem.domain.Ordering.DTO.OrderResDto;
import com.practice.ordersystem.domain.Ordering.Ordering;
import com.practice.ordersystem.domain.Ordering.Service.OrderService;
import com.practice.ordersystem.domain.common.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
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
            List<OrderResDto> listResDtoList = orderService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(listResDtoList);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @PostMapping("/order/new")
    public ResponseEntity<ResponseDto> newOrder(@RequestBody OrderReqDto orderReqDto, Principal principal){
        Ordering ordering = orderService.save(orderReqDto, principal.getName());
        return new ResponseEntity<>(
                new ResponseDto(HttpStatus.CREATED, "order succesfully created", ordering.getId())
                , HttpStatus.CREATED);
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
