package com.service;

import com.mapper.ItemMapper;
import com.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemMapper itemMapper;

    public List<Item> getAllItem(){
        return itemMapper.getAllItem();
    }
}
