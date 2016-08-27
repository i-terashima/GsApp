package com.gashfara.it.gsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Fragment_library extends android.support.v4.app.ListFragment {

    private ArrayAdapter<ListItem> adapter;
    private List<ListItem> list;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<ListItem>();
        for (int i = 0; i < 90; i++) {
            ListItem item = new ListItem();
            item.set("No" + i);
            list.add(item);
        }
        adapter = new ListAdapter(getActivity(), list);
        setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                ListItem item = (ListItem) listView.getItemAtPosition(position);
                Toast.makeText(getActivity(), item.get(), Toast.LENGTH_SHORT).show();
                if (getActivity() instanceof OnSampleListChangeListener) {
                    OnSampleListChangeListener listener = (OnSampleListChangeListener) getActivity();
                    listener.onListSelectedChanged(item.get());
                }
            }
        });
    }


    private class ListAdapter extends ArrayAdapter<ListItem> {
        private LayoutInflater mInflater;

        public ListAdapter(Context context, List<ListItem> objects) {
            super(context, 0, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            //キャッシュ
            final ViewHolder holder;

            //初めて
            if (convertView == null) {

                //動的にレイアウトファイルを利用
                convertView = mInflater.inflate(R.layout.list_row, parent, false);

                //キャッシュ
                holder = new ViewHolder();
                holder.tvListText = (TextView) convertView.findViewById(R.id.list_text);

                //次回からViewHolderを利用
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final ListItem item = getItem(position);
            holder.tvListText.setText(item.get());
            return convertView;
        }
    }

    //キャッシュ
    private class ViewHolder {
        TextView tvListText;
    }

    private class ListItem {
        private String listText = "";

        public void set(String listText) {
            this.listText = listText;
        }

        public String get() {
            return this.listText;
        }
    }
}
