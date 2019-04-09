package com.blogspot.estamoscercadelfututro.clinica;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.LinkedList;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static SQLiteDatabase BDP;
    public SQLiteHelper(Context c) {
        super(c, "DBCLINICA", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PACIENTE(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre VARCHAR(100),apellido VARCHAR(100),edad SMALLINT,fechanacimiento DATE,"+
                "sexo CHAR(1),direccion VARCHAR(100),comuna VARCHAR(100),numerotelefono INT, numerocelular INT,alergico CHAR(1))");
        db.execSQL("CREATE TABLE COMUNA(nombre VARCHAR(100))");
    }

    public void onUpgrade(SQLiteDatabase db, int a, int b)
    {

    }


    public String registrarPaciente(String nombre,String apellido,short edad,String fechaNacimiento,char sexo,String direccion,String comuna, int numeroTelefono,int numeroCelular,char alergico) {
        try
        {
            BDP = getWritableDatabase();
            String sql = String.format("INSERT INTO PACIENTE(nombre,apellido,edad,fechanacimiento,sexo," +
                            "direccion,comuna,numerotelefono,numerocelular,alergico) VALUES('%s','%s',%d," +
                            "'%s','%s','%s','%s',%d,%d,'%s')", nombre, apellido, edad,
                    fechaNacimiento, sexo, direccion,comuna, numeroTelefono, numeroCelular, alergico);
            BDP.execSQL(sql);
            return null;
        }
        catch(SQLiteException e) {
            return e.toString();
        }
    }

    public String crearComunas() {
        try {
            Cursor cursor = SpinnerComunas();
            if (cursor.moveToNext() == false) {
                BDP = getWritableDatabase();
                LinkedList<String> listarComunas = new LinkedList<>();
                listarComunas.add("Quilicura");
                listarComunas.add("Curacaví");
                listarComunas.add("Estación Central");
                listarComunas.add("Santiago");
                listarComunas.add("Maipú");
                listarComunas.add("Cerro Navia");
                listarComunas.add("San Bernardo");
                listarComunas.add("Las Condes");
                listarComunas.add("Lo Barnechea");
                listarComunas.add("Lo Prado");
                for (int i = 0; i < listarComunas.size(); i++) {
                    String sql = String.format("INSERT INTO COMUNA(nombre) VALUES('%s')", listarComunas.get(i));
                    BDP.execSQL(sql);
                }
                return null;
            }
            return null;
        }
        catch(SQLiteException e) {
            return e.toString();
        }
    }

    public Cursor listaPacientes() {
        BDP = getReadableDatabase();
        return BDP.rawQuery("SELECT * FROM PACIENTE", null);
    }

    public Cursor buscarPaciente(int id) {
        BDP = getReadableDatabase();
        return BDP.rawQuery("SELECT * FROM PACIENTE WHERE ID = "+id,null);
    }

    public Cursor SpinnerComunas() {
        BDP = getReadableDatabase();
        return BDP.rawQuery("SELECT * FROM COMUNA", null);
    }

    public String modificarPaciente(String nombre,String apellido,short edad,String fechaNacimiento,char sexo,String direccion,String comuna, int numeroTelefono,int numeroCelular,char alergico,int id) {
        try {
            BDP = getWritableDatabase();
            String sql = String.format("UPDATE PACIENTE SET nombre = '"+nombre+"', apellido = '"+apellido+"', edad = "+edad+", "+
                            "fechanacimiento = '"+fechaNacimiento+"', sexo = '"+sexo+"', direccion = '"+direccion+"', comuna = '"+comuna+"', numerotelefono = "+numeroTelefono+", "+
                            "numerocelular = "+numeroCelular+", alergico = '"+alergico+"' WHERE ID = "+id+"");
            BDP.execSQL(sql);
            return null;
        }
        catch(SQLiteException e) {
            return e.toString();
        }
    }

    public String eliminarPaciente(int id) {
        try {
            BDP = getWritableDatabase();
            String sql = String.format("DELETE FROM PACIENTE WHERE id = "+id);
            BDP.execSQL(sql);
            return null;
        }
        catch(SQLiteException e) {
            return e.toString();
        }
    }

}

