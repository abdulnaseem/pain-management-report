package com.example.a3dmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new OpenGLView(this);
        setContentView(glSurfaceView);

    }
    @Override
    protected void onResume(){
        super.onResume();
        glSurfaceView.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        glSurfaceView.onPause();
    }

}