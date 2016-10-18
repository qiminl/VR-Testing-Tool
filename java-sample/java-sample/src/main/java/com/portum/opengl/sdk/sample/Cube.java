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
import android.util.Log;

import com.portum.opengl.sdk.PortumGLES20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static android.opengl.GLES20.GL_COMPILE_STATUS;

/**
 * Renders a 3D Cube using OpenGL ES 2.0.
 *
 * For more information on how to use OpenGL ES 2.0 on Android, see the
 * <a href="//developer.android.com/training/graphics/opengl/index.html">
 * Displaying Graphics with OpenGL ES</a> developer guide.
 */
public class Cube {
    private static final String[] AD_SPACE_IDS = { "119", "119", "129", "129", "60", "60" };
    //private static final String[] AD_SPACE_IDS = { "106", "107", "57", "106", "107", "57" };

    /** Cube vertices */
    private static final float VERTICES[][] = {
            {     // front
                    -1.0f, -1.0f, 1.0f,
                    1.0f, -1.0f, 1.0f,
                    1.0f,  1.0f, 1.0f,
                    -1.0f,  1.0f, 1.0f,
            }, {  // top
            -1.0f, 1.0f,  1.0f,
            1.0f, 1.0f,  1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
    }, {  // back
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, +1.0f, -1.0f,
            1.0f, +1.0f, -1.0f,
    }, {  // bottom
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f,  1.0f,
            -1.0f, -1.0f,  1.0f,
    }, {  // left
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f,  1.0f,
            -1.0f,  1.0f,  1.0f,
            -1.0f,  1.0f, -1.0f,
    }, {  // right
            1.0f, -1.0f,  1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f,  1.0f, -1.0f,
            1.0f,  1.0f,  1.0f,
    }
    };

    private static final float TEXCOORD[] = {
            0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f
    };

    private static final long[] ADS = new long[6];

    private static final int COORDS_PER_VERTEX = 3;

    private final Context mContext;
    private final int mProgram;
    private final int mPositionHandle;
    private final int mTextureCoordHandle;
    private final int mMVPMatrixHandle;
    private final int mTextureHandle;

    static {
        for (int i = 0; i < VERTICES.length; i++) {
            ADS[i] = PortumGLES20.glGenAd(AD_SPACE_IDS[i % AD_SPACE_IDS.length]);

            if (i % 2 == 1) {
                PortumGLES20.glBindAdVerticesWithTexcoords(ADS[i], VERTICES[i], COORDS_PER_VERTEX, TEXCOORD);
            } else {
                PortumGLES20.glBindAdVertices(ADS[i], VERTICES[i], COORDS_PER_VERTEX);
                PortumGLES20.glSetAdCullface(ADS[i], GLES20.GL_BACK);
            }
        }
    }

    public Cube(Context context) {
        mContext = context;

        mProgram = GLES20.glCreateProgram();
        checkGlError("glCreateProgram");
        GLES20.glAttachShader(mProgram, loadShader(GLES20.GL_VERTEX_SHADER, "cube.v.glsl"));
        checkGlError("glAttachShader1");
        GLES20.glAttachShader(
                mProgram, loadShader(GLES20.GL_FRAGMENT_SHADER, "cube.f.glsl"));
        checkGlError("glAttachShader2");
        GLES20.glLinkProgram(mProgram);
        checkGlError("glLinkProgram=" + mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "coord3d");
        checkGlError("glGetAttribLocation1=" + mPositionHandle);
        mTextureCoordHandle = GLES20.glGetAttribLocation(mProgram, "texcoord");
        checkGlError("glGetAttribLocation2=" + mTextureCoordHandle);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "mvp");
        checkGlError("glGetAttribLocation3=" + mMVPMatrixHandle);
        mTextureHandle = GLES20.glGetUniformLocation(mProgram, "mytexture");
        checkGlError("glGetAttribLocation4=" + mTextureHandle);
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix The Model View Project matrix in which to draw this shape
     */
    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment.
        GLES20.glUseProgram(mProgram);

        // Apply the projection and view transformation.
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        PortumGLES20.glUpdateMVP(mvpMatrix);
        PortumGLES20.glDrawAds(mPositionHandle, mTextureCoordHandle, mTextureHandle);

        checkGlError("glDrawAds");
    }

    /** Loads the provided shader in the program. */
    private int loadShader(int type, String shaderFile) {
        String typeName = type == GLES20.GL_VERTEX_SHADER ? "Vertex" : "Fragment";

        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, readFromAssets(shaderFile));
        GLES20.glCompileShader(shader);

        java.nio.IntBuffer intBuf = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        GLES20.glGetShaderiv(shader, GL_COMPILE_STATUS, intBuf);

        if (intBuf.get(0) == 0) {
            GLES20.glGetShaderiv(shader, GLES20.GL_INFO_LOG_LENGTH, intBuf);

            if (intBuf.get(0) > 0) {
                Log.i("GLSL20", typeName + " shader: "+ GLES20.glGetShaderInfoLog(shader));
            }

            GLES20.glDeleteShader(shader);

            throw new RuntimeException("Cannot compile shader");
        }

        Log.d("GLSL20", typeName + " compiled success");

        return shader;
    }

    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            throw new RuntimeException(op + ": glError " + error);
        }
    }

    private String readFromAssets(String filename) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open(filename)));

            // do reading, usually loop until end of file reading
            StringBuilder sb = new StringBuilder();
            String mLine = reader.readLine();
            while (mLine != null) {
                sb.append(mLine); // process line
                mLine = reader.readLine();
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}