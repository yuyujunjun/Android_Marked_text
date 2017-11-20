package com.example.android.marked;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.marked.R;

import org.w3c.dom.Text;

import static android.media.CamcorderProfile.get;
import static com.example.android.marked.R.id.listview;
import static com.example.android.marked.R.id.textView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList title=new ArrayList();
    titleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        for(int i=0;i<10;i++){
            try{
                String filename="a"+String.valueOf(i);
                Field field=R.raw.class.getField(filename);
                int j= field.getInt(new R.raw());
                Log.d("icon",i+"");
                InputStream inputStream = getResources().openRawResource(j);
                String text=TextContentActivity.getString(inputStream);
                title.add(new SpannableString(text));
            }catch(Exception e){
                Log.e("icon",e.toString());
            }
        }




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter=new titleAdapter(this);
        ListView listview=(ListView)findViewById(R.id.title_listview);
        listview.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,TextContentActivity.class);
                ListView listView = (ListView) adapterView;
                Spannable fruit0 = (Spannable) listView.getItemAtPosition(position);
                String fruit=fruit0.toString();
                System.out.println("sdafasdfasdf"+fruit);
                Bundle bundle = new Bundle();
                bundle.putString("text", fruit);
                bundle.putString("id",String.valueOf(position));
                intent.putExtras(bundle);
                startActivityForResult(intent,101);
                startActivity(intent);

            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.jsonrelation) {
            // Handle the camera action
        } else if (id == R.id.maintitle) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    static class ViewHolder
    {

        public TextView info;
    }
    //自定义的adapter适配器
    private class titleAdapter extends BaseAdapter {
        private LayoutInflater inflater=null;//LayoutInfalter对象用来导入布局

        public titleAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount()
        {
            //  System.out.println("content length"+content.length);
            return title.size();//项目总数
        }
        @Override
        public Object getItem(int arg0) {
            return (Spannable)title.get(arg0);
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
            Spannable temp=new SpannableString("摘略： "+title.get(position).toString().subSequence(10,25).toString()+"......");
            if(position%2==0){
                temp.setSpan(new ForegroundColorSpan(Color.argb(250,50,50,50)),0,temp.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
            holder.info.setText(temp);
            holder.info.setTextSize(20);

            return convertView;
        }


    }
}
