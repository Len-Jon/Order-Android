package com.example.order.constant;

import com.example.order.entity.Item;
import com.example.order.entity.ItemSubType;
import com.example.order.entity.ItemType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Constant {
    public static final String ITEM_TYPE_ID_KEY = "ITEM_TYPE_ID_KEY";
    public static final String ITEM_SUB_TYPE_ID_KEY = "ITEM_SUB_TYPE_ID_KEY";
    public static final String ITEM_ID_KEY = "ITEM_ID_KEY";
    public static String ADDRESS = null;
    public static Integer PAY_METHOD_INDEX = null;

    public static List<ItemType> itemTypeList = null;
    public static List<ItemSubType> itemSubTypeList = null;
    public static List<Item> itemList = null;

    public static Map<Integer, Integer> itemCntMap = new HashMap<>();

    public static void initMap() {
        itemCntMap = itemList.parallelStream().collect(Collectors.toMap(Item::getId, x -> 0));
    }

    public static List<Item> getChosenList() {
        return Constant.itemList.parallelStream().filter(x -> Constant.itemCntMap.getOrDefault(x.getId(), 0) > 0).collect(Collectors.toList());
    }

    public static int getItemCnt(int id) {
        return Constant.itemCntMap.getOrDefault(id, 0);
    }
}
