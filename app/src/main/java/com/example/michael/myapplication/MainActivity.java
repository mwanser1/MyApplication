package com.example.michael.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_ENABLE_BT = 1;
    private void setToast(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideStuff();
        final BluetoothAdapter mBT = BluetoothAdapter.getDefaultAdapter();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button btnClick = (Button) findViewById(R.id.Bluetooth_Button) ;
        Button btn_Off = (Button) findViewById(R.id.bt_Off) ;
        Button btn_tell = (Button) findViewById(R.id.tell_Me);

        Button btn_discovery = (Button) findViewById(R.id.discovery);

        btn_Off.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mBT.disable();
                mBT.cancelDiscovery();
                Toast.makeText(getApplicationContext(), "Bluetooth Disabled and discovery cancelled", Toast.LENGTH_SHORT).show();
            }});

        btn_discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mBT.isEnabled()){
                    Toast.makeText(getApplicationContext(), "Bluetooth not enabled", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent discoverableIntent = new
                        Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);


                Toast.makeText(getApplicationContext(), "enable discovery", Toast.LENGTH_SHORT).show();
            }
        });



        btn_tell.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int btPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH);
                if (btPermission == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(getApplicationContext(), "Bluetooth Granted BT: "+mBT.getAddress(), Toast.LENGTH_SHORT).show();
                if (btPermission == PackageManager.PERMISSION_DENIED)
                    Toast.makeText(getApplicationContext(), "Bluetooth Denied BT: "+mBT.getAddress(), Toast.LENGTH_SHORT).show();
                if (btPermission != PackageManager.PERMISSION_DENIED && btPermission != PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(getApplicationContext(), "Something is wrong BT: "+mBT.getAddress(), Toast.LENGTH_SHORT).show();
            }});

        btnClick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                int btPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH);
                if (btPermission != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Bluetooth Not Granted", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mBT == null) {
                    Log.d("bt no support", "BT NOT SUPPORTED");
                    return;
                }
                if (!mBT.isEnabled() || btPermission!=PackageManager.PERMISSION_GRANTED) {
                    Intent enableBT = new Intent( BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBT, REQUEST_ENABLE_BT);
                    mBT.enable();
                    Toast.makeText(getApplicationContext(), "Bluetooth Enabled BT: "+mBT.getAddress(), Toast.LENGTH_SHORT).show();
                }

            }});
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
    private void hideStuff(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        );

    }
}
