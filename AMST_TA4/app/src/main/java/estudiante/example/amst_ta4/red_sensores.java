/**
 @author: GRUPO DE TRABAJO #1 - Arguello, Cedeño, Gilces, Miranda
 @version: 1.0
 **/
package estudiante.example.amst_ta4;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 @author: GRUPO DE TRABAJO #1
 @version: 1.0
 CLASE PARA CONSULTAR LA TEMPERATURA, PESO Y HUMEDAD ALMACENADOS
 EN EL SERVIDOR POR EL MODULO THINXTRA
 **/
public class red_sensores extends AppCompatActivity {

    /*
    DECLARACIÓN DE VARIABLES
    mQueue: COLA DE SOLICITUDES PARA PETICIONES HTT.
    token: VARIABLE QUE ALMACENA EL TOKEN SOLICITADO
    */
    private RequestQueue mQueue;
    private String token = "";

    /**
     @author: GRUPO DE TRABAJO #1 - Arguello, Cedeño, Gilces, Miranda
     @version: 1.0
     @description: CREACIÓN DE INSTANCIA
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_sensores);

        /*INICIALIZACION DE LA COLA DE PETICIONES*/
        mQueue = Volley.newRequestQueue(this);
        
        /*(LINEAS 42:43) EXTRAE EL TOKEN GUARDADO POR LA ACTIVIDAD LOGIN*/
        Intent login = getIntent();
        this.token = (String)login.getExtras().get("token");

        /*METODO ENCARGADO DE CONSULTAR EL VALOR DE LA TEMPERATURA, PESO Y HUMEDAD
        MEDIDOS POR EL SENSOR*/
        revisarSensores();
    }

    /**
     @author: GRUPO DE TRABAJO #1 - Arguello, Cedeño, Gilces, Miranda
     @version: 1.0
     @description: CONSULTA EL VALOR DE LA TEMPERATURA, PESO Y HUMEDAD ALMACENADOS EN LA BASE
     DE DATOS DE HEROKUAPP, PARA CADA CONSULTA HTTP SE USA EL TOKEN DE AUTORIZACION, CASO CONTRARIO
     NO SE TENDRA ACCESO A LA INFORMACION DE BD. TODA RESPUESTA POR PARTE DEL SERVIDOR
     ESTA EN FORMATO JSON*/

    private void revisarSensores(){
        final TextView tempValue = (TextView) findViewById(R.id.tempVal);
        final TextView acexValue = (TextView) findViewById(R.id.aceleracionVal);

        /*URL DE LA BASE DE DATOS*/
        String url_temp = "https://amstdb.herokuapp.com/db/logDos/20";

        /*CONSULTA DE LA TEMPERATURA Y ACELERACIÓN EN FORMATO JSON, EL MISMO ES TRATADO PARA
        EXTRAER EL VALOR NUMERICO DE LAS VARIABLES, SE HACE USO DEL TOKEN PARA REALIZAR LA CONSULTA.*/
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url_temp, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            tempValue.setText(response.getString("value").split(";")[0]+ " C");
                            acexValue.setText(response.getString("value").split(";")[1]+ " ");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            /*VALIDACION DEL TOKEN*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "JWT " + token);
                System.out.println(token);
                return params;
            }
        };;

        /*SE AÑADE LA SOLICITUD A LA COLA DE PETICIONES*/
        mQueue.add(request);
    }



}
