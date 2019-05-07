package com.example.auto_kartprototype;

import java.io.Serializable;

public class Grocery implements Serializable {
    public String name;
    public int amount;

    public Grocery(String name, int amount)
    {
        this.name = name;
        this.amount = amount;
    }

}
