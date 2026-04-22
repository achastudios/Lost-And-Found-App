package com.example.lostandfoundapp;

// These imports are needed for custom list rows
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

// This adapter displays custom rows in the list screen
public class ItemListAdapter extends ArrayAdapter<LostFoundItem> {

    // This stores the row data
    Context context;
    List<LostFoundItem> items;

    // This constructor sets the context and data
    public ItemListAdapter(Context context, List<LostFoundItem> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // This inflates the row layout if needed
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        }

        // These lines connect row views to XML ids
        TextView tvRowTitle = convertView.findViewById(R.id.tvRowTitle);
        TextView tvRowSubtitle = convertView.findViewById(R.id.tvRowSubtitle);

        // This gets the current item
        LostFoundItem item = items.get(position);

        // These lines fill the row content
        tvRowTitle.setText(item.postType + " " + item.name);
        tvRowSubtitle.setText(item.location + " • " + item.date);

        return convertView;
    }
}