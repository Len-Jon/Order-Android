package com.example.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Integer id;
    private String name;
    private String pic;
    private String description;
    private Integer price;
    private Integer itemType;
    private Integer itemSubType;
}
