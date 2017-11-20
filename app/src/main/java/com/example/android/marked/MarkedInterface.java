package com.example.android.marked;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.action;
import static android.R.attr.key;
import static android.R.attr.type;
import static com.example.android.marked.R.id.sentence2content;


public class MarkedInterface extends AppCompatActivity {
    ArrayList<String> entity=new ArrayList<String>();//实体{类型，位置1，位置2，文本}
//    int flag=0;
    ArrayList<String> relation=new ArrayList<String>();//关系{文本1，文本2，类型}
    String[] buildRelation={"",""};//用来存储两次点击的词语
    String[] buildType={null,null};//用来存储两次点击的类型
    boolean isMove=false;//用来辅助判断是否手指移动
    private long[] mHits = new long[2];//判断是否双击
    int off;//储存第一次点击的位置
    int[] buildRelationindex={0,0,0,0};//储存当前选择的两个实体的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent it=getIntent();
        String sentence=it.getStringExtra("sentence");

        setContentView(R.layout.activity_marked_interface);

        final Textpage content=(Textpage)findViewById(R.id.content);
        content.setText(sentence, TextView.BufferType.SPANNABLE);
        content.setInputType(InputType.TYPE_NULL);
        content.setSingleLine(false);
        content.setHorizontallyScrolling(false);
        content.setCursorVisible(false);
        content.setMovementMethod(LinkMovementMethod.getInstance());
        content.setTextSize(50);
        FloatingActionButton back=(FloatingActionButton)findViewById(sentence2content);
        final DrawerLayout drawer=(DrawerLayout)findViewById(R.id.marked);
        NavigationView nav=(NavigationView)findViewById(R.id.entity);
        final NavigationView navrelation=(NavigationView)findViewById(R.id.relation);

