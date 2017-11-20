package  com.example.android.marked;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.InputType;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.content.Context;
import android.support.v7.widget.TintContextWrapper;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.marked.R;

import java.util.ArrayList;

/**
 * Created by fcsx1 on 2017/11/10.
 */

public class Textpage extends android.support.v7.widget.AppCompatEditText  {
    public int []keywords=new int[100];
    public int key_num;


    int buildindex=0;
    public Textpage(Context context) {
        super(context);
        initialize();
    }

    public Textpage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public Textpage(Context context, AttributeSet attrs) {
        super(context, attrs);


    }




    private   void  initialize() {
//

        key_num=0;
        setGravity(Gravity.TOP);
//改变默认的单行模式
        setSingleLine(false);
//水平滚动设置为False
        setHorizontallyScrolling(false);
        setBackgroundColor(Color.WHITE);


    }

    @Override
    protected   void  onCreateContextMenu(ContextMenu menu) {
        //不做任何处理，为了阻止长按的时候弹出上下文菜单
    }

    @Override
    public   boolean  getDefaultEditable() {
        return   false ;
    }

    @Override
    public   boolean  onTouchEvent(MotionEvent event) {

//        int  action = event.getAction();
//        Layout layout = getLayout();
//        int  line =  0 ;
//
//        CharSequence selectText = "";
//        switch (action) {
//            case  MotionEvent.ACTION_DOWN:
//                line = layout.getLineForVertical(getScrollY()+ (int )event.getY());
//                off = layout.getOffsetForHorizontal(line, (int )event.getX());
//
//                break ;
//            case  MotionEvent.ACTION_MOVE:
//                line = layout.getLineForVertical(getScrollY()+(int )event.getY());
//                int  curOff = layout.getOffsetForHorizontal(line, ( int )event.getX());
//
//                if(curOff>off){}
//                else {
//                    int tmp=off;
//                    off=curOff;
//                    curOff=tmp;
//                }
//                int[]waitingpos={off,curOff};
//                add_pos(waitingpos);
//                selectText = getText().subSequence(off, curOff);
//                System.out.println(selectText);
////                System.out.println(off);
////                System.out.println(curOff);
////                Selection.setSelection(str, off, curOff);
////                str.setSpan(new BackgroundColorSpan(Color.argb(250, 0, 255, 255)), off, curOff, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
////                Selection.removeSelection(str);
//                break;
//            case  MotionEvent.ACTION_UP:
//                System.out.println("you are in action up");
//                line = layout.getLineForVertical(getScrollY()+(int )event.getY());
//                curOff = layout.getOffsetForHorizontal(line, ( int )event.getX());
//
//                if(curOff>off){}
//                else {
//                    int tmp=off;
//                    off=curOff;
//                    curOff=tmp;
//                }
//
////                Spannable str = (Spannable) getEditableText();
////                Selection.removeSelection(str);
//                //判断双击还是单击
//                selectText = getText().subSequence(off, curOff);
//                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
//                mHits[mHits.length - 1] = SystemClock.uptimeMillis();//获取手机开机时间
//                if (mHits[mHits.length - 1] - mHits[0] < 300) {
//                    /**双击的业务逻辑*/
//                    buildindex--;
//                    if(buildindex<0)buildindex=0;
//                    //这里确保它和前一瞬间单击的时候使用的是同样的start和end
//                    //找到双击位置，将该双击位置最近的区间位置置为0
//                    int[]t={off,curOff};
//                    int []index=find_pos(t);
//
//                    clearFocus();
//
//                }else {
//                    int[]t={off,curOff};
//                    int[]index=find_pos(t);
//                    Spannable str=(Spannable) getEditableText();
//                    str.setSpan(new BackgroundColorSpan(Color.argb(200, 80, 50, 255)), index[0], index[1], Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                    buildRelation[buildindex++]=str.toString().subSequence(index[0],index[1]).toString();
//                    if(buildindex==2){
//                        buildindex=0;
//                    }
//
//
//                }
//                break ;
//        }
        return   true ;
    }

    public void add_pos(int []off){
        if(key_num>=2) {
            if (off[0] == keywords[key_num - 2]) {
                keywords[key_num - 1] = off[1];
            } else if (off[1] == keywords[key_num - 1]) {
                keywords[key_num - 2] = off[0];
            } else {
                keywords[key_num++] = off[0];
                keywords[key_num++] = off[1];
            }
        }else{
            keywords[key_num++] = off[0];
            keywords[key_num++] = off[1];
        }
        Spannable str=(Spannable) getEditableText();
        str.setSpan(new BackgroundColorSpan(Color.argb(255, 255, 255, 255)), 0, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        for(int i=0;i<key_num;i=i+2) {
            str.setSpan(new BackgroundColorSpan(Color.argb(250, 0, 255, 255)), keywords[i], keywords[i+1], Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
    }
    public int[] find_pos(int []off){
//        for(int i=0;i<key_num;i++) {
//            System.out.println("keywords: "+keywords[i]);
//        }
        int []index=new int[2];
        index[0]=0;
        index[1]=0;
        //首先找到包含该点击的最大区间
        for (int i=0;i<key_num;i=i+2){
            if((off[0]>=keywords[i]&&off[0]<=keywords[i+1])||(off[1]>=keywords[i]&&off[1]<=keywords[i+1])){
                if(keywords[i+1]-keywords[i]>=index[1]-index[0]) {
                    index[0] = keywords[i];
                    index[1] = keywords[i + 1];
                }
            }
        }
    //    System.out.println("index[0]: "+index[0]);
      //  System.out.println("index[1]: "+index[1]);
        //然后把找到的区间的所有小项都抹去

        for (int i=0;i<key_num;i=i+2){
            if(index[0]<=keywords[i]&&index[1]>=keywords[i+1]){

                for(int j=i;j<key_num;j++){
                    keywords[j]=keywords[j+2];
                }
                key_num=key_num-2;
                i=i-2;
            }
        }

        System.out.println("i just want to: "+key_num/2);
        Spannable str=(Spannable) getEditableText();
        str.setSpan(new BackgroundColorSpan(Color.argb(250, 255, 255, 255)), index[0], index[1], Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return index;
    }
    public int[] getKeywordsindex(){
        preparereturn();
        return keywords;
    }
    public int getkey_num(){
        preparereturn();
        return key_num;
    }
    public void init_keywords(){

    }
    public void preparereturn()
    {for(int i=0;i<key_num;i=i+2){
        if(keywords[i]==keywords[i+1]){
            key_num=key_num-2;
            for(int j=i;j<key_num;j++){
                keywords[j]=keywords[j+2];
            }
            i=i-2;
        }
    }

    }
}
