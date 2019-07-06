/**
 @author: GRUPO DE TRABAJO #1
 LA PRESENTE CLASE REPRESENTA LA FUNCIONALIDAD A NIVEL LOGICO DEL MENU DE OPCIONES.
 EL MENU CONTIENE DOS BOTONES, UNO PARA SALIR DE LA AOLICACION Y OTRO PARA REVISAR LOS SENSORES
**/
package estudiante.example.amst_ta4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class menu extends AppCompatActivity {

    String token = ""; /*EL TOKEN PERMITE EL USO CORRECTO DE LA ACTIVIDAD*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent login = getIntent(); /*INTENT PARA EXTRAER EL TOKEN GUARDADO POR LA ACTIVIDAD DE LOGIN*/
        this.token = (String)login.getExtras().get("token"); /*EXTRACCION DEL TOKEN*/
    }

    /**
    METODO PARA SALIR DE LA ACTIVIDAD ACTUAL 
    **/
    public void Salir(View v){
        this.finish();
        System.exit(0);
    }

    /*METODO PARA IR A LA ACTIVIDAD RED_SENSORES*/
    public void revisarSensores(View v){
        Intent red_sensores = new Intent(getBaseContext(), red_sensores.class);
        red_sensores.putExtra("token", token);
        startActivity(red_sensores);
    }
}
