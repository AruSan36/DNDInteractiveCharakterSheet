package model.Item;

import model.Dice;

import java.util.List;

public abstract class InventoryItem {

    private String name;
    private int weight;
    private int value;
    private String description;
    private int quantity;

    public InventoryItem(String name, int weight, int value, String description, int quantity){
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.description = description;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public int getWeight() { return weight; }
    public int getValue() { return value; }
    public String getDescritption() { return description; }
    public int getQuantity() { return quantity; }



}
