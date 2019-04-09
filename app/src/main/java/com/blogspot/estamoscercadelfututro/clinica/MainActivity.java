package com.blogspot.estamoscercadelfututro.clinica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity
{
    private static final int STORAGE = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }else {

                }
                return;
            }
        }
    }


    public void changeScreenRegister(View view) {
        Intent i = new Intent(this,RegistrarPaciente.class);
        startActivity(i);
    }

    public void changeScreenList(View view) {
        Intent i = new Intent(this,ListarPacientes.class);
        startActivity(i);
    }

    public void createFile(View view) {
        try {
            SQLiteHelper db = new SQLiteHelper(this);
            Cursor cursor = db.listaPacientes();
            if(cursor.moveToNext()) {
                cursor = db.listaPacientes();
                File fileToSdCard = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "ListaPacientes.txt");
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fileToSdCard));
                while(cursor.moveToNext()) {
                    String sexo = null;
                    if(cursor.getString(cursor.getColumnIndex("sexo")).charAt(0) == 'M')
                        sexo = "Masculino";
                    else
                        sexo = "Femenino";
                    String alergico = null;
                    if(cursor.getString(cursor.getColumnIndex("alergico")).charAt(0) == 'S')
                        alergico = "Sí";
                    else
                        alergico = "No";
                    osw.write(cursor.getInt(cursor.getColumnIndex("id"))+" "+cursor.getString(cursor.getColumnIndex("nombre"))+" "+
                            cursor.getString(cursor.getColumnIndex("apellido"))+" "+cursor.getShort(cursor.getColumnIndex("edad"))+" "+
                            cursor.getString(cursor.getColumnIndex("fechanacimiento"))+" "+sexo+" "+cursor.getString(cursor.getColumnIndex("direccion"))+
                            " "+cursor.getString(cursor.getColumnIndex("comuna"))+" "+cursor.getInt(cursor.getColumnIndex("numerotelefono"))+" "+
                            cursor.getInt(cursor.getColumnIndex("numerocelular"))+" "+alergico+"\n");
                }
                osw.flush();
                osw.close();
                Toast.makeText(this, "¡Los datos fueron guardados exitosamente! :)",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "No existen datos aún :(",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e) {
            Toast.makeText(this, "Error al guardar los datos :( : "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void exitScreen(View view)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        finish();
        System.exit(0);
    }

}
