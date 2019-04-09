package com.blogspot.estamoscercadelfututro.clinica;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

public class RegistrarPaciente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_paciente);
        cargarComunas();
    }

    public void aceptar() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void openCalendarRegister(View view) {
        DatePicker newFragment = DatePicker.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                String dia = "" + day, mes = "" + (month + 1);
                if (dia.length() == 1)
                    dia = "0" + day;
                if (mes.length() == 1)
                    mes = "0" + (month + 1);
                final String selectedDate = year + "-" + mes + "-" + dia;
                EditText fechaNacimiento = (EditText) findViewById(R.id.txtRFecha);
                fechaNacimiento.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
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
        Spinner s = (Spinner) findViewById(R.id.SpinnerRComunas);
        s.setAdapter(aa1);
    }

    public void registrarPaciente(View view) {

        try {
            EditText nombre = (EditText) findViewById(R.id.txtRNombre);
            EditText apellido = (EditText) findViewById(R.id.txtRApellido);
            EditText edad = (EditText) findViewById(R.id.txtREdad);
            EditText fechaNacimiento = (EditText) findViewById(R.id.txtRFecha);
            RadioButton rbm = (RadioButton) findViewById(R.id.RMasculino);
            RadioButton rbf = (RadioButton) findViewById(R.id.RFemenino);
            String sexo = null;
            if (rbm.isChecked())
                sexo = "M";
            if (rbf.isChecked())
                sexo = "F";
            EditText direccion = (EditText) findViewById(R.id.txtRDireccion);
            Spinner comuna = (Spinner) findViewById(R.id.SpinnerRComunas);
            EditText numeroTelefono = (EditText) findViewById(R.id.txtRTelefono);
            EditText numeroCelular = (EditText) findViewById(R.id.txtRCelular);
            RadioButton rbsi = (RadioButton) findViewById(R.id.RSi);
            RadioButton rbno = (RadioButton) findViewById(R.id.RNo);
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
                if (rbsi.isChecked())
                    alergiaPaciente = 'S';
                if (rbno.isChecked())
                    alergiaPaciente = 'N';
                SQLiteHelper db = new SQLiteHelper(this);
                String mensaje = db.registrarPaciente(nombrePaciente, apellidoPaciente,
                        edadPaciente, fechaNacimientoPaciente, sexoPaciente, direccionPaciente, comunaPaciente,
                        numeroTelefonoPaciente, numeroCelularPaciente, alergiaPaciente);
                if (mensaje == null) {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                    dialogo1.setTitle("AVISO!");
                    dialogo1.setMessage("Paciente Registrado!");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            aceptar();
                        }
                    });
                    dialogo1.show();
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
}
