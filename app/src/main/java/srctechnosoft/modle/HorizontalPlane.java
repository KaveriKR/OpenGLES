package srctechnosoft.modle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by user on 11/2/2017.
 */

public class HorizontalPlane {
    Bitmap bitmap;
    Context context;
    FloatBuffer texBuffer;
    private int[] textureIDs = new int[1];

    private FloatBuffer colorBuffer;   // Buffer for color-array
    private float[] colors = { // Colors for the vertices
            1.1f, 1.1f, 1.1f, 1.0f, // Red
            1.1f, 1.1f, 1.1f, 1.0f, // Green
            1.1f, 1.1f, 1.1f, 1.0f,  // Blue (
            1.1f, 1.1f, 1.1f, 1.0f
    };

    public HorizontalPlane(Context context){
        this.context=context;
        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind


        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder()); // Use native byte order
        colorBuffer = cbb.asFloatBuffer();  // Convert byte buffer to float
        colorBuffer.put(colors);            // Copy data into buffer
        colorBuffer.position(0);
      //  bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.back);// Allocate texture buffer. An float has 4 bytes. Repeat for 6 faces.
        float[] texCoords = {
                0.0f, 1.0f,  // A. left-bottom
                1.0f, 1.0f,  // B. right-bottom
                0.0f, 0.0f,  // C. left-top
                1.0f, 0.0f   // D. right-top
        };
        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4 );
        tbb.order(ByteOrder.nativeOrder());
        texBuffer = tbb.asFloatBuffer();

        texBuffer.put(texCoords);

        texBuffer.position(0);   // Rewind
//         Setup texture-coords-array buffer, in float. An float has 4 bytes

    }

    private FloatBuffer vertexBuffer;  // Buffer for vertex-array

    private float[] vertices = {  // Vertices for the square
            -1.6f, 0.0f,  1.1f,  // 0. left-bottom
            1.6f, 0.0f,  1.1f,  // 1. right-bottom
            -1.6f,  0.0f,  -1.6f,  // 2. left-top
            1.6f,  0.0f,  -1.6f   // 3. right-top
    };



    // Render the shape
    public void draw(GL10 gl) {
        // Enable vertex-array and define its buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertexBuffer);

        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);

        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);          // Enable color-array
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);  // Define color-array buffer
        // Draw the primitives from the vertex-array directly
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,vertices.length/3);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    // Load images into 6 GL textures
    public void loadTexture(GL10 gl) {
        gl.glGenTextures(1, textureIDs, 0); // Generate texture-ID array for 6 IDs

        // Generate OpenGL texture images

        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);
        // Set up texture filters
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        // Build Texture from loaded bitmap for the currently-bind texture ID
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();

    }
}
