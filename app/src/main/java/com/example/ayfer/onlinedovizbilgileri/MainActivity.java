package com.example.ayfer.onlinedovizbilgileri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "http://www.doviz.gen.tr/doviz_json.asp?version=1.0.4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gereklı olan widgetlarımızı tanıttık
        final EditText tlcon=(EditText)findViewById(R.id.editText1);
        final EditText dolarcon=(EditText)findViewById(R.id.editText2);
        final EditText eurocon=(EditText)findViewById(R.id.editText3);


        final EditText dolar_1=(EditText)findViewById(R.id.editTextDolar1);
        final EditText dolar_2=(EditText)findViewById(R.id.editTextDolar2);
        final EditText euro_1=(EditText)findViewById(R.id.editTextEuro1);
        final EditText euro_2=(EditText)findViewById(R.id.editTextEuro2);
        TextView UptadeTime = (TextView) findViewById(R.id.textViewTime1);
//NumberFormatException hatası almamak için birim çeviri kısmındakı EditText değerlerini "0" atıyoruz
        tlcon.setText("0");
        dolarcon.setText("0");
        eurocon.setText("0");
        final float tlcon1=Float.parseFloat(tlcon.getText().toString());
        final float dolarcon1=Float.parseFloat(dolarcon.getText().toString());
        final float eurocon1=Float.parseFloat(eurocon.getText().toString());


    }

}
