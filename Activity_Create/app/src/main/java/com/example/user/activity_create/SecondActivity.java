package com.example.user.activity_create;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //get data by intent
        String data = getIntent().getStringExtra("data");
        Toast.makeText(SecondActivity.this, data,
                Toast.LENGTH_SHORT).show();
        Button button_3 = (Button) findViewById(R.id.Button_3);
        button_3.setOnClickListener(new View.OnClickListener(){
            //隐式Intent， 调用浏览器
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });

        Button button_4 = (Button)findViewById(R.id.Button_4);
        button_4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.putExtra("data2first", "This data is from the 2nd activity");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data2first", "This data is from the 2nd activity, you'll see" +
                "this message when you type back button");
        setResult(RESULT_OK, intent);
        finish();
    }
}
