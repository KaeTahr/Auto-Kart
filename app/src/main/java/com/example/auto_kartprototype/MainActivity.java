package com.example.auto_kartprototype;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class MainActivity extends AppCompatActivity {
    // our list of grocery list
    // list-ception
    // that was the most reddit thing i've said in a while please sign me up for a gas chamber
    public static ArrayList<GroceryList> List_GroceryLists = new ArrayList<GroceryList>();
    public static String User = "Pepito";

    static final int UPDATE_LIST_REQUEST = 1;
    static final int UPDATE_GROCERIES_REQUEST = 1;

    RecyclerView list;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager lMan;
    public static TextView emptyText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Perfil: "+User);
        setSupportActionBar(toolbar);

        ImageView imgButton = (ImageView)findViewById(R.id.invitationButton);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Invitaciones.class);
                startActivityForResult(startIntent,UPDATE_LIST_REQUEST);
            }
        });


      //  getSupportActionBar().setDisplayShowTitleEnabled(false);



        //TextView curUser = findViewById(R.id.UserName);
        //curUser.append(User);

        updateList();
        ItemTouchHelper iTH = new ItemTouchHelper(new SwipeToDeleteThing((GroceryListAdapter) adapter));
        iTH.attachToRecyclerView(list);

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
                Snackbar.make(findViewById(android.R.id.content),"Nueva Lista Agregada."
                        ,Snackbar.LENGTH_LONG).show();

                if(List_GroceryLists.isEmpty())
                {
                    list.setVisibility(View.GONE);
                    emptyText.setVisibility(View.VISIBLE);
                }
                else
                {
                    list.setVisibility(View.VISIBLE);
                    emptyText.setVisibility(View.GONE);
                }
            }
            /*else
            {
                adapter.notifyDataSetChanged();
            }*/
        }
    }

    public void updateList()
    {
        list = (RecyclerView) findViewById(R.id.grocery_list_list);
        emptyText = (TextView) findViewById(R.id.empty_main);

        if(List_GroceryLists.isEmpty())
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

        adapter = new GroceryListAdapter(getApplicationContext(),List_GroceryLists,
                this.findViewById(android.R.id.content));

        ((GroceryListAdapter) adapter).setOnEntryClickListener(new GroceryListAdapter.OnEntryClickListener() {
            @Override
            public void onEntryClick(View view, int position) {

                    Context context = view.getContext();
                    Intent startIntent = new Intent(context, consultarListas.class);

                    Bundle bum = new Bundle();

                    bum.putSerializable("selectedItem",(Serializable) List_GroceryLists.get(position));

                    startIntent.putExtras(bum);
                    startIntent.putExtra("selIndex",position);
                    ((Activity) context).startActivityForResult(startIntent,UPDATE_GROCERIES_REQUEST);

            }
        });

        list.setAdapter(adapter);


    }


}

class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.GroceryListViewHolder>
{


    private OnEntryClickListener mOnEntryClickListener;

    public interface OnEntryClickListener
    {
        void onEntryClick(View view, int position);
    }

    public void setOnEntryClickListener(OnEntryClickListener oECL)
    {
        mOnEntryClickListener = oECL;
    }



    public class GroceryListViewHolder extends RecyclerView.ViewHolder
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
            mOnEntryClickListener.onEntryClick(v,getLayoutPosition());
        }
    }




    private ArrayList<GroceryList> list_of_shit;
    private Context context;
    private GroceryList deletedItem;
    private int deletedItemPos;
    private View view;

    public GroceryListAdapter(Context context, ArrayList<GroceryList> arrayList, View view)
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

    public void deleteItem(int pos)
    {
        deletedItem = list_of_shit.get(pos);
        deletedItemPos = pos;
        list_of_shit.remove(pos);
        notifyItemRemoved(pos);
        showUndoSnackbar();
        if(this.list_of_shit.isEmpty())
        {
            MainActivity.emptyText.setVisibility(View.VISIBLE);
            //MaemptyText.setVisibility(View.VISIBLE);
        }
        else
        {
            MainActivity.emptyText.setVisibility(View.GONE);
        }
    }
    private void showUndoSnackbar()
    {
        Snackbar aloha = Snackbar.make(view,"Lista eliminada.",Snackbar.LENGTH_LONG);
        aloha.setAction("DESHACER", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroceryListAdapter.this.undoDelete();
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

class SwipeToDeleteThing extends ItemTouchHelper.SimpleCallback
{
    private GroceryListAdapter mAdapter;
    private Drawable icon;
    private final ColorDrawable background;

    public SwipeToDeleteThing(GroceryListAdapter adapter)
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
                            float dX, float dY, int actionState,boolean isCurrentlyActive)
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
