package cn.edu.bistu.cs.se.wordbookapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XZL on 2017/11/18.
 */

public class ListsFragment extends Fragment{
    private List<String> data=new ArrayList<String>();
    private JSONanalyse jsoNanalyse;
    private EditText editText;
    private String word;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        data.add("1");
        View view=inflater.inflate(R.layout.fragment_list,container,false);
        ListView listView=view.findViewById(R.id.list_view);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(ListsFragment.this.getContext(),android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        Button bSearch=(Button)view.findViewById(R.id.bSearch);
        TransLate transLate=new TransLate();
        editText=(EditText)view.findViewById(R.id.bText);
        editText.addTextChangedListener(transLate);
        bSearch.setOnClickListener(transLate);
        /*FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_layout, new ListsFragment());
        transaction.commit();*/
        return view;
    }

    public class TransLate implements View.OnClickListener,TextWatcher{
        private String appKey="2c426e04d350e9be";
        private String password="M2ToiczAETY5lUiRTBb9dshF0IkgrkkZ";
        private String q;
        private String salt = String.valueOf(System.currentTimeMillis());
        private String sign;
        public TransLate(){
        }
        @Override
        public void onClick(View view) {
            try {
                Runnable network=new Runnable() {
                    @Override
                    public void run() {
                        String s ="" ;
                        sign=md5(appKey+q+salt+password);
                        Log.v("appkey",appKey);
                        Log.v("password",password);
                        Log.v("q",q);
                        Log.v("salt",salt);
                        Log.v("sign",sign);
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
                new Thread(network).start();
                Configuration configuration=getContext().getResources().getConfiguration();
                if(configuration.orientation==Configuration.ORIENTATION_PORTRAIT){
                    TextFragment textFragment=new TextFragment();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("json",jsoNanalyse);
                    Log.v("json",jsoNanalyse.toString());
                    textFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_layout,textFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
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

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            this.q=charSequence.toString();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            this.q=editable.toString();
        }
    }
    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                LayoutInflater inflater=getLayoutInflater();
                View view;
                view = inflater.inflate(R.layout.search,null);
                builder.setView(view);
                builder.setTitle("搜索");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
        }
        return true;
    }*/
}
