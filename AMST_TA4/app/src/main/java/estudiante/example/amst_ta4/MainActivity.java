package estudiante.example.amst_ta4;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue = null; /*COLOA DE SOLICITUDES PARA PETICIONES HTTP*/
    private String token = null; /*VARIABLE QUE ALMACENA EL TOKEN SOLICITADO*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this); /*INCIALIZACION DE LA COLA DE PETICIONES*/
    }

    public void irMenuPrincipal(View v){
    	/*MATCHING ENTRE OBJETOS LOGICOS Y OBJETOS DE DISEÑO*/
        final EditText usuario = (EditText) findViewById(R.id.txtUsuario);
        final EditText password = (EditText) findViewById(R.id.txtContrasena);

        /*VARIABLES PARA GUARDAR USUARIO Y CONTRASEÑA INGRESADOS POR EL USUARIO*/
        String str_usuario = usuario.getText().toString();
        String str_password = password.getText().toString();

        /*FUNCION INICIAR SESION*/
        iniciarSesion(str_usuario,str_password);
    }

    /**
    @author: GRUPO DE TRABAJO #1
    @param usuario NOMBRE DE USUARIO
    @param password CONTRASEÑA INGRESADA POR EL USUARIO
    METODO ENCARGADA DE LA AUTENTICACION Y AUTIZACION DEL USUARIO INGRESADO PARA EL USO
    DE LA APLICACION, SE USA EL METODO DE AUTENTICACION BASADA EN TOKEN.
    **/
    private void iniciarSesion(String usuario, String password){

        Map<String, String> params = new HashMap(); /*ESTRUCTURA PARA MODELAR DATOS CLAVE:VALOR*/
        params.put("username", usuario); /*AÑADE LA CLAVE USERNAME CON VALOR USUARIO*/
        params.put("password", password); /*AÑADE LA CLAVE PASSWORD CON VALOR PASSWORD*/
        JSONObject parametros = new JSONObject(params); /*CREACION DE OBJETO JSON A PARTIR DE LA ESTRUCTURA PARAMS*/
        String login_url = "https://amstdb.herokuapp.com/db/nuevo-jwt"; /*URL QUE PERMITE LA SOLICITUD DEL TOKEN*/

        /*SOLICITUD FORMATEADA EN FORMATO JSON, COMO RESULTADO SE GENERA UN TOKEN SEGUN EL USUARIO ENVIADO AL SERVIDOR
        ADICIONALMENTE SE ALMACENA EL TOKEN PARA QUE LAS DEMAS ACTIVIDADES VERIFIQUEN EL TOKEN*/
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, login_url, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            token = response.getString("token");
                            Intent menuPrincipal = new
                                    Intent(getBaseContext(), menu.class);
                            menuPrincipal.putExtra("token", token);
                            startActivity(menuPrincipal);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            /*METODO PARA EL MANEJO DE ERROR EN LA RESPUESTA DE LA SOLICITU GENERADA*/
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog alertDialog = new
                        AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Alerta");
                alertDialog.setMessage("Credenciales Incorrectas");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int
                                    which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();/*MUESTRA EL MENSAJE DE ERROR*/
            }
        });
        mQueue.add(request);/*AGREGA LA SOLICITUD A LA COLA DE PETICIONES*/
    }
}
