package cn.edu.bistu.cs.se.wordbookapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.Selection;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by XZL on 2017/11/29.
 */

public class WordActivity extends AppCompatActivity{
    private int off=0;
    private String string="";
    private EditText input;
    private TextView output;
    private JSONanalyse jsoNanalyse;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_word);
        input=(EditText)findViewById(R.id.article_input);
        output=(TextView)findViewById(R.id.article_output);
        input.setOnTouchListener(new WordListener());
    }
    public class WordListener implements View.OnTouchListener{
        private String appKey="2c426e04d350e9be";
        private String password="M2ToiczAETY5lUiRTBb9dshF0IkgrkkZ";
        private String q;
        private String salt = String.valueOf(System.currentTimeMillis());
        private String sign;
        private Runnable network=new Runnable() {
            @Override
            public void run() {
                String s ="" ;
                sign=md5(appKey+q+salt+password);
                String str="http://openapi.youdao.com/api?q="+q+"&from=EN&to=zh_CHS&appKey="+appKey+"&salt="+salt+"&sign="+sign;
                URL url = null;
                try {
                    url = new URL(str);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                BufferedReader bufferedReader;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                    Log.v("test","11111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
                    Log.v("str",str);
                    String temp;
                    while ((temp = bufferedReader.readLine()) != null) {
                        s = s + temp;
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                s = s.replace('-','_');
                Log.v("gson",s);
                Gson gson = new Gson();
                jsoNanalyse=gson.fromJson(s,JSONanalyse.class);
            }
        };
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            Layout layout = input.getLayout();
            int line = 0;
            switch(action) {
                case MotionEvent.ACTION_DOWN:
                    line = layout.getLineForVertical(input.getScrollY()+ (int)motionEvent.getY());
                    off = layout.getOffsetForHorizontal(line, (int)motionEvent.getX());
                    Selection.setSelection(input.getEditableText(), off);
                    Log.v("line",String.valueOf(line));
                    Log.v("off",String.valueOf(off));
                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                    line = layout.getLineForVertical(input.getScrollY()+(int)motionEvent.getY());
                    int curOff = layout.getOffsetForHorizontal(line, (int)motionEvent.getX());
                    Selection.setSelection(input.getEditableText(), off, curOff);
                    Log.v("line",String.valueOf(line));
                    Log.v("off",String.valueOf(off));
                    Log.v("curOff",String.valueOf(curOff));
                    String resultOutput="划的词是:"+string;
                    if(off!=curOff) {
                        string = input.getText().subSequence(off,curOff).toString();
                        Log.v("String", string);
                    }
                    q=string;
                    Thread thread=new Thread(network);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        resultOutput = resultOutput + "\n" + jsoNanalyse.toString();
                        output.setText(resultOutput);
                    }catch (NullPointerException e){
                        output.setText("????????????????????????");
                    }
                    break;
            }
            return false;
        }
        public String md5(String string) {
            if(string == null){
                return null;
            }
            char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F'};
            try{
                byte[] btInput = string.getBytes("utf-8");
                /** 获得MD5摘要算法的 MessageDigest 对象 */
                MessageDigest mdInst = MessageDigest.getInstance("MD5");
                /** 使用指定的字节更新摘要 */
                mdInst.update(btInput);
                /** 获得密文 */
                byte[] md = mdInst.digest();
                /** 把密文转换成十六进制的字符串形式 */
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (byte byte0 : md) {
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
                return null;
            }
        }
    }
}
