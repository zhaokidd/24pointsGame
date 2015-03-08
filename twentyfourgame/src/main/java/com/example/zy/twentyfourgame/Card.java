package com.example.zy.twentyfourgame;


/**
 * Created by zy on 2015/3/7 0007.
 */
public class Card {
    private int number;
    private String type;
    private int resourceId;

    public Card(int num,String type,int icon){
        this.number=num;
        this.type=type;
        this.resourceId=icon;
    }
    public int returnImage(){
        return this.resourceId;
    }

    public int getNumber(){
        return number;
    }
}
