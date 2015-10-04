package kowol.andrzej.sensory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends ActionBarActivity implements SensorEventListener{

    SensorManager manager;
    SensorManager manager2;

    TextView t2;
    Matrix matrix;
    ImageView imageView;
    ImageView tarcza;
    Bitmap bm;
    SeekBar sb;
    SeekBar sb2;
    Float kat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matrix = new Matrix();

        t2 = (TextView)findViewById(R.id.textView2);
        sb = (SeekBar)findViewById(R.id.seekBar);
        sb2 = (SeekBar)findViewById(R.id.seekBar2);
        sb.setMax(360);
        sb2.setMax(360);
        imageView = (ImageView)findViewById(R.id.imageView2);
        tarcza = (ImageView)findViewById(R.id.imageView);
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.compasshi);

        manager=(SensorManager)getSystemService(SENSOR_SERVICE);
       	manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_ORIENTATION), 0, null);

    }

    private Bitmap rotate(Bitmap bm, float v) {

        matrix.reset();
        matrix.postRotate(v);
        return Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.values[0]==0.0f && event.values[1]==0.0f && event.values[2]==0.0f) {
            t2.setText("Jesteś zwycięzcą");
        }else{
            t2.setText("Kompas: "+event.values[0] + "\n" + "Poziomica pion  : "+event.values[1] + "\n" + "Poziomica poziom: "+event.values[2]);
        }

        tarcza.setRotation(0);
        tarcza.setRotation(-event.values[0]+0.75f);
        sb.setProgress( (int)event.values[1] + 180 );
        sb2.setProgress( (int)event.values[2] + 180 );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
