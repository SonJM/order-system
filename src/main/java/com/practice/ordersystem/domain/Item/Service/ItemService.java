package com.practice.ordersystem.domain.Item.Service;

import com.practice.ordersystem.domain.Item.DTO.ItemListResDto;
import com.practice.ordersystem.domain.Item.Item;
import com.practice.ordersystem.domain.Item.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemListResDto> findAll(){
        List<Item> itemList = itemRepository.findAll();
        List<ItemListResDto> itemListResDtos = new ArrayList<>();
        for(Item item : itemList){
            ItemListResDto itemListResDto = ItemListResDto.builder()
                    .name(item.getName())
                    .price(item.getPrice())
                    .stockQuantity(item.getStockQuantity())
                    .imagePath(item.getImagePath())
                    .build();
            itemListResDtos.add(itemListResDto);
        }
        return itemListResDtos;
    }
}
