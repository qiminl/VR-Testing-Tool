/*
 * Copyright (C) 2014 The Android Open Source Project
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
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.portum.opengl.sdk.PortumGLES20;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Renders a 3D OpenGL Cube on a
 */
public class CubeRenderer implements GLSurfaceView.Renderer {

    private final Context mContext;
    private final float[] mMVPMatrix;
    private final float[] mProjectionMatrix;
    private final float[] mViewMatrix;
    private final float[] mFinalMVPMatrix;
    private final float[] mRotationMatrix;

    private Cube mCube;
    private float mCubeRotationX;
    private float mCubeRotationY;
    private float mCubeRotationZ;

    public CubeRenderer(Context context) {
        mContext = context;
        mMVPMatrix = new float[16];
        mProjectionMatrix = new float[16];
        mViewMatrix = new float[16];
        mFinalMVPMatrix = new float[16];
        mRotationMatrix = new float[16];

        // Set the fixed camera position (View matrix).
        Matrix.setLookAtM(mViewMatrix, 0, 0.0f, 0.0f, -7.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClearDepthf(1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);

        PortumGLES20.glResume();

        mCube = new Cube(mContext);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;

        GLES20.glViewport(0, 0, width, height);
        // This projection matrix is applied to object coordinates in the onDrawFrame() method.
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1.0f, 1.0f, 3.0f, 8.0f);
        // modelView = projection x view
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Apply the rotation.
        //Matrix.setRotateM(mRotationMatrix, 0, mCubeRotationX, 1.0f, 0.0f, 0.0f);
        Matrix.setRotateM(mRotationMatrix, 0, 0, 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(mRotationMatrix, 0, mCubeRotationX, 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(mRotationMatrix, 0, mCubeRotationY, 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(mRotationMatrix, 0, mCubeRotationZ, 0.0f, 0.0f, 1.0f);
        // Combine the rotation matrix with the projection and camera view
        Matrix.multiplyMM(mFinalMVPMatrix, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw cube.
        mCube.draw(mFinalMVPMatrix);
    }

    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getAngleX() {
        return mCubeRotationX;
    }
    public float getAngleY() {
        return mCubeRotationY;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngleX(float angle) {
        mCubeRotationX = angle;
    }
    public void setAngleY(float angle) {
        mCubeRotationY = angle;
    }


    //value interface

    public void setAngle(float x, float y, float z){
        mCubeRotationX = x;
        mCubeRotationY = y;
        mCubeRotationZ = z;
    }
}