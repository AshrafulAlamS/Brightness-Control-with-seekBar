package com.example.seekbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telecom.CallScreeningService;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askPermission(this);    //method for check & get user permission

        seekBar=findViewById(R.id.seekBar);
        textView=findViewById(R.id.textView);

        int currentBrightness = Settings.System.getInt(getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,0);   //get device current brightness

        seekBar.setProgress(currentBrightness);
        textView.setText("Brightness : "+currentBrightness+"/"+seekBar.getMax());

        //add listener of seekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                android.provider.Settings.System.putInt(getContentResolver(),   //also work without "android.provider."
                        Settings.System.SCREEN_BRIGHTNESS, progress);   //the "progress" parameter value are applied to "String name" parameter
                textView.setText("Brightness : "+progress+"/"+seekBar.getMax());


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                Toast.makeText(MainActivity.this, "onStartTrackingTouch", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Toast.makeText(MainActivity.this, "onStopTrackingTouch", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void askPermission(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(Settings.System.canWrite(context)){  //canWrite method require min api level 23
                //you have permission to write settings

            }else {
                Intent i = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                context.startActivity(i);   //goto permission page to get user permission
            }
        }
    }
}