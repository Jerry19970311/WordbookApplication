package cn.edu.bistu.cs.se.wordbookapplication;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by XZL on 2017/11/22.
 */

public class TextFragment extends Fragment{
    private TextView textView;
    private Bundle bundle;
    private String jsoNanalyse;
    private String jsonS="";
    private Handler handler=new Handler();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_text,container,false);
        textView = view.findViewById(R.id.result);
        Configuration configuration=getContext().getResources().getConfiguration();
            try {
                bundle = getArguments();
                jsoNanalyse = bundle.getString("json");
                if(!jsoNanalyse.toString().equals("找不到该单词！")){
                }
                textView.setText(jsoNanalyse.toString());
            }catch (NullPointerException e){
                textView.setText("");
            }
            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Configuration configuration=getContext().getResources().getConfiguration();
                    if(configuration.orientation==Configuration.ORIENTATION_PORTRAIT) {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.remove(TextFragment.this);
                        transaction.commit();
                    }
                    return false;
                }
            });
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Configuration configuration=getResources().getConfiguration();
        if(configuration.orientation==Configuration.ORIENTATION_PORTRAIT) {
            inflater.inflate(R.menu.main, menu);
        }
    }
}
