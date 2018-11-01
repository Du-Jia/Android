package com.example.user.activity_create;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class First_Activity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstatnceState){
        super.onCreate(savedInstatnceState);
        setContentView(R.layout.first_layout);

        //Toast
        Button button_1 = (Button) findViewById(R.id.button_1);
        button_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(First_Activity.this, "You clicked Button_1",
                        Toast.LENGTH_SHORT).show();
                //Open SecondActivity explicitly
//                Intent intent = new Intent(First_Activity.this, SecondActivity.class);
//                startActivity(intent);
                //Open SecondActivity implicitly
                Intent intent = new Intent("com.example.activitytest.ACTION_START");
                intent.addCategory("com.example.activitytest.MY_CATEGORY");
                //tranform data by intent
                intent.putExtra("data", "transfrom data by intent");
                startActivityForResult(intent, 1);
            }
        });

        Button button_2 = (Button) findViewById(R.id.button_2);
        button_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(First_Activity.this, "You clicked Button_2",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "You clicked Add Button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "You clicked Remove Button", Toast.LENGTH_SHORT).show();
                break;
            default:


        }
        return true;
    }

    //override onActivityResult method to get the data being transformed by intent from secondActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String returnData = data.getStringExtra("data2first");
                    Log.d("FirstActivity", returnData);
                }
                break;
            default: break;
        }
    }
}
