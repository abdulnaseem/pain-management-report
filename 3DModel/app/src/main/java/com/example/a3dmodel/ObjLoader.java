package com.example.a3dmodel;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;

public final class ObjLoader {

    public final int numFaces;

    public final float[] normals;
    public final float[] textureCoordinates;
    public final float[] vertices;

    public FloatBuffer normals1;
    public FloatBuffer textureCoordinates1;
    public FloatBuffer vertices1;

    public final int COORDS_PER_VERTEX = 4;

    private final String vertexShaderCode1 =
                    "attribute vec4 vPosition;" +
                            "void main() {" +
                                "gl_Position = vPosition;" +
                            "}";
    private final String vertexShaderCode2 =
                    "in vec3 vPosition;" +
                    "uniform mat4 MVP;" +
                    "void main() {" +
                    "gl_Position = MVP * vec4(vertexPosition.xyz,1.0);" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private final int mProgram;
    private int positionHandle;
    private int normalHandle;
    private int textcoordHandle;

    private int colorHandle;

    float color[] = { 2.0f, 0.76953125f, 0.22265625f, 1.0f };

    private int vPosition;
    private int vTexturePosition;
    private int uMVPMatrix;

    static float triangleCoords[] = {   // in counterclockwise order:
            0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 3; // 4 bytes per vertex

    public ObjLoader(String file) throws FileNotFoundException, IOException {

        Vector<Float> _vertices = new Vector<>();
        Vector<Float> _normals = new Vector<>();
        Vector<Float> _textures = new Vector<>();
        Vector<String> _faces = new Vector<>();

        BufferedReader reader = null;
        try {
            //InputStreamReader in = new InputStreamReader(context.getAssets().open(file));
            reader = new BufferedReader(new BufferedReader(new FileReader(file)));
            //reader = new BufferedReader(in);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                switch (parts[0]) {
                    case "v":
                        /* vertices
                         * read the file and split coordinates via spacing
                         * if the case is 'v' then add the x, y and z coordinates to the _vertices
                         * similarly do this for the rest
                         */
                        _vertices.add(Float.valueOf(parts[1]));
                        _vertices.add(Float.valueOf(parts[2]));
                        _vertices.add(Float.valueOf(parts[3]));
                        break;
                    case "vt":
                        // textures
                        _textures.add(Float.valueOf(parts[1]));
                        _textures.add(Float.valueOf(parts[2]));
                        break;
                    case "vn":
                        // normals
                        _normals.add(Float.valueOf(parts[1]));
                        _normals.add(Float.valueOf(parts[2]));
                        _normals.add(Float.valueOf(parts[3]));
                        break;
                    case "f":
                        // faces: vertex/texture/normal
                        if (parts.length > 4) {
                            // triangle 1
                            _faces.add(parts[1]);
                            _faces.add(parts[2]);
                            _faces.add(parts[3]);
                            // triangle 2
                            _faces.add(parts[1]);
                            _faces.add(parts[3]);
                            _faces.add(parts[4]);
                        }
                        else {
                            _faces.add(parts[1]);
                            _faces.add(parts[2]);
                            _faces.add(parts[3]);
                        }
                        break;
                }
            }
        } catch (IOException e) {
            // cannot load or read file
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        numFaces = _faces.size();
        this.normals = new float[numFaces * 3];
        textureCoordinates = new float[numFaces * 2];
        vertices = new float[numFaces * 3];
        int positionIndex = 0;
        int normalIndex = 0;
        int textureIndex = 0;
        for (String face : _faces) {
            String[] parts = face.split("/");
            /* point the arrays towards the three distinct coordinates of faces which are split by '/'
             * that are split as vertices/textures/normals
             */
            int index = 3 * (Short.valueOf(parts[0]) - 1);
            vertices[positionIndex++] = _vertices.get(index++);
            vertices[positionIndex++] = _vertices.get(index++);
            vertices[positionIndex++] = _vertices.get(index);

            index = 2 * (Short.valueOf(parts[1]) - 1);
            textureCoordinates[normalIndex++] = _textures.get(index++);
            textureCoordinates[normalIndex++] = 1 - _textures.get(index);

            index = 3 * (Short.valueOf(parts[2]) - 1);
            this.normals[textureIndex++] = _normals.get(index++);
            this.normals[textureIndex++] = _normals.get(index++);
            this.normals[textureIndex++] = _normals.get(index);
        }

        vertices1 = ByteBuffer.allocateDirect(vertices.length * COORDS_PER_VERTEX)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertices1.put(vertices).position(0);

        normals1 = ByteBuffer.allocateDirect(normals.length * COORDS_PER_VERTEX)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        normals1.put(normals).position(0);

        textureCoordinates1 = ByteBuffer.allocateDirect(textureCoordinates.length * COORDS_PER_VERTEX)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        textureCoordinates1.put(textureCoordinates).position(0);


        //_____________________________________________________________________________________________________
        //_____________________________________________________________________________________________________

        int vertexShader = OpenGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode1);
        int fragmentShader = OpenGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        Log.d("Shader", "Added");
        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();
        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);
        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);
        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
        Log.d("Program", "Linked");


    }


    public void draw(){
        GLES20.glUseProgram(mProgram);
        Log.d("Program", "Using");

        //get a handle on each attribute
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        normalHandle = GLES20.glGetAttribLocation(mProgram, "aNormal");
        textcoordHandle = GLES20.glGetAttribLocation(mProgram, "aTexCoor");

        // Prepare the coordinate data
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false,
                vertexStride, vertices1);

        GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT, false,
                vertexStride, normals1);

        GLES20.glVertexAttribPointer(textcoordHandle, 3, GLES20.GL_FLOAT, false,
                vertexStride, textureCoordinates1);

        //enable the handles
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glEnableVertexAttribArray(normalHandle);
        GLES20.glEnableVertexAttribArray(textcoordHandle);

        // get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        //draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length);
        Log.d("Drawn?", "Yepp");

        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(normalHandle);
        GLES20.glDisableVertexAttribArray(textcoordHandle);

        // GLES20.glDisableVertexAttribArray(textureCoordHandle);



    }

    public FloatBuffer getVertices(){
        return vertices1;
    }
    public FloatBuffer getNormals(){
        return normals1;
    }
    public FloatBuffer getTextureCoordinates(){
        return textureCoordinates1;
    }

}


