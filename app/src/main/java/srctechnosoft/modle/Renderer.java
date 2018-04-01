package srctechnosoft.modle;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by user on 11/2/2017.
 */

public class Renderer implements GLSurfaceView.Renderer {

    private float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f};
    private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
    private float[] lightPosition = {0.0f, 0.0f, -6.0f, 1.0f};
    float angle; float move=-0.4f;
    Context context;

    private HorizontalPlane sqaure2;

    private VerticalPlane text;



    public  Renderer(Context context){
         this.context= context;

         sqaure2= new HorizontalPlane(context);
         text= new VerticalPlane(context);


    }



    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        gl10.glClearColor(0.0f,0.0f,0.0f,0.0f);
        gl10.glClearDepthf(1.0f);
        gl10.glEnable(GL10.GL_DEPTH_TEST);
        gl10.glDepthFunc(GL10.GL_LEQUAL);
        gl10.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_NICEST);
        gl10.glShadeModel(GL10.GL_SMOOTH);
        gl10.glDisable(GL10.GL_DITHER);

          // Load images into textures
//        sqaure2.loadTexture(gl10);
        text.loadTexture(gl10);

        gl10.glEnable(GL10.GL_TEXTURE_2D);

        // Setup lighting GL_LIGHT1 with ambient and diffuse lights
        gl10.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient, 0);
        gl10.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, lightDiffuse, 0);
        gl10.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosition, 0);
        gl10.glEnable(GL10.GL_LIGHT1);   // Enable Light 1
        gl10.glEnable(GL10.GL_LIGHT0);   // Enable the default Light 0

        gl10.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);           // Full brightness, 50% alpha
        gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE); // Select blending function
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        if (height == 0) height = 1;   // To prevent divide by zero
        float aspect = (float)width / height;

        // Set the viewport (display area) to cover the entire window
        gl10.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl10.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
        gl10.glLoadIdentity();                 // Reset projection matrix
        // Use perspective projection
        GLU.gluPerspective(gl10, 45, aspect, 0.1f, 100.f);

        gl10.glMatrixMode(GL10.GL_MODELVIEW);  // Select model-view matrix
        gl10.glLoadIdentity();

    }



    @Override
    public void onDrawFrame(GL10 gl10) {

        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl10.glEnable(GL10.GL_LIGHTING);
        gl10.glEnable(GL10.GL_BLEND);
        gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);// Turn blending on

        //draw ground plane
        gl10.glLoadIdentity();
        gl10.glTranslatef(0.0f, -0.9f, -10.0f);
        gl10.glRotatef(20f,1.0f,0.0f,0.0f);

        gl10.glRotatef((float) Math.sin(angle)*10,0.0f,1.0f,0.0f);
        gl10.glTranslatef(0.0f,(float) Math.max(Math.sin(move)/2,0.0f),0.0f);
        sqaure2.draw(gl10);

        //draw android icon plane

        gl10.glLoadIdentity();
        gl10.glTranslatef(0.0f,0f,-10.0f);

        gl10.glScalef(0.7f,1f,0.5f);

        gl10.glRotatef((float) Math.sin(angle)*10,0.0f,1.0f,0.0f);
        gl10.glTranslatef(0.0f,(float) Math.max(Math.sin(move)/2,0.0f),0.0f);


        text.draw(gl10);

        angle+=0.03f;
        move+=0.02f;


    }

}
