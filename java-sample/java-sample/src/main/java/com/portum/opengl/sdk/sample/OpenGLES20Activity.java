/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.portum.opengl.sdk.sample;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OpenGLES20Activity extends Activity {
    private GLSurfaceView mGLView;
    TextView tv = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGLView = (CubeSurfaceView) findViewById(R.id.surfaceView);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        //mGLView = new CubeSurfaceView(this);
        //setContentView(mGLView);

        //        mGLView = new MyGLSurfaceView(this);
//        setContentView(mGLView);

        final EditText editX = (EditText) findViewById(R.id.editX);
        final EditText editY = (EditText) findViewById(R.id.editY);
        final EditText editZ = (EditText) findViewById(R.id.editZ);
        final SetValueChanged svChanged = (SetValueChanged) mGLView;
        Button button = (Button) findViewById(R.id.button);
        tv = (TextView)findViewById(R.id.textView3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float x = 0;
                float y = 0;
                float z = 0;
                String strx = editX.getText().toString();

                String stry =  editY.getText().toString();

                String strz =  editZ.getText().toString();
                if(!strx.isEmpty() && !stry.isEmpty() && !strz.isEmpty()){
                    x = Float.parseFloat(strx);
                    y = Float.parseFloat(stry);
                    z = Float.parseFloat(strz);
                }
                Log.d("DEBUG", "strx("+strx+"), stry("+stry+"), strz("+strz+")");

                svChanged.setValue(x, y, z);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }
}