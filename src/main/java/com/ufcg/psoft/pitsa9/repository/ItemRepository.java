package com.ufcg.psoft.pitsa9.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.pitsa9.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
