package com.example.android.marked;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class TextContentActivity extends AppCompatActivity {
    float fontsize=15;//当前字体大小
    private ListView listview;//listview的实体
    ArrayList content=new ArrayList();//实例化spannable
    int[] keywords={0,0,0,0,0,0};//keywords的索引{position,index[0],index[1]}
    private MyAdapter adapter;//adapger实例
    private String[] words={"","","",""};


    int flag=0;//这是当前第几个词
    Gson relationship=new Gson();
    Artical artical;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_content);
        NavigationView navigationView = (NavigationView) findViewById(R.id.relation);
        final DrawerLayout drawer=(DrawerLayout)findViewById(R.id.relationdrawer);
        final Intent it=getIntent();
        String articalid=it.getStringExtra("id");
        artical=new Artical(articalid);
        //从文件中获取对应的文字
        listview=(ListView)findViewById(R.id.listview);
        adapter=new MyAdapter(this);
        listview.setAdapter(adapter);
        String text=it.getStringExtra("text");
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    flag=0;
//                    // Handle action bar item clicks here. The action bar will
//                    // automatically handle clicks on the Home/Up button, so long
//                    // as you specify a parent activity in AndroidManifest.xml.
//                    int id = item.getItemId();
//                Context context = getApplicationContext();
//                Toast.makeText(context,id,Toast.LENGTH_SHORT);
//                    //noinspection SimplifiableIfStatement
//                    if (id == R.id.nationality) {
//                        //建立关系，清除flag
//                        flag=0;
//                        System.out.println(keywords[0]);
//                        content[keywords[0]].setSpan(new BackgroundColorSpan(Color.argb(200,150,50,50)),keywords[1],keywords[2],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                        content[keywords[3]].setSpan(new BackgroundColorSpan(Color.argb(200,150,50,50)),keywords[4],keywords[5],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                        adapter.notifyDataSetChanged();
//                        EntityMention entity1=new EntityMention(words[2],keywords[1],keywords[2],words[0],keywords[0]);
//                        EntityMention entity2=new EntityMention(words[3],keywords[4],keywords[5],words[1],keywords[3]);
//                        RelationMention relation=new RelationMention(entity1.text,entity2.text,"country");
//                        artical.addrelation(entity1,entity2,relation);
//                        System.out.println(relationship.toJson(artical));
//
//                    }else if(id==R.id.capital){
//                        flag=0;
//                        content[keywords[0]].setSpan(new BackgroundColorSpan(Color.argb(200,50,150,50)),keywords[1],keywords[2],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                        content[keywords[3]].setSpan(new BackgroundColorSpan(Color.argb(200,50,150,50)),keywords[4],keywords[5],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                        adapter.notifyDataSetChanged();
//                        EntityMention entity1=new EntityMention(words[2],keywords[1],keywords[2],words[0],keywords[0]);
//                        EntityMention entity2=new EntityMention(words[3],keywords[4],keywords[5],words[1],keywords[3]);
//                        RelationMention relation=new RelationMention(entity1.text,entity2.text,"country");
//                        artical.addrelation(entity1,entity2,relation);
//                        System.out.println(relationship.toJson(artical));
//
//                    }else if(id==R.id.contains){
//                        flag=0;
//                        content[keywords[0]].setSpan(new BackgroundColorSpan(Color.argb(200,50,50,150)),keywords[1],keywords[2],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                        content[keywords[3]].setSpan(new BackgroundColorSpan(Color.argb(200,50,50,150)),keywords[4],keywords[5],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                        adapter.notifyDataSetChanged();
//                        EntityMention entity1=new EntityMention(words[2],keywords[1],keywords[2],words[0],keywords[0]);
//                        EntityMention entity2=new EntityMention(words[3],keywords[4],keywords[5],words[1],keywords[3]);
//                        RelationMention relation=new RelationMention(entity1.text,entity2.text,"country");
//                        artical.addrelation(entity1,entity2,relation);
//                        System.out.println(relationship.toJson(artical));
//
//                    }else if (id==R.id.place_of_death){
//                        flag=0;
//                        content[keywords[0]].setSpan(new BackgroundColorSpan(Color.argb(200,150,50,250)),keywords[1],keywords[2],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                        content[keywords[3]].setSpan(new BackgroundColorSpan(Color.argb(200,150,50,250)),keywords[4],keywords[5],Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                        adapter.notifyDataSetChanged();
//                        EntityMention entity1=new EntityMention(words[2],keywords[1],keywords[2],words[0],keywords[0]);
//                        EntityMention entity2=new EntityMention(words[3],keywords[4],keywords[5],words[1],keywords[3]);
//                        RelationMention relation=new RelationMention(entity1.text,entity2.text,"country");
//                        artical.addrelation(entity1,entity2,relation);
//                        System.out.println(relationship.toJson(artical));
//
//                    }
//                Toast.makeText(context,"marked!",Toast.LENGTH_SHORT);
//                drawer.closeDrawer(GravityCompat.START);
//               return false;
//            }
//        });
        //将文字显示在界面上
        //先将文字进行逐句分割
        String[] stringcontent=text.split("。");
        for(int i=0;i<stringcontent.length;i++){
            content.add(new SpannableString(stringcontent[i]));
        }
    //新建自己的adapter适配器（可以自动调节字体大小和后续颜色）

