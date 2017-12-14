package com.example.ayfer.onlinedovizbilgileri;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    Button btnconvert;
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

        btnconvert.setOnClickListener(new View.OnClickListener() {

            @Override
            //Birimleri birbirine çevirmek için kullanılan kodlar
            public void onClick(View v) {
                float tlcon1 = Float.parseFloat(tlcon.getText().toString());
                float dolarcon1 = Float.parseFloat(dolarcon.getText().toString());
                float eurocon1 = Float.parseFloat(eurocon.getText().toString());

                if(tlcon1!=0 && dolarcon1==0 && eurocon1==0){
                    dolarcon1 = tlcon1/Float.parseFloat(dolar_1.getText().toString());
                    eurocon1 = tlcon1/Float.parseFloat(euro_1.getText().toString());
                    dolarcon.setText(Float.toString(dolarcon1));
                    eurocon.setText(Float.toString(eurocon1));
                }else if(dolarcon1!=0 && tlcon1==0 && eurocon1==0){
                    tlcon1 = dolarcon1*Float.parseFloat(dolar_2.getText().toString());
                    eurocon1 = dolarcon1*Float.parseFloat(dolar_2.getText().toString())/Float.parseFloat(euro_1.getText().toString());
                    tlcon.setText(Float.toString(tlcon1));
                    eurocon.setText(Float.toString(eurocon1));
                }
                //Eger birden fazla birim girildiyse yanlış deger almamak ıcın EditTextleri sıfırlıyoruz
                else if(eurocon1!=0 && tlcon1==0 && dolarcon1==0){
                    tlcon1 = eurocon1*Float.parseFloat(euro_2.getText().toString());
                    dolarcon1 = eurocon1*Float.parseFloat(euro_2.getText().toString())/Float.parseFloat(dolar_1.getText().toString());
                    tlcon.setText(Float.toString(tlcon1));
                    eurocon.setText(Float.toString(eurocon1));
                }else{
                    tlcon.setText("0");
                    dolarcon.setText("0");
                    eurocon.setText("0");
                }
            }

        });
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

        btnconvert =(Button)findViewById(R.id.buttonConvert);
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
            euro = json.getDouble("euro");
            dolar2 = json.getDouble("dolar2");
            euro2 = json.getDouble("euro2");

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
