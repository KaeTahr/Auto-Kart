package com.example.auto_kartprototype;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class crearLista extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_lista);
        getSupportActionBar().setTitle("Crear Nueva Lista");

    }
    public void addPerson(View view)
    {
        AlertDialog.Builder bob = new AlertDialog.Builder(this);
        bob.setTitle("Crear Nueva Invitación");
        EditText tmp = new EditText(this);
        tmp.setHint("Usuario");
        bob.setView(tmp);

        boolean sendInvite = false;

        bob.setPositiveButton("Invitar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(),"Invitación enviada.",Toast.LENGTH_LONG).show();
            }
        });
        bob.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        bob.show();

        //Toast.makeText(this,"Invitación enviada.",Toast.LENGTH_LONG).show();

    }

    public void createList(View view)
    {
        EditText textBox1 = (EditText) findViewById(R.id.textBox1);
        String text = textBox1.getText().toString();

        if (TextUtils.isEmpty(text))
        {

           // Toast.makeText(this,"", Toast.LENGTH_SHORT).show();
            Snackbar.make(view,"¡Debe nombrar la lista!",Snackbar.LENGTH_LONG).show();
        }
        else
        {
            MainActivity.List_GroceryLists.add(new GroceryList(text));


           // Toast.makeText(this," Lista Agregada: " + text, Toast.LENGTH_SHORT).show();

            //Intent returnIntent = new Intent();
            //returnIntent.putExtra()
            setResult(RESULT_OK);
            this.finish();
        }
    }
}
