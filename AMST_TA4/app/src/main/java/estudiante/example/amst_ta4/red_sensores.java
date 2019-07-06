package estudiante.example.amst_ta4;

/**
 @author: GRUPO DE TRABAJO #1
 CLASE PARA CONSULTAR LA TEMPERATURA, PESO Y HUMEDAD ALMACENADOS
 EN EL SERVIDOR POR EL MODULO THINXTRA
**/

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

public class red_sensores extends AppCompatActivity {

    private RequestQueue mQueue; /*COLA DE PETICIONES HTTPS*/
    private String token = "";/*TOKEN GENERADO POR LA ACTIVIDAD LOGIN
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_sensores);
        mQueue = Volley.newRequestQueue(this); /*INICIALIZACION DE LA COLA DE PETICIONES*/
        
        /*(LINEAS 42:43) EXTRAE EL TOKEN GUARDADO POR LA ACTIVIDAD LOGIN*/
        Intent login = getIntent();
        this.token = (String)login.getExtras().get("token");

        /*METODO ENCARGADO DE CONSULTAR EL VALOR DE LA TEMPERATURA, PESO Y HUMEDAD
        MEDIDOS POR EL SENSOR*/
        revisarSensores();
    }

    /*EL SIGUIENTE METODO CONSULTA EL VALOR DE LA TEMPERATURA, PESO Y HUMEDAD ALMACENADOS EN LA BASE
    DE DATOS DE HEROKUAPP, PARA CADA CONSULTA HTTP SE USA EL TOKEN DE AUTORIZACION, CASO CONTRARIO
    NO SE TENDRA ACCESO A LA INFORMACION DE BD. TODA RESPUESTA POR PARTE DEL SERVIDOR
    ESTA EN FORMATO JSON*/
    private void revisarSensores(){
        /*DECLARACION Y MATCH DE LOS COMPONENTES DE LA GUI PARA MOSTRAR EL VALOR DE LA TEMPERATURA,
        PESO Y HUMEDAD*/
        final TextView tempValue = (TextView) findViewById(R.id.tempVal);
        final TextView pesoValue = (TextView) findViewById(R.id.pesoVal);
        final TextView humedadValue = (TextView) findViewById(R.id.humedadVal);

        String url_temp = "https://amstdb.herokuapp.com/db/logUno/1";/*URL DE LA BASE DE DATOS PARA TEMPERATURA*/

        /*CONSULTA DE LA TEMPERATURA EN FORMATO JSON, EL MISMO ES TRATADO PARA EL EXTRAER EL VALOR NUMERICO
        DE LA VARIABLE, SE HACE USO DEL TOKEN PARA REALIZAR LA CONSULTA.*/
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url_temp, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {

                            tempValue.setText(response.getString("value")+ " C"); /*EXTRACCION DEL VALOR DE LA TEMPERATURA*/
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
        mQueue.add(request); /*SE AÑADE LA SOLICITUD A LA COLA DE PETICIONES*/

        String url_humedad = "https://amstdb.herokuapp.com/db/logUno/2";/*URL DE LA BASE DE DATOS PARA HUMEDAD*/

        /*CONSULTA DE LA HUMEDAD EN FORMATO JSON, EL MISMO ES TRATADO PARA EL EXTRAER EL VALOR NUMERICO
        DE LA VARIABLE, SE HACE USO DEL TOKEN PARA REALIZAR LA CONSULTA.*/
        JsonObjectRequest request_humedad = new JsonObjectRequest(
                Request.Method.GET, url_humedad, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {

                            humedadValue.setText(response.getString("value")+ " C"); /*EXTRACCION DEL VALOR DE LA HUMEDAD*/
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
        mQueue.add(request_humedad); /*SE AÑADE LA SOLICITUD A LA COLA DE PETICIONES*/


        String url_peso = "https://amstdb.herokuapp.com/db/logUno/3";/*URL DE LA BASE DE DATOS PARA PESO*/

        /*CONSULTA DEL PESO EN FORMATO JSON, EL MISMO ES TRATADO PARA EL EXTRAER EL VALOR NUMERICO
        DE LA VARIABLE, SE HACE USO DEL TOKEN PARA REALIZAR LA CONSULTA.*/
        JsonObjectRequest request_peso = new JsonObjectRequest(
                Request.Method.GET, url_peso, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            pesoValue.setText(response.getString("value")+ " C"); /*EXTRACCION DEL VALOR DEL PESO*/
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
        mQueue.add(request_peso); /*SE AÑADE LA SOLICITUD A LA COLA DE PETICIONES*/
    }

    /*METODO QUE PERMITE REGISTRAR UN NUEVO VALOR PARA LA TEMPERATURA, EL DATO ES
    INGRESADO POR USUARIO MEDIANTE UN TEXTEDIT, EL METODO HTTP PARA PUBLICAR EN LA BD
    ES PUT*/
    public void put(View view){
        /*DECLARACION Y MATCH DE LOS COMPONENTES DE LA GUI CON LA FUNCIONALIDAD LOGICA*/
        final TextView txt= (TextView) findViewById(R.id.tempVal);
        final EditText val = (EditText) findViewById(R.id.txtTemp);

        /*METODO QUE PROCESA LA PETICION HTTP PUT*/
        desafio(txt, val.getText().toString());
    }

    /**
    @param editText ETIQUETA
    @param valor VALOR A PUBLICAR EN LA BD
    **/
    private void desafio(final TextView editText , final String valor){
        Map<String, String> params = new HashMap(); /*ESTRUCTURA CLAVE:VALOR PARA LA TEMPERATURA*/
        params.put("value", valor); /*SE CREA LA CLAVE VALUE CON VALOR VALOR*/
        params.put("key", "temperatura"); /*SE CREA LA CLAVE KEY CON VALOR TEMPERATURA*/

        JSONObject parametros = new JSONObject(params); /*GENERACION DE SATOS EN FORMATO JSON A PARTIR DE PARAMS*/

        String login_url = "https://amstdb.herokuapp.com/db/logUno/1";/*URL A LA BD DONDE SE PUBLICAR EL DATO INGRESADO*/

        /*GENERA LA SOLICITUD EN FORMATO JSON, LA SOLICITUD ES DE TIPO PUT, Y PERMITE PUBLICAR EL VALOR DE TEMPERATURA
        INGRESADO POR EL USUARIO, PARA LA SOLICITUD SE DEBE USAR EL TOKEN DE AUTORIZACION GENERADO POR LOGIN*/
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT, login_url, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            //token = response.getString("token");
                            /*Intent menuPrincipal = new
                                    Intent(getBaseContext(), menu.class);
                            menuPrincipal.putExtra("token", token);*/
                            //startActivity(menuPrincipal);
                            editText.setText(valor);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            /*MANEJO DE ERRORES EN CASO DE PRESENTARSE UNA ANOMALIA EN LA AUTENTICACION DEL TOKEN*/
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog alertDialog = new
                        AlertDialog.Builder(red_sensores.this).create();
                alertDialog.setTitle("Alerta");
                alertDialog.setMessage("Credenciales Incorrectas");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int
                                    which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show(); /*MUESTRA EL MENSAJE DE ERROR*/
            }
        }){
            /*PUBLICACION SE DEL NUEVO VALOR, NOTAR QUE ES NECESARIO EL USO DEL TOKEN DE AUTORIZACION*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "JWT " + token);
                System.out.println(token);
                return params;
            }
        };;
        
        mQueue.add(request); /*SE AÑADE LA SOLICITUD A LA COLA DE PETICIONES*/
    }
}
