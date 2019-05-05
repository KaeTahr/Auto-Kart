package com.example.auto_kartprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
