package com.portum.opengl.sdk.sample;

import com.portum.opengl.sdk.PortumGLES20;

/**
 * Created by camobap on 6/10/16.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PortumGLES20.glInit(this);
    }
}
