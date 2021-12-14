package com.example.consumirapiconparametros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.style.IconMarginSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    TextView tvInformacion;
    RequestQueue requestQueue;
    EditText Parametro;
    private static final String ARG_PARAM1 = "param1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInformacion = findViewById(R.id.TvLista);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void btEnviar(View view) {

        Parametro = (EditText)findViewById(R.id.txtParametro1);
        CasoVolley();

    }
    private void CasoVolley() {
        String codigo= Parametro.getText().toString();
        tvInformacion.setText("");
        tvInformacion.append("Google Volley \n");
        String url = "https://api-uat.kushkipagos.com/transfer-subscriptions/v1/bankList";

        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET,
                url,null, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // tvInformacion.append("\n info: "+response.getJSONObject(1).toString());

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject info= new JSONObject(response.get(i).toString());
                        if((info.getString("code")).equals(codigo)) {
                            String contentido = "";
                            contentido += ("codigo: " + info.getString("code") + "; \n ");
                            contentido += ("Nombre: " + info.getString("name") + "; \n");
                            contentido += ("\n");
                            tvInformacion.append(contentido);
                       }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } , new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.getMessage());
            }
        }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("dataType", "json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Public-Merchant-Id", "84e1d0de1fbf437e9779fd6a52a9ca18");
                return headers;

            }

            @Override
            public String getBodyContentType() {
                //Establece la codificación para los datos del web service
                return "application/json; charset=utf-8";
            }

        };
                requestQueue.add(request);
    }
}