package com.example.a3dmodel;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer extends AppCompatActivity implements GLSurfaceView.Renderer{

    private Triangle mTriangle;
    private ObjLoader objLoader;
    private Context context;

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    private float[] rotationMatrix = new float[16];
    public int shaderProgramId;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //set the background frame color
        GLES20.glClearColor(1.0f, 2.0f, 1.0f, 0.0f);
        //mTriangle = new Triangle();
        try {
            objLoader = new ObjLoader("FinalBaseMesh.obj");
            Log.d("Passed", "Object has been loaded.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        //Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        //redraw backround color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //mTriangle.draw();
        objLoader.draw();//set the object to draw


        /*
        gl.glVertexPointer(3, GLES20.GL_FIXED, 0, objLoader.getVertices());

        Log.d("vertex", "loaded");
        gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, objLoader.getTextureCoordinates());
        Log.d("texture", "loaded");
        gl.glNormalPointer(1, GL10.GL_FIXED, objLoader.getNormals());
        Log.d("normals", "loaded");
        gl.glColor4f(0.0f, 1.0f, 1.0f, 1.0f);
        gl.glNormal3f(0, 0, 1);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, objLoader.getVertices().limit());
        Log.d("Drawn?", "Yep");
        */

    }

    public static int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}
