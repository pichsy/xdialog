package com.pichs.xdialog.base;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

public class ViewHolder {

    private SparseArray<View> views;
    private View convertView;

    private ViewHolder(View view) {
        convertView = view;
        views = new SparseArray<>();
    }

    public static ViewHolder create(View view) {
        return new ViewHolder(view);
    }

    public <T extends View> T findViewById(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    public void setText(int viewId, CharSequence text) {
        TextView textView = findViewById(viewId);
        textView.setText(text);
    }

    public void setText(int viewId, int textId) {
        TextView textView = findViewById(viewId);
        textView.setText(textId);
    }

    public void setTextColor(int viewId, int colorId) {
        TextView textView = findViewById(viewId);
        textView.setTextColor(colorId);
    }

    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        View view = findViewById(viewId);
        view.setOnClickListener(clickListener);
    }

    public void setBackground(int viewId, Drawable drawable) {
        View view = findViewById(viewId);
        view.setBackground(drawable);
    }

    public void setBackgroundResource(int viewId, int resId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);
    }

    public void setBackgroundColor(int viewId, int colorId) {
        View view = findViewById(viewId);
        view.setBackgroundColor(colorId);
    }
}
