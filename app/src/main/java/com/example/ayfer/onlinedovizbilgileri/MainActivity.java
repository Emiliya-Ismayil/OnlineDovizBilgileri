package com.example.ayfer.onlinedovizbilgileri;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "http://www.doviz.gen.tr/doviz_json.asp?version=1.0.4";

    AQuery aq;
    Context curContext;

    // Gereklı olan widgetlarımızı tanıttık
    EditText tlcon,dolarcon,eurocon,dolar_1,dolar_2,euro_1,euro_2;
    TextView UptadeTime;
    float tlcon1, dolarcon1, eurocon1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        //NumberFormatException hatası almamak için birim çeviri kısmındakı EditText değerlerini "0" atıyoruz
        tlcon.setText("0");
        dolarcon.setText("0");
        eurocon.setText("0");
        tlcon1 = Float.parseFloat(tlcon.getText().toString());
        dolarcon1 = Float.parseFloat(dolarcon.getText().toString());
        eurocon1 = Float.parseFloat(eurocon.getText().toString());

        curContext = this.getApplicationContext();
        aq = new AQuery(curContext);

        loadData();
    }

    public void findView(){
        tlcon=(EditText)findViewById(R.id.editText1);
        dolarcon=(EditText)findViewById(R.id.editText2);
        eurocon=(EditText)findViewById(R.id.editText3);

        dolar_1=(EditText)findViewById(R.id.editTextDolar1);
        dolar_2=(EditText)findViewById(R.id.editTextDolar2);
        euro_1=(EditText)findViewById(R.id.editTextEuro1);
        euro_2=(EditText)findViewById(R.id.editTextEuro2);

        UptadeTime = (TextView) findViewById(R.id.textViewTime1);
    }

    public void loadData() {
        aq.ajax(URL , JSONObject.class, this, "jsonCallbackLoadData");
    }

    public void jsonCallbackLoadData(String url, JSONObject json, AjaxStatus status) throws JSONException {
        boolean jsonStatus = false;
        String jsonMsg = "", strJsonData = "";
        Double dolar, dolar2, euro, euro2;

        String LastUptadeTime;
        String LastRegisterDate ;

        if (json == null)
        {
            if(status.getCode() == 500){
                Toast.makeText(curContext, "Server is busy or down. Try again!", Toast.LENGTH_LONG).show();
            }
            else if(status.getCode() == 404){
                Toast.makeText(curContext, "Resource not found!", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(curContext, "Unexpected Error occured", Toast.LENGTH_LONG).show();
            }
            return;
        }

        try {
            //Siteden gelen veriyi JSON Objemize atıyoruz

            dolar = json.getDouble("dolar");
            euro = json.getDouble("dolar");
            dolar2 = json.getDouble("dolar");
            euro2 = json.getDouble("dolar");

            LastUptadeTime = json.getString("guncelleme");
            LastRegisterDate = json.getString("sonkayit");

            //Verilerimizi ekrana yazdırıyoruz
            dolar_1.setText(dolar.toString());
            euro_1.setText(euro.toString());
            dolar_2.setText(dolar2.toString());
            euro_2.setText(euro2.toString());
            UptadeTime.setText(LastUptadeTime.toString()+"/n"+LastRegisterDate.toString());
            
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
