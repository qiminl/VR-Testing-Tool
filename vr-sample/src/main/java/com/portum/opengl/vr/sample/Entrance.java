package com.portum.opengl.vr.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



/**
 * Created by edward on 2016-09-22.
 */

public class Entrance extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrance_ui);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button button = (Button) findViewById(R.id.go_vr);

        final Intent i = new Intent(this, TreasureHuntActivity.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText AD_ID = (EditText) findViewById(R.id.editText);
                EditText AD_ID2 = (EditText) findViewById(R.id.editText2);
                EditText AD_ID3 = (EditText) findViewById(R.id.editText3);
                EditText AD_ID4 = (EditText) findViewById(R.id.editText4);
                EditText AD_ID5 = (EditText) findViewById(R.id.editText5);
                EditText AD_ID6 = (EditText) findViewById(R.id.editText6);
                String[] AD_Cube= {AD_ID.getText().toString(), AD_ID2.getText().toString(),
                        AD_ID3.getText().toString(),AD_ID4.getText().toString(),
                        AD_ID5.getText().toString(),AD_ID6.getText().toString()};

                String getrec = AD_ID.getText().toString();
                //Create the bundle
                Bundle bundle = new Bundle();
                bundle.putString("ADs", getrec);
                bundle.putStringArray("cube", AD_Cube);
                Log.d("AD", "jumpping");
                i.putExtras(bundle);
                startActivity(i);
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
