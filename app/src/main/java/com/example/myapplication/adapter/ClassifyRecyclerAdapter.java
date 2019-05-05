package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.bean.FileClassify;
import com.example.myapplication.fragment.ClassifyFragment;

import java.util.List;

public class ClassifyRecyclerAdapter extends RecyclerView.Adapter<ClassifyFragment.VH> {

    private final Context mContext;
    private final List<FileClassify> mList;

    public ClassifyRecyclerAdapter(Context context, List<FileClassify> list) {
        this.mList = list;
        this.mContext = context;
    }


    @Override
    public ClassifyFragment.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = View.inflate(mContext, R.layout.item_recy_classify, null);
        ClassifyFragment.VH vh = new ClassifyFragment.VH(layout);
        return vh;
    }

    @Override
    public void onBindViewHolder(ClassifyFragment.VH holder, int position) {
        holder.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
