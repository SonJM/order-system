package com.practice.ordersystem.domain.Item.Controller;

import com.practice.ordersystem.domain.Item.DTO.ItemListResDto;
import com.practice.ordersystem.domain.Item.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public ResponseEntity<Object> getItems(){
        try{
            List<ItemListResDto> listResDtoList = itemService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(listResDtoList);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }
}
