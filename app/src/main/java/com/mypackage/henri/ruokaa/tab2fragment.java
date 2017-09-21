package com.mypackage.henri.ruokaa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Henri on 20.9.2017.
 */

public class tab2fragment extends Fragment {
    private static final String TAG = "tab2fragment";

    ImageButton addFavorite;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment, container, false);
        //VITUT TOIMI
       /* addFavorite = (ImageButton)view.findViewById(R.id.lisaaBtn);

        addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View thisLayout = (ConstraintLayout)view.findViewById(R.id.suosikit);


                TextView text = new TextView(getView().getContext());
                text.setText("NI5UU");
                text.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                ((ConstraintLayout) thisLayout).addView(text);
            }
        });
        */
        return view;
    }
}
