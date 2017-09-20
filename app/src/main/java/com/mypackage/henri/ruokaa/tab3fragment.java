package com.mypackage.henri.ruokaa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Henri on 20.9.2017.
 */

public class tab3fragment extends Fragment {
    private static final String TAG = "tab3fragment";
    TextView txt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment, container, false);
        txt = (TextView)view.findViewById(R.id.tab3Txt);
        return view;
    }
}
