package com.example.linxj.urlrouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by linxj on 16/8/6.
 */

public class UrlJumpActivity  extends AppCompatActivity {
    TextView txv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        txv = (TextView)super.findViewById(R.id.txv);
        if(getIntent().getStringExtra("name")!=null){
            txv.setText("params is: "+getIntent().getStringExtra("name"));
        }
    }


}
