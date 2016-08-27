package com.gashfara.it.gsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private TextView title_tv;

    public static TextFragment newInstance(int position) {
        TextFragment fragment = new TextFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public void setText(String s) {
        title_tv.setText(s);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("txt", title_tv.getText().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        int page = getArguments().getInt(ARG_POSITION, 0);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        title_tv = (TextView) view.findViewById(R.id.page_text);
        if (savedInstanceState != null) {
            String str = savedInstanceState.getString("txt");
            if (str != null) {
                title_tv.setText(str);
            }
        }

        return view;
    }
}
