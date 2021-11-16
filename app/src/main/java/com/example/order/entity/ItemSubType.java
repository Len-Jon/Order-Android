package com.example.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSubType {
    private Integer id;
    private String name;
    private String pic;
    private Integer itemType;

}
