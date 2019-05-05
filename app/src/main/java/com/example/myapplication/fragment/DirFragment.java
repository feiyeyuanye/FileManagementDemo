package com.example.myapplication.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DirRecyclerAdapter;
import com.example.myapplication.adapter.Item_SortAdapter;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirFragment extends BaseFragment implements View.OnClickListener {

    //当前是哪个目录
    String curr_path = Environment.getExternalStorageDirectory().getAbsolutePath();
    List<File> mList;
    @Override
    protected void initView(View view) {
        linear_dir_path = view.findViewById(R.id.linear_dir_path);
        recyclerView = view.findViewById(R.id.recyclerView);
        tv_home = view.findViewById(R.id.tv_home);
        tabbar = view.findViewById(R.id.tabbar);
        tabbar_send = view.findViewById(R.id.tabbar_send);
        tabbar_move = view.findViewById(R.id.tabbar_move);
        tabbar_more = view.findViewById(R.id.tabbar_more);
        tabbar_delete = view.findViewById(R.id.tabbar_delete);
        movebar = view.findViewById(R.id.movebar);
        tv_up = view.findViewById(R.id.tv_up);
        tab_new = view.findViewById(R.id.tab_new);
        move_new = view.findViewById(R.id.move_new);
        tab_sort = view.findViewById(R.id.tab_sort);
        tab_grid = view.findViewById(R.id.tab_grid);
        tab_f5 = view.findViewById(R.id.tab_f5);
        move_cancal = view.findViewById(R.id.move_cancal);
        move_move = view.findViewById(R.id.move_move);

        tv_home.setOnClickListener(this);
        tv_up.setOnClickListener(this);
        tab_new.setOnClickListener(this);
        move_new.setOnClickListener(this);
        tab_sort.setOnClickListener(this);
        tab_grid.setOnClickListener(this);
        tab_f5.setOnClickListener(this);
        tabbar_send.setOnClickListener(this);
        tabbar_move.setOnClickListener(this);
        move_cancal.setOnClickListener(this);
        move_move.setOnClickListener(this);
        tabbar_delete.setOnClickListener(this);
        tabbar_more.setOnClickListener(this);
    }

    LinearLayout linear_dir_path;
    TextView tv_home;
    LinearLayout tabbar;
    TextView move_move,move_cancal,tab_f5,tab_grid,tab_sort,tab_new,move_new,tv_up;
    TextView tabbar_send;
    TextView tabbar_move;
    TextView tabbar_more;
    TextView tabbar_delete;
    List<File> moveList;
    LinearLayout movebar;

    public static final String DIR = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected View getLayout() {
        return View.inflate(getContext(), R.layout.fragment_dir, null);
    }



    RecyclerView recyclerView;

    DirRecyclerAdapter adapter;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mList = FileUtils.getCurrFiles(curr_path);
        adapter = new DirRecyclerAdapter(getActivity(), mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(itemClickListener);
        adapter.setOnItemSelectedListener(itemSelectedListener);
    }

    //判断是否有选中项
    DirRecyclerAdapter.OnItemSelectedListener itemSelectedListener = new DirRecyclerAdapter.OnItemSelectedListener() {

        @Override
        public void onItemSelected(boolean b) {
            if (b) { //true 打勾  in -->
                if (tabbar.getVisibility() == View.INVISIBLE) {
                    tabbar.setVisibility(View.VISIBLE);
                    tabbar.startAnimation(getAnim(true));
                }
            } else {
                if (tabbar.getVisibility() == View.VISIBLE) {
                    Animation anim = getAnim(false);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tabbar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    tabbar.startAnimation(anim);
                }
            }
        }
    };

    public Animation getAnim(boolean b) {
        //%p
        Animation anim = null;
        float height = tabbar.getHeight();
        if (b) {//进场
            anim = new TranslateAnimation(0, 0, height, 0);
            anim.setDuration(500);
        } else {
            //出场
            anim = new TranslateAnimation(0, 0, 0, height);
            anim.setDuration(500);
        }
        return anim;
    }


    private DirRecyclerAdapter.OnItemClickListener itemClickListener = new DirRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            curr_path = mList.get(position).getAbsolutePath();
            addTagView(mList.get(position).getName());
            setDirPath();
            //生成一个TextView并显示
        }
    };

    public void addTagView(String path) {
        TextView tv = new TextView(getContext());
        tv.setText(path);
        tv.setPadding(5, 5, 5, 5);
        //设置Drawable图片
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.applist_right, 0);
        //携带路径
        tv.setTag(curr_path);
        tv.setOnClickListener(pathOnClickListener);
        linear_dir_path.addView(tv);
    }

    //目录的点击事件
    View.OnClickListener pathOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String dir = (String) view.getTag();
            if (curr_path == dir)
                return;
            for (int i = linear_dir_path.getChildCount() - 1; i >= 0; i--) {
                if (linear_dir_path.getChildAt(i).getTag().equals(dir)) {
                    break;
                }
                linear_dir_path.removeViewAt(i);
            }
            curr_path = dir;
            setDirPath();
            System.out.println("-----");
        }
    };

    private void setDirPath() {
        List<File> files = FileUtils.getCurrFiles(curr_path);
        mList.clear();
        mList.addAll(files);
        //清楚check标记
        adapter.booleanClear();
        adapter.notifyDataSetChanged();
    }




    public static class VH extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView time;
        public CheckBox cb;

        public VH(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            cb = (CheckBox) itemView.findViewById(R.id.cb);
            time = (TextView) itemView.findViewById(R.id.time);
        }

        public void setData(File file) {
            int count = 0;
            if (file.isDirectory()) {
                img.setImageResource(R.mipmap.folder);
                //获取子文件有多少个
                count = FileUtils.getCurrFiles(file.getAbsolutePath()).size();
            } else
                img.setImageResource(R.mipmap.hst);
            name.setText(file.getName());
            if (count != 0)
                name.append("(" + count + ")");
            time.setText(FileUtils.getTime(file.lastModified()));
        }
    }

    private void tabNewClick(View v) {
        String[] items = {"文本文件", "文件夹"};
        Dialog dialog = new AlertDialog.Builder(getContext()).setTitle("请选择")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            //跳转
                        } else {
                            final EditText et = new EditText(getContext());
                            et.setHint("请输入文件夹的名称");
                            new AlertDialog.Builder(getContext())
                                    .setTitle("文件夹名称")
                                    .setView(et)
                                    .setNegativeButton("取消", null)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            String name = et.getText().toString();
                                            if (name.equals(""))
                                                Toast.makeText(getContext(), "文件夹名称错误", Toast.LENGTH_SHORT).show();
                                            String dir = curr_path + "/" + name;
                                            if (new File(dir).mkdir()) {
                                                Toast.makeText(getContext(), "创建成功", Toast.LENGTH_SHORT).show();
                                                setDirPath();
                                            } else {
                                                Toast.makeText(getContext(), "文件夹重复或者非法字符", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).create().show();
                        }
                    }
                }).create();
        dialog.show();
    }

    Dialog dialog = null;

    //排序
    private void tabSortClick(View v) {
        View layout = View.inflate(getContext(), R.layout.item_sort, null);
        RecyclerView rv = (RecyclerView) layout.findViewById(R.id.dialog_rv);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        Item_SortAdapter adapter = new Item_SortAdapter(getContext());
        adapter.setOnItemClickListener(onItemClick);
        rv.setAdapter(adapter);
        dialog = new AlertDialog.Builder(getContext())
                .setTitle("文件排序规则")
                .setView(layout).create();
        dialog.show();
    }

    Item_SortAdapter.OnItemClickListener onItemClick = new Item_SortAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            switch (position) {
                case 0:
                    FileUtils.sortByName(mList, false);
                    break;
                case 1:
                    FileUtils.sortBySize(mList, false);
                    break;
                case 2:
                    FileUtils.sortByTime(mList, false);
                    break;
                case 3:
                    break;
                case 4:
                    FileUtils.sortByName(mList, true);
                    break;
                case 5:
                    FileUtils.sortBySize(mList, true);
                    break;
                case 6:
                    FileUtils.sortByTime(mList, true);
                    break;
                case 7:
                    break;
                    default:
                        break;
            }
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    };

    //图片
    private void tabGridClick(View v) {

    }


    //刷新
    private void tabF5Click(View v) {
        setDirPath();
    }


    public static class SortVH extends RecyclerView.ViewHolder {
        public TextView tv;

        public SortVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView;
        }
    }

    private void tabbar_sendOnClick(View v) {
        List<File> list = getTabFiles();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isDirectory()) {
                showTaost("分享不支持文件夹!");
                return;
            }
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        for (int i = 0; i < list.size(); i++) {
            intent.setDataAndType(Uri.fromFile(list.get(i)), "*/*");
        }
        startActivity(intent);
    }

    @NonNull
    private List<File> getTabFiles() {
        SparseBooleanArray array = adapter.getBooleanArray();
        List<File> list = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if (array.get(i)) {
                list.add(mList.get(i));
            }
        }
        return list;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_up:
                if (curr_path.equals(DIR))
                    return;
                //移除一个布局中的 摸个子项       获取子项数量
                linear_dir_path.removeViewAt(linear_dir_path.getChildCount() - 1);
                curr_path = new File(curr_path).getParentFile().getAbsolutePath();
                setDirPath();
                break;
            case R.id.tv_home:
                if (curr_path == DIR)
                    return;
                curr_path = DIR;
                setDirPath();
                for (int i = linear_dir_path.getChildCount() - 1; i >= 2; i--) {
                    linear_dir_path.removeViewAt(i);
                }
                break;
            case  R.id.tab_new:
                //新建
                tabNewClick(view);
                break;
            case  R.id.move_new:
                //新建
                tabNewClick(view);
                break;
            case  R.id.tab_sort:
                //排序
                tabSortClick(view);
                break;
            case  R.id.tab_grid:
                tabGridClick(view);
                break;
            case  R.id.tab_f5:
                tabF5Click(view);
                break;
            case  R.id.tabbar_send:
                tabbar_sendOnClick(view);
                break;
            case  R.id.tabbar_move:
                tabbar_moveOnClick(view);
                break;
            case  R.id.move_cancal:
                move_cancelOnClick(view);
                break;
                case  R.id.move_move:
                    move_move(view);
                break;
            case  R.id.tabbar_more:
                tabbar_moreOnClick(view);
                break;
            case  R.id.tabbar_delete:
                tabbar_deleteOnClick(view);
                break;
            default:
                break;
        }
    }
    private void move_cancelOnClick(View v) {
        adapter.setMove(false);
        moveList.clear();
        movebar.setVisibility(View.INVISIBLE);
        adapter.notifyDataSetChanged();
    }

    private void move_move(View v) {
        //第一种，重名。
        for (int i = 0; i < moveList.size(); i++) {
            if (moveList.get(i).getParent().equals(curr_path)) {
                showTaost("无须在同一个目录中移动");
                return;
            }
            //粘贴的位置，，curr_path  //C:path/ ---> C:/path/aaa 当前目录
            if (curr_path.contains(moveList.get(i).getAbsolutePath())) {
                showTaost("无法在源文件的下级目录中复制");
                return;
            }
        }
        FileUtils.MoveFileNew(moveList, new File(curr_path));
        setDirPath();
    }
    private void tabbar_moveOnClick(View v) {
        movebar.setVisibility(View.VISIBLE);
        moveList = getTabFiles();
        adapter.setMove(true);
        adapter.notifyDataSetChanged();
    }
    private void tabbar_moreOnClick(View v) {
        PopupWindow window = new PopupWindow(getContext());
        ListView lv = new ListView(getContext());
        String[] array = {"复制", "重命名", "压缩", "详情"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, array);
        window.setContentView(lv);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        if (getTabFiles().size() == 1) {
                            final EditText et = new EditText(getContext());
                            et.setHint("输入新文件名");
                            et.setText(getTabFiles().get(0).getName());
                            new AlertDialog.Builder(getContext()).setTitle("重命名")
                                    .setView(et)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            String name = et.getText().toString();
                                            if (name.equals(""))
                                                showTaost("新文件夹名不能为空");
                                            else {
                                                String path = getTabFiles().get(0).getParent();
                                                getTabFiles().get(0).renameTo(new File(path + "/" + name));
                                                setDirPath();
                                            }
                                        }
                                    }).setNegativeButton("取消", null).create().show();

                        } else {
                            showTaost("重命名只能对一个文件进行操作");
                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        });
        //焦点，布局，宽高
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setWidth(300);
        window.setHeight(500);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        window.showAtLocation(tabbar, Gravity.RIGHT | Gravity.BOTTOM, 0, 0);
    }

    ProgressDialog progressDialog;

    private void tabbar_deleteOnClick(View v) {
        new AlertDialog.Builder(getActivity()).setTitle("删除文件")
                .setMessage("确定要删除吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final List<File> list = getTabFiles();// 三个文件夹
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setCancelable(false);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.setMax(list.size());
                        progressDialog.show();
                        new Thread() {
                            @Override
                            public void run() {
                                for (int j = 0; j < list.size(); j++) {
                                    progressDialog.setProgress(j);
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    FileUtils.deleteFile(list.get(j), new FileUtils.OnFileDeteteListener() {
                                        @Override
                                        public void onFileDetete(final String name) {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressDialog.setTitle("正在删除" + name);
                                                }
                                            });
                                        }
                                    });
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            setDirPath();
                                        }
                                    });
                                    list.get(j).delete();
                                }
                            }
                        }.start();

//                        new Thread() {
//                            @Override
//                            public void run() {
//
//                            }
//                        }.start();
                    }
                }).create().show();
    }
}
