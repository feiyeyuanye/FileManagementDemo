package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.fragment.DirFragment;

import java.io.File;
import java.util.List;

public class DirRecyclerAdapter extends RecyclerView.Adapter<DirFragment.VH> {

    boolean isMove = false;
    /**
     * 在使用一个Integer作为索引的时候，值为基础数据类型，更加优化
     * 他就是一个指定了K,V的hashmap
     */
    SparseBooleanArray booleanArray = new SparseBooleanArray();


    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    public SparseBooleanArray getBooleanArray() {
        return booleanArray;
    }

    public void setBooleanArray(SparseBooleanArray booleanArray) {
        this.booleanArray = booleanArray;
    }

    public Context mContext;
    public List<File> mList;

    public void booleanClear() {
        booleanArray.clear();
        if (onItemSelectedListener != null)
            onItemSelectedListener.onItemSelected(false);
    }


    public DirRecyclerAdapter(Context mContext, List<File> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public DirFragment.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = View.inflate(mContext, R.layout.item_pathdir, null);
        DirFragment.VH vh = new DirFragment.VH(layout);
        return vh;
    }


    @Override
    public void onBindViewHolder(final DirFragment.VH holder, final int position) {
        holder.setData(mList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position);
            }
        });
        if (!isMove) {
            holder.cb.setVisibility(View.VISIBLE);
            holder.cb.setChecked(booleanArray.get(position));
            holder.cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    booleanArray.put(position, !booleanArray.get(position));
                    if (onItemSelectedListener != null) {
                        for (int i = 0; i < mList.size(); i++) {
                            if (booleanArray.get(i)) {
                                if (!state)
                                    onItemSelectedListener.onItemSelected(true);
                                state = true;
                                return;
                            }
                        }
                        if (state)
                            onItemSelectedListener.onItemSelected(false);
                        state = false;
                    }
                }
            });
        } else {
            holder.cb
                    .setVisibility(View.INVISIBLE);
        }
    }

    boolean state = false;

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    OnItemSelectedListener onItemSelectedListener;

    public interface OnItemSelectedListener {
        public void onItemSelected(boolean b);
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
