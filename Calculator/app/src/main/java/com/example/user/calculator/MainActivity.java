package com.example.user.calculator;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private String expression = "";
    private boolean last_equal = false;//上一次的按键是否为等号
    protected EditText text1;//第一行，用来显示按过等号之后的完整表达式
    protected EditText text2;//第二行，用来显示表达式和结果
    private View board;
    private int screen_width;
    private int screen_height;
    private LinearLayout display;
    private Button[] buttons;

    /*
    TODO:
    科学计算器
     */
    protected static boolean isSimple = true;//当前是否是简易计算器
    private View board2;
    private Button[] buttons2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (EditText)findViewById(R.id.text1);
        text2 = (EditText)findViewById(R.id.text2);


        //初始化计算器键盘
        buttons = new Button[18];
        initSimpleBoard(buttons);//初始化计算器键盘
        board = (View)findViewById(R.id.board);


        if(savedInstanceState != null){
            text1.setText(savedInstanceState.getString("text1"));
            text2.setText(savedInstanceState.getString("text2"));
            Log.v("TAG==>","OKKOKOKO");
        }
    }


    //活动被回收时，保存临时数据
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("text1", text1.getText().toString());
        outState.putString("text2", text2.getText().toString());

    }


    //为了得到用户区域的高度，重写onWindowFocusChanged,这个方法在onResume之后被调用
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            Dimension dimen1 = getAreaOne(this);
            Dimension dimen2 = getAreaTwo(this);
            Dimension dimen3 = getAreaThree(this);
            Log.v("one=>","Area one : \n\tWidth: "+dimen1.mWidth + ";\tHeight: "+dimen1.mHeight);
            Log.v("two=>","\nArea two: \n\tWidth: "+dimen2.mWidth + ";\tHeight: "+dimen2.mHeight);
            Log.v("three","\nArea three: \n\tWidth: "+dimen3.mWidth + ";\tHeight: "+dimen3.mHeight);

            Log.v("TAG","---isSimple=>>" + isSimple);
            screen_width = dimen3.mWidth;
            screen_height = dimen3.mHeight;

            initWidthAndHeight();
        }
    }

    //初始化键盘，显示区域的宽和高（显示区域包括change按钮，text，text2）
    private void initWidthAndHeight(){
        //设置change按钮和显示区域的高度只和 始终为用户区域高度的三分之一
        display  = (LinearLayout)findViewById(R.id.display);
        android.view.ViewGroup.LayoutParams lp =display.getLayoutParams();
        lp.height=screen_height/3;

        //简易计算器
        int btn_width = screen_width/4;
        int btn_height = (screen_height - screen_height/3)/5;//tablelayout为屏幕的2/3大，一共5行
        for(int i= 0; i < 18; i++){
            buttons[i].setWidth(btn_width);
            buttons[i].setHeight(btn_height);
        }

        buttons[0].setWidth(btn_width*2);
        buttons[16].setHeight(btn_height*2);
    }

    //初始化计算器键盘
    private void initSimpleBoard(final Button[] buttons){
        buttons[0] = (Button)findViewById(R.id.zero);
        buttons[1] = (Button)findViewById(R.id.one);
        buttons[2] = (Button)findViewById(R.id.two);
        buttons[3] = (Button)findViewById(R.id.three);
        buttons[4] = (Button)findViewById(R.id.four);
        buttons[5] = (Button)findViewById(R.id.five);
        buttons[6] = (Button)findViewById(R.id.six);
        buttons[7] = (Button)findViewById(R.id.seven);
        buttons[8] = (Button)findViewById(R.id.eight);
        buttons[9] = (Button)findViewById(R.id.nine);

        buttons[10] = (Button)findViewById(R.id.empty);
        buttons[11] = (Button)findViewById(R.id.delete);
        buttons[12] = (Button)findViewById(R.id.divide);
        buttons[13] = (Button)findViewById(R.id.multiple);
        buttons[14] = (Button)findViewById(R.id.minus);
        buttons[15] = (Button)findViewById(R.id.plus);
        buttons[16] = (Button)findViewById(R.id.equal);
        buttons[17] = (Button)findViewById(R.id.dot);


        initCommonBtns(buttons);
    }

    //初始化计算器的18个按钮
    private void initCommonBtns(final Button[] buttons){
        //添加监听事件
        //数字0～9
        for(int i = 0; i < 10; i++){
            final int m = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(last_equal){
                        expression = "";//这次按的数字，如果上次按了等号，则清空表达式
                        last_equal = false;
                    }
                    expression += buttons[m].getText();
                    text2.setText(expression);
                    text2.setSelection(expression.length());
                }
            });
        }
        //empty
        buttons[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression = "";
                text2.setText("0");
                text1.setText(null);
                last_equal = false;
            }
        });
        //delete
        buttons[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expression.length() < 1){
                    return;
                }
                expression = expression.substring(0,expression.length()-1);
                text2.setText(expression);
                text2.setSelection(expression.length());
                last_equal = false;
            }
        });
        //divide
        buttons[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression += buttons[12].getText();
                text2.setText(expression);
                text2.setSelection(expression.length());
                last_equal = false;
            }
        });
        //multiple
        buttons[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression += buttons[13].getText();
                text2.setText(expression);
                text2.setSelection(expression.length());
                last_equal = false;
            }
        });
        //minus
        buttons[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression += buttons[14].getText();
                text2.setText(expression);
                text2.setSelection(expression.length());
                last_equal = false;
            }
        });
        //plus
        buttons[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression += buttons[15].getText();
                text2.setText(expression);
                text2.setSelection(expression.length());
                last_equal = false;
            }
        });
        //equal
        buttons[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(last_equal) return;//如果上次还是按的等号，那么什么也不做
                text1.setText(expression + "=");
                        text1.setSelection(expression.length()+1);//在第一行显示计算表达式
                        try{
                            expression = Calculate.calculate(expression);
                            text2.setText(expression);//在第二行显示计算结果
                        }catch(Exception exception){
                            text2.setText("表达式错误!");//在第二行显示计算结果
                            expression = "";
                        }
                // 为下一次按计算器键盘做准备。
                // 如果下次按的是数字，那么清空第二行重新输入第一个数。
                // 如果是非数字，那就当这次的结果是输入的第一个数，直接参与运算。
                last_equal = true;

            }


        });
        buttons[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression += buttons[17].getText();
                text2.setText(expression);
                text2.setSelection(expression.length());
                last_equal = false;
            }
        });
    }



    //屏幕高度
    private Dimension getAreaOne(Activity activity){
        Dimension dimen = new Dimension();
        Display disp = activity.getWindowManager().getDefaultDisplay();
        Point outP = new Point();
        disp.getSize(outP);
        dimen.mWidth = outP.x ;
        dimen.mHeight = outP.y;
        return dimen;
    }

    //不算状态栏的高度
    private Dimension getAreaTwo(Activity activity){
        Dimension dimen = new Dimension();
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        System.out.println("top:"+outRect.top +" ; left: "+outRect.left) ;
        dimen.mWidth = outRect.width() ;
        dimen.mHeight = outRect.height();
        return dimen;
    }

    //不算状态栏，标题栏的高度
    private Dimension getAreaThree(Activity activity){
        Dimension dimen = new Dimension();
        // 用户绘制区域
        Rect outRect = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
        dimen.mWidth = outRect.width() ;
        dimen.mHeight = outRect.height();
        // end
        return dimen;
    }
    private class Dimension {
        public int mWidth ;
        public int mHeight ;
        public Dimension(){}
    }
}
