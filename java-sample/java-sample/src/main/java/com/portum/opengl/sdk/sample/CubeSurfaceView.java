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
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.portum.opengl.sdk.PortumGLES20;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
public class CubeSurfaceView extends GLSurfaceView implements SetValueChanged{

    private final CubeRenderer mRenderer;

    public CubeSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Lets try to keep EGL Context as long as we can
        setPreserveEGLContextOnPause(true);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new CubeRenderer(getContext());
        setRenderer(mRenderer);

        // setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
    public CubeSurfaceView(Context context,  AttributeSet attrs){
        super(context, attrs);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new CubeRenderer(getContext());
        setRenderer(mRenderer);

        // setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private float mPreviousX;
    private float mPreviousY;

//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        final float TOUCH_SCALE_FACTOR = 180.0f / 320;
//        // MotionEvent reports input details from the touch screen
//        // and other input controls. In this case, you are only
//        // interested in events where the touch position changed.
//
//        float x = e.getX();
//        float y = e.getY();
//
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//
//                float dx = mPreviousX - x;
//                float dy = mPreviousY - y;
//
//                mRenderer.setAngleX(
//                        mRenderer.getAngleX()
//                                + ((dy) * TOUCH_SCALE_FACTOR));
//
//                mRenderer.setAngleY(
//                        mRenderer.getAngleY()
//                                + ((dx) * TOUCH_SCALE_FACTOR));
//
//                if (BuildConfig.DEBUG) {
//                    requestRender();
//                }
//
//                break;
//        }
//
//        mPreviousX = x;
//        mPreviousY = y;
//        return true;
//    }

    @Override
    public void setValue(float x, float y, float z) {
        Log.d("DEBUG", "x = " + x + ", y = " + y + ", z = " + z);
        mRenderer.setAngle(x, y, z);
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                PortumGLES20.glPause();
            }
        });

        super.surfaceDestroyed(holder);
    }

}
