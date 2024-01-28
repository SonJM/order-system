package com.practice.ordersystem.domain.Item.Repository;

import com.practice.ordersystem.domain.Item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    public Optional<Item> findById(Long id);
}
