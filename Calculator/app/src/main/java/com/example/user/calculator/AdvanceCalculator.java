package com.example.user.calculator;

import java.util.Stack;

public class AdvanceCalculator {
    private String test = "";
    private String exprssion = "";
    private Stack<String> operator = null;
    private Stack<Double> number = null;
    private static String[] ADVANCE = {"cos", "sin", "sqrt", "square", "pow", "tan"};

    public AdvanceCalculator(){};

    public AdvanceCalculator(String expression){
        this.test = expression;
    }

    /*
    TODO:
    完善更新表达式时对新表达式的检测条件
     */
    public boolean updateExpression(String exprssion){
        if(this.exprssion.equals(exprssion))
            return false;
        else{
            this.exprssion = exprssion;
            return true;
        }
    }

    public double calculate(){
        double res = 0.0;

        return res;
    }

    /*return if wait is needed to be calculated before top
    True: need
    Flase: needn't
     */
    public boolean opCmp(String wait, String top){
        boolean res = false;
        if(wait.equals("(")){
            return true;
        }else if(wait.equals(")")){
            if(top.equals("(")) return false;
            else return false;
        }
        else if(isAdvance(""));
        return res;
    }

    private boolean isAdvance(String op){
        return false;
    }

    public static  void main(String args[]){

    }

    private boolean test(String expression){

    }


}