        navrelation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                Context context = getApplicationContext();
                Toast.makeText(context,id,Toast.LENGTH_SHORT);
                //noinspection SimplifiableIfStatement
                if (id == R.id.nationality) {
                    //建立关系，清除flag
//                    flag=0;
//                    System.out.println(keywords[0]);
                    Spannable str = (Spannable) content.getEditableText();
                    str.setSpan(new BackgroundColorSpan(Color.argb(200,150,50,50)),buildRelationindex[0],buildRelationindex[1],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    str.setSpan(new BackgroundColorSpan(Color.argb(200,150,50,50)),buildRelationindex[2],buildRelationindex[3],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    relation.add(str.toString().subSequence(buildRelationindex[0],buildRelationindex[1]).toString());
                    relation.add(str.toString().subSequence(buildRelationindex[2],buildRelationindex[3]).toString());
                    relation.add("nationality");


                }else if(id==R.id.capital){
                    Spannable str = (Spannable) content.getEditableText();
                    str.setSpan(new BackgroundColorSpan(Color.argb(200,150,150,50)),buildRelationindex[0],buildRelationindex[1],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    str.setSpan(new BackgroundColorSpan(Color.argb(200,150,150,50)),buildRelationindex[2],buildRelationindex[3],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    relation.add(str.toString().subSequence(buildRelationindex[0],buildRelationindex[1]).toString());
                    relation.add(str.toString().subSequence(buildRelationindex[2],buildRelationindex[3]).toString());
                    relation.add("capital");

                }else if(id==R.id.contains){
                    Spannable str = (Spannable) content.getEditableText();
                    str.setSpan(new BackgroundColorSpan(Color.argb(200,150,50,150)),buildRelationindex[0],buildRelationindex[1],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    str.setSpan(new BackgroundColorSpan(Color.argb(200,150,50,150)),buildRelationindex[2],buildRelationindex[3],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    relation.add(str.toString().subSequence(buildRelationindex[0],buildRelationindex[1]).toString());
                    relation.add(str.toString().subSequence(buildRelationindex[2],buildRelationindex[3]).toString());
                    relation.add("contains");

                }else if (id==R.id.place_of_death){
                    Spannable str = (Spannable) content.getEditableText();
                    str.setSpan(new BackgroundColorSpan(Color.argb(200,150,150,150)),buildRelationindex[0],buildRelationindex[1],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    str.setSpan(new BackgroundColorSpan(Color.argb(200,150,150,150)),buildRelationindex[2],buildRelationindex[3],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    relation.add(str.toString().subSequence(buildRelationindex[0],buildRelationindex[1]).toString());
                    relation.add(str.toString().subSequence(buildRelationindex[2],buildRelationindex[3]).toString());
                    relation.add("place of death");

                }
                Toast.makeText(context,"marked!",Toast.LENGTH_SHORT);
                drawer.closeDrawer(GravityCompat.END);
                return false;
            }
        });
        content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                int action = event.getAction();
                Layout layout = content.getLayout();
                int line = 0;

                CharSequence selectText = "";
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("entity size: "+entity.size());
                        line = layout.getLineForVertical(view.getScrollY() + (int) event.getY());
                        off = layout.getOffsetForHorizontal(line, (int) event.getX());

                        break;
                    case MotionEvent.ACTION_MOVE:
                        isMove=true;
                        line = layout.getLineForVertical(view.getScrollY() + (int) event.getY());
                        int curOff = layout.getOffsetForHorizontal(line, (int) event.getX());

                        if (curOff > off) {
                        } else {
                            int tmp = off;
                            off = curOff;
                            curOff = tmp;
                        }
                        int[] waitingpos = {off, curOff};
                        content.add_pos(waitingpos);
                        selectText = content.getText().subSequence(off, curOff);
                        System.out.println(selectText);
                        //                System.out.println(off);
                        //                System.out.println(curOff);
                        //                Selection.setSelection(str, off, curOff);
                        //                str.setSpan(new BackgroundColorSpan(Color.argb(250, 0, 255, 255)), off, curOff, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        //                Selection.removeSelection(str);
                        break;
                    case MotionEvent.ACTION_UP:
                        //这个位置最好用已经建立好实体的索引，因为随着点击content.keywords可能会发生变化，更新不及时，而建立实体之前一定会处理好一些边界条件
                        System.out.println("you are in action up");
                        line = layout.getLineForVertical(view.getScrollY() + (int) event.getY());
                        curOff = layout.getOffsetForHorizontal(line, (int) event.getX());

                        if (curOff > off) {
                        } else {
                            int tmp = off;
                            off = curOff;
                            curOff = tmp;
                        }

//                Spannable str = (Spannable) getEditableText();
//                Selection.removeSelection(str);
                        //判断双击还是单击
                        selectText = content.getText().subSequence(off, curOff);
                        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                        mHits[mHits.length - 1] = SystemClock.uptimeMillis();//获取手机开机时间
                        if (mHits[mHits.length - 1] - mHits[0] < 300) {
                            /**双击的业务逻辑*/
                            content.buildindex--;
                            if (content.buildindex < 0) content.buildindex = 0;
                            //这里确保它和前一瞬间单击的时候使用的是同样的start和end
                            //找到双击位置，将该双击位置最近的区间位置置为0
                            int[] t = {off, curOff};
                            int[] index = content.find_pos(t);

                            content.clearFocus();

                        } else if(off==curOff){//如果只是点击而没有移动
                            Spannable str = (Spannable) content.getEditableText();
                            for(int i=0;i<entity.size();i=i+4) {
                                str.setSpan(new BackgroundColorSpan(Color.argb(250, 0, 255, 255)), Integer.valueOf(entity.get(i+1)).intValue(), Integer.valueOf(entity.get(i+2)).intValue(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                            }
                            System.out.println("isnot move");
                            int[] t = {off, curOff};
                            int[] index = find_entity_pos(off);
                            //content.find_pos不能用这个函数因为此时keywords已经变化了
                            if(index!=null) {
                                buildRelation[content.buildindex] = str.toString().subSequence(index[0], index[1]).toString();
                                buildRelationindex[2 * content.buildindex] = index[0];
                                buildRelationindex[2 * content.buildindex + 1] = index[1];
                                buildType[content.buildindex] = find_entity_type(off);
                                content.buildindex++;
                                for (int i = 0; i < content.buildindex * 2; i = i + 2) {
                                    str.setSpan(new BackgroundColorSpan(Color.argb(200, 80, 50, 255)), buildRelationindex[i], buildRelationindex[i + 1], Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                                }
                                if (content.buildindex == 2) {
                                    content.buildindex = 0;
                                    hide_item(buildType, navrelation);
                                    buildType[0] = null;
                                    buildType[1] = null;
                                    drawer.openDrawer(GravityCompat.END);
                                }
                            }

                        }else if(off!=curOff){
                            System.out.println("ismove");
                            drawer.openDrawer(GravityCompat.START);
                            isMove=false;
                        }
                        break;
                }
                return true;
            }
        });
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                int key_num=content.getkey_num();
                int []keywords=content.getKeywordsindex();
                System.out.println("keynum: "+key_num);
                if(key_num>0) {
                    if (id == R.id.country) {
                        entity.add("country");
                        String s1=String.valueOf(keywords[key_num-2]);
                        String s2=String.valueOf(keywords[key_num-1]);
                        entity.add(s1);
                        entity.add(s2);
                        entity.add(content.toString().subSequence(keywords[key_num-2],keywords[key_num-1]).toString());


                    } else if (id == R.id.city) {
                        entity.add("city");
                        String s1=String.valueOf(keywords[key_num-2]);
                        String s2=String.valueOf(keywords[key_num-1]);
                        entity.add(s1);
                        entity.add(s2);
                        entity.add(content.toString().subSequence(keywords[key_num-2],keywords[key_num-1]).toString());
                    } else if (id == R.id.person) {
                        entity.add("person");
                        String s1=String.valueOf(keywords[key_num-2]);
                        String s2=String.valueOf(keywords[key_num-1]);
                        entity.add(s1);
                        entity.add(s2);
                        entity.add(content.toString().subSequence(keywords[key_num-2],keywords[key_num-1]).toString());
                    }
                }else if(key_num==0){
                    Context context=getApplicationContext();
                    Toast.makeText(context,"亲请至少选择一个实体类型哦",Toast.LENGTH_SHORT);
                }

                drawer.closeDrawer(GravityCompat.START);
//                if(key_num/2+flag>=2){
//                    backtocontent(it,content);
//                }
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backtocontent(it,content);
            }
        });

    }
    private void hide_item(String[] entity_type,NavigationView navrelation){
        for(int i=0;i<navrelation.getMenu().size();i++){
            navrelation.getMenu().getItem(i).setVisible(true);
        }
        if(buildType[0]==null||buildType[1]==null){
            Context context=getApplicationContext();
            Toast.makeText(context,"Please choose entities first",Toast.LENGTH_SHORT);
            for(int i=0;i<navrelation.getMenu().size();i++){
                navrelation.getMenu().getItem(i).setVisible(false);
            }
        }
        navrelation.getMenu().getItem(1).setVisible(false);
    }
    private String find_entity_type(int off){

        for(int i=0;i<entity.size();i=i+4){
            if(Integer.valueOf(entity.get(i+1)).intValue()<=off&&Integer.valueOf(entity.get(i+2)).intValue()>=off){
                return entity.get(i);
            }
        }

        return null;
    }
    private int[] find_entity_pos(int off){
        int []t={0,0};
        for(int i=0;i<entity.size();i=i+4){
            if(Integer.valueOf(entity.get(i+1)).intValue()<=off&&Integer.valueOf(entity.get(i+2)).intValue()>=off){
                t[0]=Integer.valueOf(entity.get(i+1)).intValue();
                t[1]=Integer.valueOf(entity.get(i+2)).intValue();
                return t;
            }
        }

        return null;
    }
    private void backtocontent(Intent it,Textpage content){
        Intent sentence2content=new Intent();
//        sentence2content.putExtra("keywords",content.getKeywordsindex());
        sentence2content.putExtra("position",it.getIntExtra("position",99));
//        sentence2content.putExtra("key_num",content.getkey_num());
        sentence2content.putStringArrayListExtra("entity",entity);
        sentence2content.putStringArrayListExtra("relation",relation);
        System.out.println("content length938457938utj9euigvn"+entity);
        setResult(RESULT_OK,sentence2content);
        finish();
    }



}
