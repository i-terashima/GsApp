package com.gashfara.it.gsapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Fragment_stock extends ListFragment {

    private List<Item_stock> list;
    private ArrayAdapter<Item_stock> adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            Item_stock itemstock = new Item_stock();
            itemstock.book = "BookTitle" + (i+1);
            itemstock.page = "Page : " + (i+1);
            itemstock.stock = "引用" + (i+1);
            list.add(itemstock);
        }
        adapter = new ListAdapter(getActivity(), list);
        setListAdapter(adapter);
    }

        private class ListAdapter extends ArrayAdapter<Item_stock> {
            private LayoutInflater mInflater;

            public ListAdapter(Context context, List<Item_stock> objects) {
                super(context, 0, objects);
                mInflater = (LayoutInflater)context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                final ViewHolder holder;
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.card_stock, parent, false);
                    holder = new ViewHolder();
                    holder.bookListText = (TextView)convertView.findViewById(R.id.book);
                    holder.pageListText = (TextView)convertView.findViewById(R.id.page);
                    holder.stockListText = (TextView)convertView.findViewById(R.id.stock);
                                        convertView.setTag(holder);
                } else {
                    holder = (ViewHolder)convertView.getTag();
                }
                final Item_stock item = getItem(position);
                holder.bookListText.setText(item.getBook());
                holder.pageListText.setText(item.getPage());
                holder.stockListText.setText(item.getStock());

                return convertView;
            }
        }
    private class ViewHolder {
        TextView bookListText;
        TextView pageListText;
        TextView stockListText;

    }
}
