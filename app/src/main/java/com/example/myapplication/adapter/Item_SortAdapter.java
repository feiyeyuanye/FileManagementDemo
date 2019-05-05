package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.fragment.DirFragment;

public class Item_SortAdapter extends RecyclerView.Adapter<DirFragment.SortVH> {

    public static final String[] names = {
            "从Z到A",
            "从大到小",
            "从新到旧",
            "按类型降",
            "从A到Z",
            "从小到大",
            "从旧到新",
            "按类型升"
    };

    public static final int[] imgs =
            {
                    R.mipmap.sort_name_desc,
                    R.mipmap.sort_size_desc,
                    R.mipmap.sort_time_desc,
                    R.mipmap.sort_type_desc,
                    R.mipmap.sort_name_asc,
                    R.mipmap.sort_size_asc,
                    R.mipmap.sort_time_asc,
                    R.mipmap.sort_type_asc,

            };


    private final Context mContext;

    public Item_SortAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public DirFragment.SortVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = View.inflate(mContext, android.R.layout.simple_list_item_1, null);
        DirFragment.SortVH vh = new DirFragment.SortVH(layout);
        return vh;
    }

    @Override
    public void onBindViewHolder(DirFragment.SortVH holder, final int position) {
        holder.tv.setText(names[position]);
        //设置左上右下的图标
        holder.tv.setCompoundDrawablesWithIntrinsicBounds(0, imgs[position], 0, 0);
        holder.tv.setGravity(Gravity.CENTER);
        holder.tv.setPadding(5, 5, 5, 5);
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }
}
