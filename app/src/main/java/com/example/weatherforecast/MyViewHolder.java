package com.example.weatherforecast;

import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder{
    private TextView mTextView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        //change size of item
        mTextView=itemView.findViewById(R.id.hello);
    }
    public TextView getmTextView() {
        return mTextView;
    }
    public void setmTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }
}
