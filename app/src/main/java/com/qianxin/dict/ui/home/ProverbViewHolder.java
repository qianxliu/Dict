package com.qianxin.dict.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qianxin.dict.R;
import com.qianxin.dict.db.entity.Proverb;

class ProverbViewHolder extends RecyclerView.ViewHolder {
    private final TextView wordItemView;

    private ProverbViewHolder(View itemView) {
        super(itemView);
        wordItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        wordItemView.setText(text);
    }

    static ProverbViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_proverb, parent, false);
        return new ProverbViewHolder(view);
    }
}