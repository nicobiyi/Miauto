package com.example.nicolas.miauto.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nicolas.miauto.R;

public class GarageActivity extends Activity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        btn = (Button) findViewById(R.id.btnSelectAuto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GarageActivity.this, MenuActivity.class);
                //intent.putExtra("nombreId", variable); Para pasar datos entre activities
                startActivity(intent);
            }
        });
    }
}
