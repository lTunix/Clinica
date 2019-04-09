package com.blogspot.estamoscercadelfututro.clinica;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;

public class ListarPacientes extends AppCompatActivity
{
    private ListView lvPacientes;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pacientes);
        generarPacientes(getCurrentFocus());
        lvPacientes=(ListView)findViewById(R.id.ListarPacientes);
        lvPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                String itemValue = lvPacientes.getItemAtPosition(position).toString();
                String[] datos = itemValue.split(" ");
                int idPaciente = Integer.parseInt(datos[0]);
                SQLiteHelper db = new SQLiteHelper(getApplicationContext());
                Cursor cursor = db.buscarPaciente(idPaciente);
                if(cursor.moveToNext()) {
                    Intent i = new Intent(getApplicationContext(), ModificarPacientes.class);
                    i.putExtra("Id",""+cursor.getInt(cursor.getColumnIndex("id")));
                    i.putExtra("nombre",cursor.getString(cursor.getColumnIndex("nombre")));
                    i.putExtra("apellido",cursor.getString(cursor.getColumnIndex("apellido")));
                    i.putExtra("edad",""+cursor.getShort(cursor.getColumnIndex("edad")));
                    i.putExtra("fechaNacimiento",cursor.getString(cursor.getColumnIndex("fechanacimiento")));
                    i.putExtra("sexo",cursor.getString(cursor.getColumnIndex("sexo")));
                    i.putExtra("direccion",cursor.getString(cursor.getColumnIndex("direccion")));
                    i.putExtra("comuna",cursor.getString(cursor.getColumnIndex("comuna")));
                    i.putExtra("numeroTelefono",""+cursor.getInt(cursor.getColumnIndex("numerotelefono")));
                    i.putExtra("numeroCelular",""+cursor.getInt(cursor.getColumnIndex("numerocelular")));
                    i.putExtra("alergia",cursor.getString(cursor.getColumnIndex("alergico")));
                    startActivity(i);
                    ListarPacientes.super.finish();
                }
            }
        });
    }

    public void generarPacientes(View view) {
        SQLiteHelper db = new SQLiteHelper(this);
        ListView lv1 = (ListView)findViewById(R.id.ListarPacientes);
        LinkedList<String> listaPacientes = new LinkedList<>();
        Cursor cursor = db.listaPacientes();
        while(cursor.moveToNext()) {
            listaPacientes.add(cursor.getInt(cursor.getColumnIndex("id"))+" "+cursor.getString(cursor.getColumnIndex("nombre"))+
                    " "+cursor.getString(cursor.getColumnIndex("apellido")));
        }
        cursor.close();

        ArrayAdapter<String> aa1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaPacientes);
        lv1.setAdapter(aa1);
    }
}
