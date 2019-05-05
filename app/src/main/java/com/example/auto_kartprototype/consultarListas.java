package com.example.auto_kartprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class consultarListas extends AppCompatActivity {

    RecyclerView list = (RecyclerView) findViewById(R.id.grocery_list_list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_listas2);

        
    }
}
