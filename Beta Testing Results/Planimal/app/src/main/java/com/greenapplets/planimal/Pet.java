package com.greenapplets.planimal;

import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ASUS on 4/26/2015.
 */
public class Pet {
    private int health;
    private int hunger;
    private String name;
    ArrayList<Item> inventory;
    private int money;

    public Pet() {
        health = 100;
        hunger = 100;
        money=0;
        name = "<PetName>";
    }

    public void addToInventory(ArrayList<Item> add){
        this.inventory = new ArrayList<Item>();
        this.inventory.addAll(add);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void setMoney(int money) { this.money = money; }

    public int getMoney() {return this.money;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
