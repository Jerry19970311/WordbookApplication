package cn.edu.bistu.cs.se.wordbookapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by XZL on 2017/11/22.
 */

public class TextFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_text,container,false);
        Bundle bundle=getArguments();
        JSONanalyse jsoNanalyse=(JSONanalyse)bundle.getSerializable("json");
        TextView textView=view.findViewById(R.id.result);
        textView.setText(jsoNanalyse.toString());
        Log.v("jsonS",jsoNanalyse.toString());
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
    }
}
