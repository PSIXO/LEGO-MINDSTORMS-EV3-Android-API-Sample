package com.charlie.ev3.sample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.charlie.ev3.BluetoothCommunication;
import com.charlie.ev3.Brick;
import com.charlie.ev3.BrickChangedListener;
import com.charlie.ev3.InputPort;
import com.charlie.ev3.OutputPort;


public class MainActivity extends ActionBarActivity implements BrickChangedListener{
    private Brick b = new Brick(new BluetoothCommunication());
    private TextView textView;
    private EditText speed,time;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);
        speed = (EditText)findViewById(R.id.speed);
        time = (EditText)findViewById(R.id.milliseconds);
        spinner = (Spinner)findViewById(R.id.port);
        spinner.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.ports)));
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void connect(View view){
        try{
            b.connect();
            b.directCommand.playTone(100,(short)500,(short)1000);
        }catch(Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void start(View view){
        try{
            OutputPort outputPort = OutputPort.ALL;
            for(OutputPort o:OutputPort.values())
                if(o.ordinal() == spinner.getSelectedItemPosition()) outputPort = o;
            b.directCommand.timeMotorSpeed(Integer.parseInt(speed.getText().toString()),Integer.parseInt(time.getText().toString()),true,outputPort);
        }catch(Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void brickChanged() {
        textView.setText(
                "端口1："+b.ports.get(InputPort.One)+
                "端口2："+b.ports.get(InputPort.Two)+
                "端口3："+b.ports.get(InputPort.Three)+
                "端口4："+b.ports.get(InputPort.Four)+
                "端口A："+b.ports.get(InputPort.A)+
                "端口B："+b.ports.get(InputPort.B)+
                "端口C："+b.ports.get(InputPort.C)+
                "端口D："+b.ports.get(InputPort.D));
    }
}
