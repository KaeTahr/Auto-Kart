package com.example.auto_kartprototype;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class consultarListas extends AppCompatActivity {

    RecyclerView list;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager lMan;
    public static TextView emptyText;
    public static ArrayList<Grocery> List_Groceries;// = new ArrayList<Grocery>();

    static final int UPDATE_LIST_REQUEST = 1;
    static final int UPDATE_GROCERIES_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_listas2);
        //setContentView(R.layout.activity_main);

        ActionBar aBar = getSupportActionBar();
        aBar.setTitle("Lista");

        GroceryList tmp = (GroceryList) this.getIntent().getExtras().getSerializable("selectedItem");
        List_Groceries = tmp.items;

        updateList();
       // ItemTouchHelper iTH = new ItemTouchHelper(new SwipeToDeleteGrocery((GroceryAdapter) adapter));
       // iTH.attachToRecyclerView(list);
        aBar.setTitle("Lista: " + tmp.listName);

        FloatingActionButton btnNuevaLista = (FloatingActionButton) findViewById(R.id.fab2);
        btnNuevaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();

            }
        });

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        MainActivity.List_GroceryLists.get(this.getIntent().getExtras()
                .getInt("selIndex")).replaceList(List_Groceries);
    }
    public void updateList()
    {
        list = (RecyclerView) findViewById(R.id.List_Groceries);
        emptyText = (TextView) findViewById(R.id.empty_grocery);

        if(List_Groceries.isEmpty())
        {
            list.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        }
        else
        {
            list.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
        }

        lMan = new LinearLayoutManager(this);
        list.setLayoutManager(lMan);

        adapter = new GroceryAdapter(getApplicationContext(),List_Groceries,
                this.findViewById(android.R.id.content));


        list.setAdapter(adapter);


    }

    public void addItem()
    {
        AlertDialog.Builder bob = new AlertDialog.Builder(this);
        bob.setTitle("Añadir Elemento");



        final EditText tmp = new EditText(this);
        final EditText tmp2 = new EditText(this);
        LinearLayout lila = new LinearLayout(this);

        lila.setOrientation(LinearLayout.VERTICAL);
        tmp.setHint("Nombre de Elemento");
        tmp2.setHint("Cantidad");
        tmp2.setInputType(InputType.TYPE_CLASS_NUMBER);
        lila.addView(tmp);
        lila.addView(tmp2);
        bob.setView(lila);


        bob.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = "heck";
                int amount = NULL;
                try {
                    name = tmp.getText().toString().trim();
                    amount = Integer.parseInt(tmp2.getText().toString().trim());

                    if(name.equals("") || amount != NULL)
                    {
                        List_Groceries.add(new Grocery(name,amount));
                        updateList();
                        Toast.makeText(getBaseContext(),"Elemento agregado.",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(),"Por favor llene todos los campos."
                                ,Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e)
                {
                    Toast.makeText(getBaseContext(),"Por favor llene todos los campos."
                            ,Toast.LENGTH_LONG).show();
                }



            }
        });
        bob.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        bob.show();
    }
}

class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>
{


    public class GroceryViewHolder extends RecyclerView.ViewHolder
    {
        //TextView name, author;
        protected TextView ItemTextView;
        protected TextView QuanTextView;
        public GroceryViewHolder(View itemView)
        {
            super(itemView);
            ItemTextView = (TextView) itemView.findViewById(R.id.itemTextView);
            QuanTextView = (TextView) itemView.findViewById(R.id.quanTextView);
        }

    }




    private ArrayList<Grocery> list_of_shit;
    private Context context;
    private Grocery deletedItem;
    private int deletedItemPos;
    private View view;

    public GroceryAdapter(Context context, ArrayList<Grocery> arrayList, View view)
    {
        this.context = context;
        this.list_of_shit = arrayList;
        this.view = view;
    }

    public Context getContext()
    {
        return this.context;
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
    public GroceryAdapter.GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item,
                parent, false);
        GroceryViewHolder v = new GroceryViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(GroceryAdapter.GroceryViewHolder holder, int pos)
    {

        Grocery obj = list_of_shit.get(pos);
        holder.ItemTextView.setText(obj.name);
        holder.QuanTextView.setText(Integer.toString(obj.amount));

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rV)
    {
        super.onAttachedToRecyclerView(rV);
    }

    public void deleteItem(int pos)
    {
        deletedItem = list_of_shit.get(pos);
        deletedItemPos = pos;
        list_of_shit.remove(pos);
        notifyItemRemoved(pos);
        showUndoSnackbar();
        if(this.list_of_shit.isEmpty())
        {
            consultarListas.emptyText.setVisibility(View.VISIBLE);
            //MaemptyText.setVisibility(View.VISIBLE);
        }
        else
        {
            consultarListas.emptyText.setVisibility(View.GONE);
        }
    }
    private void showUndoSnackbar()
    {
        Snackbar aloha = Snackbar.make(view,"Lista eliminada.",Snackbar.LENGTH_LONG);
        aloha.setAction("DESHACER", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroceryAdapter.this.undoDelete();
            }
        });
        aloha.show();
    }
    private void undoDelete()
    {
        list_of_shit.add(deletedItemPos,deletedItem);
        notifyItemInserted(deletedItemPos);
        MainActivity.emptyText.setVisibility(View.GONE);
    }





}

class SwipeToDeleteGrocery extends ItemTouchHelper.SimpleCallback
{
    private GroceryAdapter mAdapter;
    private Drawable icon;
    private final ColorDrawable background;

    public SwipeToDeleteGrocery(GroceryAdapter adapter)
    {
        super(0,ItemTouchHelper.LEFT/*|ItemTouchHelper.RIGHT*/);
        mAdapter = adapter;
        icon = ContextCompat.getDrawable(mAdapter.getContext(),R.drawable.ic_action_name);

        background = new ColorDrawable(Color.RED);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // used for up and down movements
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        int position = viewHolder.getAdapterPosition();
        mAdapter.deleteItem(position);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 5;

        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();


        //swipe left
        if (dX < 0)
        {
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);

            background.setBounds(itemView.getRight() + ((int)dX)
                            - backgroundCornerOffset,itemView.getTop(),
                    itemView.getRight(),itemView.getBottom());
        }
        // no swipe
        else
        {
            background.setBounds(0,0,0,0);
        }
        background.draw(c);
        icon.draw(c);
    }

}
