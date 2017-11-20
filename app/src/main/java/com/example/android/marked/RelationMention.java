package com.example.android.marked;

/**
 * Created by fcsx1 on 2017/11/19.
 */

public class RelationMention{
    public String entity1;
    public String entity2;
    public String relation;
    RelationMention(String entity1,String entity2,String relation){
        this.entity1=entity1;
        this.entity2=entity2;
        this.relation=relation;
    }
}