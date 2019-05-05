package com.example.auto_kartprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class crearLista extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_lista);

    }

    public void createList(View view)
    {
        EditText textBox1 = (EditText) findViewById(R.id.textBox1);
        String text = textBox1.getText().toString();

        if (TextUtils.isEmpty(text))
        {
            Toast.makeText(this,"you didnt write anything dumbass", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this," lol you wrote: " + text, Toast.LENGTH_SHORT).show();
        }
    }
}
