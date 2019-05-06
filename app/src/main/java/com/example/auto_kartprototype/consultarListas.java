package com.example.auto_kartprototype;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class consultarListas extends AppCompatActivity {
    RecyclerView list;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager lMan;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_listas2);

        list = (RecyclerView) findViewById(R.id.grocery_list_list);

        lMan = new LinearLayoutManager(this);
        list.setLayoutManager(lMan);

        adapter = new GroceryListAdapter(getApplicationContext(),MainActivity.List_GroceryLists);
        list.setAdapter(adapter);

    }
}

class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.GroceryListViewHolder>
{
    public static class GroceryListViewHolder extends RecyclerView.ViewHolder
    {
        //TextView name, author;
        protected TextView NameTextView;
        protected TextView AuthorTextView;
        public GroceryListViewHolder(View itemView)
        {
            super(itemView);
            NameTextView = (TextView) itemView.findViewById(R.id.nTextView);
            AuthorTextView = (TextView) itemView.findViewById(R.id.aTextView);
        }

    }

    private ArrayList<GroceryList> list_of_shit;
    private Context context;

    public GroceryListAdapter(Context context, ArrayList<GroceryList> arrayList)
    {
        this.context = context;
        this.list_of_shit = arrayList;
    }

    @Override
    public int getItemCount()
    {
        return list_of_shit.size();
    }

    @Override
    public GroceryListAdapter.GroceryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_list_item, parent, false);
        GroceryListViewHolder v = new GroceryListViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(GroceryListAdapter.GroceryListViewHolder holder, int pos)
    {
        //GroceryList obj = list_of_shit.get(pos);
        holder.AuthorTextView.setText(list_of_shit.get(pos).authorName);
        holder.NameTextView.setText(list_of_shit.get(pos).listName);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rV)
    {
        super.onAttachedToRecyclerView(rV);
    }
}
