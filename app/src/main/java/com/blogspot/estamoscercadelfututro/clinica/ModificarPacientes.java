package com.blogspot.estamoscercadelfututro.clinica;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.LinkedList;

public class ModificarPacientes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_pacientes);
        cargarComunas();
        generarDatos();
    }

    public void openCalendarModify(View view) {
        DatePicker newFragment = DatePicker.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                String dia = "" + day, mes = "" + (month + 1);
                if (dia.length() == 1)
                    dia = "0" + day;
                if (mes.length() == 1)
                    mes = "0" + (month + 1);
                final String selectedDate = year + "-" + mes + "-" + dia;
                EditText fechaNacimiento = (EditText) findViewById(R.id.txtFecha);
                fechaNacimiento.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void generarDatos() {

        EditText nombrePaciente = (EditText) findViewById(R.id.txtNombre);
        EditText apellidoPaciente = (EditText) findViewById(R.id.txtApellido);
        EditText edadPaciente = (EditText) findViewById(R.id.txtEdad);
        EditText fechaNacimientoPaciente = (EditText) findViewById(R.id.txtFecha);
        RadioButton rbmm = (RadioButton) findViewById(R.id.Masculino);
        RadioButton rbmf = (RadioButton) findViewById(R.id.Femenino);
        EditText direccionPaciente = (EditText) findViewById(R.id.txtDir);
        Spinner comunaPaciente = (Spinner) findViewById(R.id.SpinnerComunas);
        EditText numeroTelefono = (EditText) findViewById(R.id.txtTelefono);
        EditText numeroCelular = (EditText) findViewById(R.id.txtCelular);
        RadioButton rbmsi = (RadioButton) findViewById(R.id.Si);
        RadioButton rbmno = (RadioButton) findViewById(R.id.No);

        int id = Integer.parseInt(getIntent().getExtras().getString("Id"));
        nombrePaciente.setText(getIntent().getExtras().getString("nombre"));
        apellidoPaciente.setText(getIntent().getExtras().getString("apellido"));
        edadPaciente.setText("" + Short.parseShort(getIntent().getExtras().getString("edad")));
        fechaNacimientoPaciente.setText(getIntent().getExtras().getString("fechaNacimiento"));
        direccionPaciente.setText(getIntent().getExtras().getString("direccion"));
        if (getIntent().getExtras().getString("sexo").charAt(0) == 'M')
            rbmm.setChecked(true);
        if (getIntent().getExtras().getString("sexo").charAt(0) == 'F')
            rbmf.setChecked(true);
        for (int i = 0; i < comunaPaciente.getCount(); i++) {
            if (getIntent().getExtras().getString("comuna").equals(comunaPaciente.getItemAtPosition(i).toString())) {
                comunaPaciente.setSelection(i);
            }
        }
        numeroTelefono.setText(getIntent().getExtras().get("numeroTelefono").toString());
        numeroCelular.setText(getIntent().getExtras().get("numeroCelular").toString());
        if (getIntent().getExtras().getString("alergia").charAt(0) == 'S')
            rbmsi.setChecked(true);
        if (getIntent().getExtras().getString("alergia").charAt(0) == 'N')
            rbmno.setChecked(true);
    }

    public void cargarComunas() {
        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        LinkedList<String> listComunas = new LinkedList<>();
        Cursor c = db.SpinnerComunas();
        while (c.moveToNext()) {
            listComunas.add(c.getString(c.getColumnIndex("nombre")));
        }
        c.close();
        ArrayAdapter<String> aa1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listComunas);
        Spinner s = (Spinner) findViewById(R.id.SpinnerComunas);
        s.setAdapter(aa1);
    }

    public void modificarPaciente(View view) {
        try {
            EditText nombre = (EditText) findViewById(R.id.txtNombre);
            EditText apellido = (EditText) findViewById(R.id.txtApellido);
            EditText edad = (EditText) findViewById(R.id.txtEdad);
            EditText fechaNacimiento = (EditText) findViewById(R.id.txtFecha);
            RadioButton rbm = (RadioButton) findViewById(R.id.Masculino);
            RadioButton rbf = (RadioButton) findViewById(R.id.Femenino);
            String sexo = null;
            if (rbm.isChecked())
                sexo = "M";
            if (rbf.isChecked())
                sexo = "F";
            EditText direccion = (EditText) findViewById(R.id.txtDir);
            Spinner comuna = (Spinner) findViewById(R.id.SpinnerComunas);
            EditText numeroTelefono = (EditText) findViewById(R.id.txtTelefono);
            EditText numeroCelular = (EditText) findViewById(R.id.txtCelular);
            RadioButton rbmsi = (RadioButton) findViewById(R.id.Si);
            RadioButton rbmno = (RadioButton) findViewById(R.id.No);
            if (nombre.getText().toString().isEmpty() == true || apellido.getText().toString().isEmpty() == true ||
                    edad.getText().toString().isEmpty() == true || fechaNacimiento.getText().toString().isEmpty() == true ||
                    direccion.getText().toString().isEmpty() == true || numeroTelefono.getText().toString().isEmpty() == true ||
                    numeroCelular.getText().toString().isEmpty() == true) {
                AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
                alert1.setMessage("Estos campos no pueden quedar vacíos: Nombre Apellido, Edad,\nFecha de Nacimiento, Direccion,\nNúmero de Teléfono, " +
                        "Número de Celular.").setTitle("ADVERTENCIA!");
                AlertDialog dialog = alert1.create();
                dialog.show();
            } else {
                int id = Integer.parseInt(getIntent().getExtras().getString("Id"));
                String nombrePaciente = nombre.getText().toString();
                String apellidoPaciente = apellido.getText().toString();
                short edadPaciente = Short.parseShort(edad.getText().toString());
                String fechaNacimientoPaciente = fechaNacimiento.getText().toString();
                char sexoPaciente = sexo.toString().charAt(0);
                String direccionPaciente = direccion.getText().toString();
                String comunaPaciente = comuna.getSelectedItem().toString();
                int numeroTelefonoPaciente = Integer.parseInt(numeroTelefono.getText().toString());
                int numeroCelularPaciente = Integer.parseInt(numeroCelular.getText().toString());
                char alergiaPaciente = ' ';
                if (rbmsi.isChecked())
                    alergiaPaciente = 'S';
                if (rbmno.isChecked())
                    alergiaPaciente = 'N';
                SQLiteHelper db = new SQLiteHelper(this);
                String mensaje = db.modificarPaciente(nombrePaciente, apellidoPaciente,
                        edadPaciente, fechaNacimientoPaciente, sexoPaciente, direccionPaciente, comunaPaciente,
                        numeroTelefonoPaciente, numeroCelularPaciente, alergiaPaciente, id);
                if (mensaje == null) {
                    Intent i = new Intent(this, ListarPacientes.class);
                    startActivity(i);
                    this.finish();
                } else {
                    AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
                    alert1.setMessage("Error: " + mensaje).setTitle("Aviso");
                    AlertDialog dialog = alert1.create();
                    dialog.show();
                }
            }
        } catch (Exception e) {
            AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
            alert1.setMessage("Error: " + e.toString()).setTitle("Aviso");
            AlertDialog dialog = alert1.create();
            dialog.show();
        }
    }

    public void eliminarPaciente(View view) {
        int id = Integer.parseInt(getIntent().getExtras().getString("Id"));
        SQLiteHelper db = new SQLiteHelper(this);
        String mensaje = db.eliminarPaciente(id);
        if(mensaje == null) {
            Intent i = new Intent(this,ListarPacientes.class);
            startActivity(i);
            this.finish();
        }
        else {
            AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
            alert1.setMessage("Error: "+mensaje).setTitle("Aviso");
            AlertDialog dialog = alert1.create();
            dialog.show();
        }
    }
}

