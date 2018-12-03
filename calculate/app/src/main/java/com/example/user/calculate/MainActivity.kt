package com.example.daisy.calculate


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    internal var input: TextView? = null  //初始化
    internal var clear_flag: Boolean = false //初始化

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.input) as TextView
        //数字实例化
        val button_0 = findViewById(R.id.button_0) as Button
        val button_1 = findViewById(R.id.button_1) as Button
        val button_2 = findViewById(R.id.button_2) as Button
        val button_3 = findViewById(R.id.button_3) as Button
        val button_4 = findViewById(R.id.button_4) as Button
        val button_5 = findViewById(R.id.button_5) as Button
        val button_6 = findViewById(R.id.button_6) as Button
        val button_7 = findViewById(R.id.button_7) as Button
        val button_8 = findViewById(R.id.button_8) as Button
        val button_9 = findViewById(R.id.button_9) as Button

        //操作实例化
        val point = findViewById(R.id.point) as Button
        val clear = findViewById(R.id.clear) as Button
        val delet = findViewById(R.id.delet) as Button
        val percent = findViewById(R.id.percent) as Button
        val divide = findViewById(R.id.divide) as Button
        val mul = findViewById(R.id.mul) as Button
        val sub = findViewById(R.id.sub) as Button
        val add = findViewById(R.id.add) as Button
        val equ = findViewById(R.id.equ) as Button


        button_0.setOnClickListener(this)
        button_1.setOnClickListener(this)
        button_2.setOnClickListener(this)
        button_3.setOnClickListener(this)
        button_4.setOnClickListener(this)
        button_5.setOnClickListener(this)
        button_6.setOnClickListener(this)
        button_7.setOnClickListener(this)
        button_8.setOnClickListener(this)
        button_9.setOnClickListener(this)
        point.setOnClickListener(this)
        clear.setOnClickListener(this)
        delet.setOnClickListener(this)
        add.setOnClickListener(this)
        point.setOnClickListener(this)
        mul.setOnClickListener(this)
        divide.setOnClickListener(this)
        input!!.setOnClickListener(this)
        percent.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var str = input!!.text.toString()
        when (v.id) {
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9, R.id.point -> {
                if (clear_flag) {
                    clear_flag = false
                    str = ""
                }
                input!!.text = str + (v as Button).text
            }
            R.id.sub, R.id.divide, R.id.mul, R.id.add -> {
                clear_flag = false
                input!!.text = str + " " + (v as Button).text + " "//在屏幕显示+，-，*，/，并于字符间有空格
            }
            R.id.percent -> {
                val result3 = 0.01 * java.lang.Double.parseDouble(str)
                val r3 = result3.toString()
                input!!.text = r3
            }

            R.id.delet -> input!!.text = str.substring(0, str.length - 1)//从字符串的0位置开始，索引到字符串的倒数第二个字符串
            R.id.clear -> {
                clear_flag = false
                input!!.text = ""
            }
            R.id.equ -> getResult()
        }
    }

    private fun getResult() {
        val exp = input!!.text.toString()

        if (!exp.contains(" ")) {
            return
        }
        clear_flag = true
        var result = 0.0
        val s1 = exp.substring(0, exp.indexOf(" ")) //获取运算符前面的字符串
        val op = exp.substring(exp.indexOf(" ") + 1, exp.indexOf(" ") + 2)
        val s2 = exp.substring(exp.indexOf(" ") + 3)//获取运算符后面的字符串
        if (s1 != " " && s2 != " ") {
            var d1 = java.lang.Double.parseDouble(s1)
            var d2 = java.lang.Double.parseDouble(s2)
            when (op) {
                "+" -> result = d1 + d2
                "-" -> result = d1 - d2
                "*" -> result = d1 * d2
                "/" -> if (d2 == 0.0) {
                    Toast.makeText(this@MainActivity, "除数不能为0", Toast.LENGTH_SHORT).show()
                } else {
                    result = d1 / d2
                }
            }
            //判断第一个字符是否为小数点，如果是小数点自动变为0.x,如果用正负号转换，自动变为-0.x
            if (s1[0] == '.') {
                d1 = 0 + d1
            }
            if (s2[0] == '.') {
                d2 = 0 + d2
            }
            if (s1[0] != '-') {
                d1 = 0 - d1
            }

            input!!.text = result.toString()//把运算结果输出到显示屏

        } else if (s1[0] != '-') {
            val s = exp.substring(2, exp.indexOf(" "))
            val d1 = 0 - java.lang.Double.parseDouble(s)
        } else if (s2[0] != '-') {
            val s = exp.substring(exp.indexOf(" ") + 4)
            val d2 = 0 - java.lang.Double.parseDouble(s)
        } else
            return

    }
}
