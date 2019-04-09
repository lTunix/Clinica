package com.blogspot.estamoscercadelfututro.clinica;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SplashScreen extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SQLiteHelper db = new SQLiteHelper(this);
        db.crearComunas();


        ImageView IV = (ImageView) findViewById(R.id.imageView);

        IV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                SplashScreen.super.finish();
            }
        });


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run()
            {
                if(SplashScreen.super.isFinishing() == false)
                {
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    SplashScreen.super.finish();
                }
            }
        },3000);
    }

    public void changeScreen(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();
    }
}