//        final Intent markedinterface=new Intent(this,MarkedInterface.class);
        //点击进入下一个界面


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ListView listView = (ListView) adapterView;
                Spannable fruit0 = (Spannable) listView.getItemAtPosition(position);
                String fruit=fruit0.toString();
                System.out.println("sdafasdfasdf"+fruit);

                Intent intent = new Intent(TextContentActivity.this,MarkedInterface.class);
                Bundle bundle = new Bundle();
                bundle.putString("sentence", fruit);
                bundle.putInt("position",position);
                bundle.putIntArray("keywords",keywords);
                bundle.putInt("flag",flag);
                intent.putExtras(bundle);
                startActivityForResult(intent,102);

                //FruitList.this.finish();
            }
        });

        //设置返回按钮的动作
        FloatingActionButton back=(FloatingActionButton)findViewById(R.id.content2title);
        final Intent content2title=new Intent(this,MainActivity.class);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json=relationship.toJson(artical);
                System.out.println(relationship.toJson(artical));
                finish();
            }
        });
        //设置字体大小的按钮：
        //首先获取当前的字体大小

        FloatingActionButton bigger=(FloatingActionButton)findViewById(R.id.FontSizeBigger);
        FloatingActionButton smaller=(FloatingActionButton)findViewById(R.id.FontSizeSmaller);
        bigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontsize+=5;
                listview.setAdapter(adapter);
            }
        });
        smaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontsize-=5;
                listview.setAdapter(adapter);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//            int[] result = data.getExtras().getIntArray("keywords");//得到新Activity 关闭后返回的数据
//            System.out.println("return keywords : :  " + result);
//            int position=data.getExtras().getInt("position");
//            System.out.println("return keywords : :  " + position);
//            int key_num=data.getExtras().getInt("key_num");
//            String[] entity=data.getExtras().getStringArray("entity");

            ArrayList<String> entity=data.getExtras().getStringArrayList("entity");
            ArrayList<String> relation=data.getExtras().getStringArrayList("relation");
            int sentence=data.getIntExtra("position",99);
            Context context = getApplicationContext();
        //接下来将获取到的变量做成结构存在数组里面
            for(int i=0;i<entity.size();i=i+4){
                int location1=Integer.valueOf(entity.get(i+1)).intValue();
                int location2=Integer.valueOf(entity.get(i+2)).intValue();
                EntityMention entity_temp=new EntityMention(entity.get(i),location1,location2,entity.get(i+3),sentence);
                artical.addrelation(entity_temp);

            }
            //将这个句子中的关系和实体储存好
            for(int i=0;i<relation.size();i=i+3){
                RelationMention relation_temp=new RelationMention(relation.get(i),relation.get(i+1),relation.get(i+2));
                artical.addrelation(relation_temp);
            }
            for(int i=0;i<artical.Entities.size();i++){
                EntityMention entity_temp=((EntityMention)artical.Entities.get(i));
                ((Spannable) content.get(entity_temp.sentence)).setSpan(new BackgroundColorSpan(Color.argb(250,0,255,255)),entity_temp.Start,entity_temp.End,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
             adapter.notifyDataSetChanged();
//            for(int i=0;i<artical.Relations.size();i++){
//                RelationMention relation_temp=(RelationMention)artical.Relations.get(i);
//                if(relation_temp.relation=="nationality"){
//
//                }else if(relation_temp.relation=="capital"){
//
//                }else if(relation_temp.relation=="contains"){
//
//                }else if(relation_temp.relation=="place of death"){
//
//                }
//
//            }
//            for(int i=0;i<key_num;i=i+2) {
//                if(flag<2) {
//                    words[flag] = content[position].toString().subSequence(result[i], result[i + 1]).toString();
//                    if(key_num==2) {
//                        words[flag + 2] = entity[0];
//                    }else{
//                        words[flag+2]=entity[flag];
//                    }
//                    keywords[3*flag]=position;
//                    keywords[3*flag+1]=result[i];
//                    keywords[3*flag+2]=result[i+1];
//                    Toast.makeText(context, "you choice"+words[flag], Toast.LENGTH_SHORT).show();
//                    content[position].setSpan(new BackgroundColorSpan(Color.argb(250,0,255,255)),result[i],result[i+1],
//                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//                    adapter.notifyDataSetChanged();
//
//                    flag++;
//                }else break;
//            }
//            if(flag==2){
//                Toast.makeText(context, words[2]+": "+words[0]+"  "+words[3]+": "+words[1], Toast.LENGTH_SHORT).show();
//                DrawerLayout drawer=(DrawerLayout)findViewById(R.id.relationdrawer);
//                drawer.openDrawer(GravityCompat.START);
//            }

//            for(int i=0;i<key_num;i=i+2){
//                content[position].setSpan(new BackgroundColorSpan(Color.argb(250,0,255,255)),result[i],result[i+1],
//                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//            }
//            adapter.notifyDataSetChanged();

    }
    //从文件流读取文字
    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    //ViewHolder静态类
    static class ViewHolder
    {

        public TextView info;
    }
    //自定义的adapter适配器
    private class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater=null;//LayoutInfalter对象用来导入布局

        public MyAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount()
        {
          //  System.out.println("content length"+content.length);
            return content.size();//项目总数
        }
        @Override
        public Object getItem(int arg0) {
            return (Spannable)content.get(arg0);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            //如果缓存convertView为空，则需要创建View
            if(convertView == null)
            {
                holder = new ViewHolder();
                //根据自定义的Item布局加载布局
                convertView = inflater.inflate(R.layout.simple_item, null);
                holder.info = (TextView)convertView.findViewById(R.id.content);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            }else
            {
                holder = (ViewHolder)convertView.getTag();

            }

           // holder.info.setMovementMethod(LinkMovementMethod.getInstance());


//            Spannable x= new SpannableString(content[position]);
//            x.setSpan(new BackgroundColorSpan(Color.argb(255, 0, 255, 255)), 0, content[position].length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            holder.info.setText((Spannable)content.get(position));
            holder.info.setTextSize(fontsize);

            return convertView;
        }


    }

}
