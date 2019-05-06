package com.example.auto_kartprototype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // our list of grocery list
    // list-ception
    // that was the most reddit thing i've said in a while please sign me up for a gas chamber
    public static ArrayList<GroceryList> List_GroceryLists = new ArrayList<GroceryList>();
    private String User = "El Bananero";

    static final int UPDATE_LIST_REQUEST = 1;

    RecyclerView list;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager lMan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile: "+User);
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayShowTitleEnabled(false);



        //TextView curUser = findViewById(R.id.UserName);
        //curUser.append(User);

        updateList();


        FloatingActionButton btnNuevaLista = (FloatingActionButton) findViewById(R.id.fab);
        btnNuevaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), crearLista.class);
                startActivityForResult(startIntent,UPDATE_LIST_REQUEST);

            }
        });
        /*
        Button btnInvitaciones = (Button) findViewById(R.id.btnInvitaciones);
        btnInvitaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Invitaciones.class);
                startActivity(startIntent);
            }
        });

        Button btnConsultarListas = (Button) findViewById(R.id.btnConsultarListas);
        btnConsultarListas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), consultarListas.class);
                startActivity(startIntent);
            }
        });*/


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == UPDATE_LIST_REQUEST)
        {
            if(resultCode==RESULT_OK)
            {
                adapter.notifyDataSetChanged();
                //updateList();
                Snackbar.make(findViewById(android.R.id.content),"now u gots: " + adapter.getItemCount(),Snackbar.LENGTH_LONG).show();
            }
        }
    }

    public void updateList()
    {
        list = (RecyclerView) findViewById(R.id.grocery_list_list);

        lMan = new LinearLayoutManager(this);
        list.setLayoutManager(lMan);

        adapter = new GroceryListAdapter(getApplicationContext(),List_GroceryLists);
        list.setAdapter(adapter);
    }

}

class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.GroceryListViewHolder>
{
    public static class GroceryListViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        //TextView name, author;
        protected TextView NameTextView;
        protected TextView AuthorTextView;
        public GroceryListViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            NameTextView = (TextView) itemView.findViewById(R.id.nTextView);
            AuthorTextView = (TextView) itemView.findViewById(R.id.aTextView);
        }

        @Override
        public void onClick(View v)
        {
            Toast.makeText(v.getContext(),"you pressed a thing wow",Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<GroceryList> list_of_shit;
    private Context context;

    public GroceryListAdapter(Context context, ArrayList<GroceryList> arrayList)
    {
        this.context = context;
        this.list_of_shit = arrayList;
    }

    public void clearList()
    {
        this.list_of_shit.clear();
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

