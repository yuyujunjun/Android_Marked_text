package com.example.android.marked;

import android.content.Entity;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by fcsx1 on 2017/11/19.
 */

public class Artical {



    String articalid;
    ArrayList Entities=new ArrayList();
    ArrayList Relations=new ArrayList();
    Artical(String articalid){
        this.articalid=articalid;
    }
    public void addrelation(EntityMention entity1,EntityMention entity2,RelationMention relation ){
        this.Entities.add(entity1);
        this.Entities.add(entity2);
        this.Relations.add(relation);
    }
    public void addrelation(EntityMention entity){
        this.Entities.add(entity);
    }
    public void addrelation(RelationMention relation){
        this.Relations.add(relation);
    }

}
