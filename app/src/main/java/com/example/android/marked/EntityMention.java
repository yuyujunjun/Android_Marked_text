package com.example.android.marked;

/**
 * Created by fcsx1 on 2017/11/19.
 */

public class EntityMention{
    public String type;
    public int Start;
    public int End;
    public String text;
    public int sentence;
    EntityMention(String type,int start,int End,String text,int sentence){
        this.type=type;
        this.Start=start;
        this.End=End;
        this.text=text;
        this.sentence=sentence;
    }
    public int returnsentence(){
        return this.sentence;
    }
}