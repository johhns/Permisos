package com.johhns.permisos;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_BLUETOOTH = 1 ;
    private static final int CODIGO_SOLICITUD = 0 ;

    ActivityResultLauncher<Intent> intentLauncher ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main", "ON CREATE") ;

        ActivityResultLauncher<Intent> abreIntents = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            //doSomeOperations();
                        }
                    }
                });





        Button btnBlueTooth = (Button) findViewById(R.id.btnBlueTooth) ;
        btnBlueTooth.setOnClickListener(new View.OnClickListener() {
            BluetoothAdapter miBlueTooth = BluetoothAdapter.getDefaultAdapter();
            public void onClick(View view) {
                //Toast.makeText( getBaseContext() , "Pidiendo permiso bluetooth" , Toast.LENGTH_LONG ).show();
                solicitarPermiso();

                Log.d("Main", "Verificando si el dispositivo tiene blutu");

                if (miBlueTooth == null) {
                    Toast.makeText(getApplicationContext(), "Tu dispositivo NO tiene bluetooth", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Tu dispositivo SI tiene bluetooth", Toast.LENGTH_LONG).show();

                }
                if (!miBlueTooth.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "NO ESTA HABILITADO", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "SI ESTA HABILITADO", Toast.LENGTH_LONG).show();
                }
                Log.d("Main", "VERIFICANDO SI ESTA HABILITADO BLUeTOOOOOOOOOOOOOOOOOOOOOOOOOTH");
                if (!miBlueTooth.isEnabled()) {
                    Intent habilitarBlue = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    abreIntents.launch(habilitarBlue);
                    Log.d("Main", "ANTES DE INTENT LAUNCHER");
                }
                Log.d("Main", "FFFFFFFFFFFFFFFFIIIIIIIIIIIIIIIIIIIIIIIIIIINNNNNNNNNNNNNNNNNNN");
            }
        });
        Log.d("Main", "Actividad iniciada") ;
    }

    public boolean estaActivoBlueTooth(){
        int estadoActual = ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.BLUETOOTH) ;
        if ( estadoActual == PackageManager.PERMISSION_GRANTED) {
            return  true ;
        } else {
            return false ;
        }
    }

    public void solicitarPermiso(){
        if (ActivityCompat.shouldShowRequestPermissionRationale( this , Manifest.permission.BLUETOOTH  )){
            Toast.makeText( this , "Este permiso ya fue otorgado", Toast.LENGTH_LONG ).show();
        } else {
            ActivityCompat.requestPermissions( this , new String[] {Manifest.permission.BLUETOOTH }, CODIGO_BLUETOOTH );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ( requestCode == CODIGO_BLUETOOTH ) {
            Toast.makeText( this , "Ya esta activo el permiso", Toast.LENGTH_LONG ).show();
        }
    }
}