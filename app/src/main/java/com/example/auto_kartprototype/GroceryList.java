package com.example.auto_kartprototype;
import java.util.ArrayList;

public class GroceryList {

    public String listName;
    public String authorName = "yo";
    public ArrayList<Grocery> items = new ArrayList<Grocery>();


    public GroceryList(String listName)
    {
        this.listName = listName;
    }

    public GroceryList(String listName, String authorName)
    {
        this.listName = listName;
        this.authorName = authorName;
    }

    public void addItems (Grocery g)
    {
        this.items.add(g);
    }

}
