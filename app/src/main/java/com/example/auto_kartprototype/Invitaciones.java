package com.example.auto_kartprototype;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Invitaciones extends AppCompatActivity {

    static final int UPDATE_LIST = 1;
    static boolean first_run = true;
    static ArrayList<GroceryList> ListInvitations = new ArrayList<GroceryList>();
    RecyclerView list;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager lMan;
    TextView emptyText;

    GroceryList titorList;
    GroceryList hydeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitaciones);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(first_run)
        {
            fillLists();

            ListInvitations.add(titorList);
            ListInvitations.add(hydeList);
            first_run = false;
        }
        updateList();

    }

    public void fillLists()
    {
        titorList = new GroceryList("A Time-Travel Adventure", "John Titor");
        hydeList = new GroceryList(
                "How To Legally Build A Bomb For Explicitly Self-Defense Purposes",
                "Samuel Hyde");

        titorList.addItem(new Grocery("IBM 5100",1));
        titorList.addItem(new Grocery("Feels",9001));

        hydeList.addItem(new Grocery("Nitroclycerin, Oz.", 30));
        hydeList.addItem(new Grocery("Sawdust, Oz.", 10));
        hydeList.addItem(new Grocery("Sodium Carbonate, Oz.", 1));
        hydeList.addItem(new Grocery("Sodium Nitrate, Oz.", 5));

    }

    public void updateList()
    {
        list = (RecyclerView) findViewById(R.id.invitation_list);
        emptyText = (TextView) findViewById(R.id.empty_view);

        if(ListInvitations.isEmpty())
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

        adapter = new InvitationListAdapter(getApplicationContext(),ListInvitations,
                this.findViewById(android.R.id.content));

        ((InvitationListAdapter) adapter).setOnEntryClickListener(new InvitationListAdapter.OnEntryClickListener() {
            @Override
            public void onEntryClick(View view, final int position) {

                AlertDialog.Builder bob = new AlertDialog.Builder(view.getContext());
                bob.setMessage("¿Aceptar Invitación?");
                bob.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do something when accept
                        GroceryList stuff = ListInvitations.get(position);
                        MainActivity.List_GroceryLists.add(ListInvitations.get(position));
                        setResult(RESULT_OK);
                        ((InvitationListAdapter) adapter).deleteItem(position,false);
                        updateList();
                    }
                });
                bob.setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // when it's a nogo do dis
                        ((InvitationListAdapter) adapter).deleteItem(position,true);
                        updateList();
                    }
                });

                AlertDialog dialog = bob.create();
                dialog.show();
            }
        });

        list.setAdapter(adapter);

       // ItemTouchHelper iTH = new ItemTouchHelper(new SwipeToDeleteThing((GroceryListAdapter) adapter));
        //iTH.attachToRecyclerView(list);
    }

}

class InvitationListAdapter extends RecyclerView.Adapter<InvitationListAdapter.InvitationListViewHolder>
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



    public class InvitationListViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        //TextView name, author;
        protected TextView NameTextView;
        protected TextView AuthorTextView;
        public InvitationListViewHolder(View itemView)
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

    public InvitationListAdapter(Context context, ArrayList<GroceryList> arrayList, View view)
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
    public InvitationListAdapter.InvitationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_list_item,
                parent, false);
        InvitationListViewHolder v = new InvitationListViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(InvitationListAdapter.InvitationListViewHolder holder, int pos)
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

    public void deleteItem(int pos,boolean save)
    {
        deletedItem = list_of_shit.get(pos);
        deletedItemPos = pos;
        list_of_shit.remove(pos);
        notifyItemRemoved(pos);
        if(save) showUndoSnackbar();
        else showSaveSnackbar();
    }
    private void showUndoSnackbar()
    {
        Snackbar aloha = Snackbar.make(view,"Invitación Rechazada.",Snackbar.LENGTH_LONG);
        aloha.show();
    }
    private void showSaveSnackbar()
    {
        Snackbar aloha = Snackbar.make(view,"Invitación Agregada.",Snackbar.LENGTH_LONG);
        aloha.show();
    }
    private void undoDelete()
    {
        list_of_shit.add(deletedItemPos,deletedItem);
        notifyItemInserted(deletedItemPos);
    }



}
