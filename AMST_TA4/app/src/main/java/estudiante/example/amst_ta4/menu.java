/**
 @author: GRUPO DE TRABAJO #1 - Arguello, Cedeño, Gilces, Miranda
 @version: 1.0
**/
package estudiante.example.amst_ta4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
/**
 @author: GRUPO DE TRABAJO #1 - Arguello, Cedeño, Gilces, Miranda
 @version: 1.0
 LA PRESENTE CLASE REPRESENTA LA FUNCIONALIDAD A NIVEL LOGICO DEL MENU DE OPCIONES.
 EL MENU CONTIENE DOS BOTONES, UNO PARA SALIR DE LA APLICACION Y OTRO PARA REVISAR LOS SENSORES.
 **/
public class menu extends AppCompatActivity {
    /*
    DECLARACIÓN DE VARIABLES
    TOKEN: PERMITE EL USO CORRECTO DE LA ACTIVIDAD
    */
    String token = "";

    /**
     @author: GRUPO DE TRABAJO #1 - Arguello, Cedeño, Gilces, Miranda
     @version: 1.0
     @description: CREACIÓN DE INSTANCIA
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        /*INTENT PARA EXTRAER EL TOKEN GUARDADO POR LA ACTIVIDAD DE LOGIN*/
        Intent login = getIntent();
        /*EXTRACCION DEL TOKEN*/
        this.token = (String)login.getExtras().get("token");
    }

    /**
     @author: GRUPO DE TRABAJO #1 - Gilces
     @version: 1.0
     @description: METODO PARA SALIR DE LA ACTIVIDAD ACTUAL
    **/
    public void Salir(View v){
        this.finish();
        System.exit(0);
    }

    /**
     @author: GRUPO DE TRABAJO #1 - Miranda
     @version: 1.0
     @description: METODO PARA IR A LA ACTIVIDAD RED_SENSORES
     **/
    public void revisarSensores(View v){
        Intent red_sensores = new Intent(getBaseContext(), red_sensores.class);
        red_sensores.putExtra("token", token);
        startActivity(red_sensores);
    }
}
