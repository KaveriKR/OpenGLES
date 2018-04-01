package srctechnosoft.modle;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    GLSurfaceView glView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        LinearLayout back= (LinearLayout) findViewById(R.id.back);

        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new Renderer(this));
        back.addView(view);
    }



}
