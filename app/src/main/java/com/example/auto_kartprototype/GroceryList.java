package com.example.auto_kartprototype;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class GroceryList implements Serializable {

    public String listName;
    public String authorName;
    public ArrayList<Grocery> items = new ArrayList<Grocery>();


    public GroceryList(String listName)
    {
        this.listName = listName;
        this.authorName = MainActivity.User;
    }

    public GroceryList(String listName, String authorName)
    {
        this.listName = listName;
        this.authorName = authorName;
    }

    public void addItem (Grocery g)
    {
        this.items.add(g);
    }

    public void replaceList (ArrayList<Grocery> shit)
    {
        this.items = shit;
    }

}
