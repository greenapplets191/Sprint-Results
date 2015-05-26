package com.greenapplets.planimal;

import java.util.ArrayList;

/**
 * Created by ASUS on 4/26/2015.
 */
public class Pet {
    private int health;
    private int hunger;
    private String name;
    private Item[] inventory;
     private int score;
     private int money;

    public Pet() {
        health = 100;
        hunger = 100;
        name = new String();
        inventory = new Item[100]; // inventory limit = 100?
    }

     public Pet (String name, int score, int money, int hunger, int health) {

     }

    public void addToInventory(Item item){
        // go through array - loop: i = 0, i to inventory.length, i++
        // if item is already in the inventory : inventory[i].setAmount(getAmount() + 1), break;
        // else: if i + 1 <= 100 --> inventory[i + 1] = item, break
                // else: pop up that inventory is full and cannot buy anymore

        for(int i = 0; i < inventory.length; i++){
        }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     public void setMoney(int money) { this.money = money; }

     public int getMoney() {return this.money;}

     public void setScore(int score) {this.score = score;}

     public int getScore() {return this.score;}
}
