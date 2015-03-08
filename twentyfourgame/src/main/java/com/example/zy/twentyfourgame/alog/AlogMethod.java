package com.example.zy.twentyfourgame.alog;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by zy on 2015/3/7 0007.
 */
public class AlogMethod {
    private static int[] inputValue = new int[4];
    private static ArrayList<Character> arrayListOfOperator = new ArrayList<Character>(4);
    static{
        arrayListOfOperator.add('+');
        arrayListOfOperator.add('-');
        arrayListOfOperator.add('/');
        arrayListOfOperator.add('*');
    }

    public static boolean stringValid(String s) {
        Log.e("nullReturn",""+arrayListOfOperator.size());
        if (s == null || s.length() < 1) {
            Log.e("nullReturn","null");
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!(Character.isDigit(s.charAt(i)) || arrayListOfOperator.contains(s.charAt(i)) || s.charAt(i) == '(' || s.charAt(i) == ')')) {
                Log.e("nullReturn","character is not digit");
                return false;
            }
        }
        // if the start and the end of the string is an operator , then return false
        if (arrayListOfOperator.contains(s.charAt(0)) || arrayListOfOperator.contains(s.charAt(s.length() - 1))) {
            Log.e("nullReturn","char at 0");
            return false;
        }

        //there must be a digitnumber after the operator
        for (int i = 1; i < s.length() - 1; i++) {
            if (arrayListOfOperator.contains(s.charAt(i))) {
                if (!(Character.isDigit(s.charAt(i + 1)) || s.charAt(i + 1) == '('))
                    return false;
            }
        }
        GenericStack<Character> stack1 = new GenericStack<Character>();
        char c;
        for (int i = 0; i < s.length(); i++) {
            if ((c = s.charAt(i)) == '(')
                stack1.push(c);
            else if (c == ')') {
                if (!stack1.isEmpty() && stack1.pop() == '(') ;
                else
                    return false;
            }
        }

        if (!stack1.isEmpty())
            return false;
        return true;
    }

    /**
     * This method is to return the result of the  given expression
     * Reverse polish Notation
     */
    public static double result(String expression) {
        char c;
        //stringbuilder to store the infix expression
        StringBuilder builder = new StringBuilder();

        //first step: turn the expresssion into infix expression using RPN method
        GenericStack<Character> stackOfOperator = new GenericStack<Character>();
        for (int i = 0; i < expression.length(); i++) {
            if (Character.isDigit(expression.charAt(i))) {
                builder.append(expression.charAt(i));
                if (i < expression.length() - 1 && !Character.isDigit(expression.charAt(i + 1)))
                    builder.append('#');
                if (i == expression.length() - 1)
                    builder.append('#');
                System.out.println(builder);
            } else if (expression.charAt(i) == '(') {
                stackOfOperator.push(expression.charAt(i));
            } else if (expression.charAt(i) == ')') {
                while (!stackOfOperator.isEmpty() && stackOfOperator.peek() != '(') {
                    builder.append(stackOfOperator.pop());
                }
                stackOfOperator.pop();
            } else if (arrayListOfOperator.contains((c = expression.charAt(i)))) {
                if (c == '+' || c == '-') {
                    while (!stackOfOperator.isEmpty() && (arrayListOfOperator.contains(stackOfOperator.peek()))) {
                        builder.append(stackOfOperator.pop());
                    }
                    stackOfOperator.push(c);
                } else {
                    stackOfOperator.push(c);
                }
            }
        }
        while (!stackOfOperator.isEmpty()) {
            builder.append(stackOfOperator.pop());
        }
        System.out.println("----------" + builder);
        String result = builder.toString();
        System.out.println(result.length());
        GenericStack<Double> stackOfOperand = new GenericStack<Double>();
        int j = 0;
        for (int i = 0; i < result.length(); ) {
            if (Character.isDigit((c = result.charAt(i)))) {
                StringBuilder sb = new StringBuilder();
                sb.append(c);
                while (++i < result.length() && ((c = result.charAt(i)) != '#') && Character.isDigit(c)) {
                    sb.append(c);
                    System.out.println(sb.toString());
                }
                double number = 0;
                stackOfOperand.push((number = Double.parseDouble(sb.toString())));
                //存储editText中输入的值
                inputValue[j++] = (int) number;
                i++;
                System.out.println("zifu:" + result.charAt(i));
            } else if (result.charAt(i) != '#') {
                double num2 = stackOfOperand.pop();
                System.out.println(stackOfOperand.getSize());
                System.out.println("num2 is " + num2);
                //System.out.println(stackOfOperand.getSize());
                double num1 = stackOfOperand.pop();
                System.out.println("num1 is " + num1);
                if ((c = result.charAt(i)) == '+')
                    stackOfOperand.push(num1 + num2);
                else if (c == '-')
                    stackOfOperand.push(num1 - num2);
                else if (c == '/')
                    stackOfOperand.push(num1 / num2);
                else if (c == '*')
                    stackOfOperand.push(num1 * num2);
                i++;
            }
        }
        System.out.println(stackOfOperand.getSize());
        return stackOfOperand.pop();
    }

    /**
     * return the array of input value
     * */
    public static  int [] returnArray(){
        int [] array = new int[4];
        for(int i=0;i<array.length;i++){
            array[i]=inputValue[i];
        }
        return array;
    }

 }
