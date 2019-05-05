package com.example.auto_kartprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // our list of grocery list
    // list-ception
    // that was the most reddit thing i've said in a while please sign me up for a gas chamber
    public static ArrayList<GroceryList> List_GroceryLists = new ArrayList<GroceryList>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnNuevaLista = (Button) findViewById(R.id.btnNuevaLista);
        btnNuevaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), crearLista.class);
                startActivity(startIntent);

            }
        });
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
        });

    };

}
