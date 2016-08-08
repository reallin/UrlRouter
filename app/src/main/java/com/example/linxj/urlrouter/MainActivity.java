package com.example.linxj.urlrouter;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.Test;
import com.example.linxj.internal.Scheme;

@Test
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void jump(View view){
        new Scheme.Builder(MainActivity.this, Uri.parse("linxj://jumpActivity")).dispatch();
    }

    public void jumpParam(View view){
        new Scheme.Builder(MainActivity.this, Uri.parse("linxj://jumpActivity?name=lxj")).dispatch();
    }

}
