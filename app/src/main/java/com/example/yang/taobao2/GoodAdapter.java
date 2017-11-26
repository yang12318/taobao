package com.example.yang.taobao2;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder>{

    private static final String TAG = "goodadapter";

    private Context mContext;

    private List<Good> mGoodList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView goodImage;
        TextView goodName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            goodImage = (ImageView) view.findViewById(R.id.good_image);
            goodName = (TextView) view.findViewById(R.id.good_name);
        }
    }

    public GoodAdapter(List<Good> goodList) {
        mGoodList = goodList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.gooditem, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Good Good = mGoodList.get(position);
        holder.goodName.setText(Good.getName());
        Glide.with(mContext).load(Good.getImageId()).into(holder.goodImage);
    }

    @Override
    public int getItemCount() {
        return mGoodList.size();
    }

}
