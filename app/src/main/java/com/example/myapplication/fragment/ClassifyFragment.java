package com.example.myapplication.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ClassifyRecyclerAdapter;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.bean.FileClassify;
import com.example.myapplication.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassifyFragment extends BaseFragment {

    RecyclerView recyclerView;

    ClassifyRecyclerAdapter adapter;

    @Override
    protected View getLayout() {
        return View.inflate(getContext(), R.layout.fragment_classify, null);
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ClassifyRecyclerAdapter(getContext(), classifyList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        //初始化
        recyclerView.setAdapter(adapter);

        //动态权限，这里没做版本判断
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            initData();
        }else{
            //没有权限，向用户请求权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    List<File> mList;
    List<FileClassify> classifyList = new ArrayList<>();
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //用户同意，执行操作
                initData();
            }else{
                //用户不同意，向用户展示该权限作用
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage("SD卡权限")
                            .setPositiveButton("OK", (dialog1, which) ->
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2))
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();
                }
            }
        }
    }

    public void initData() {
        new Thread() {
            @Override
            public void run() {
                mList = FileUtils.getAllFiles();
                        // 这里，如果文件太多，会导致ANR
                        FileClassify music = new FileClassify();
                        music.setType("音乐");
                        music.setMipmapId(R.mipmap.c_icon_audio);
                        FileClassify video = new FileClassify();
                        video.setType("视频");
                        video.setMipmapId(R.mipmap.c_icon_video);
                        FileClassify picture = new FileClassify();
                        picture.setType("图片");
                        picture.setMipmapId(R.mipmap.c_icon_image);
                        FileClassify text = new FileClassify();
                        text.setType("文档");
                        text.setMipmapId(R.mipmap.c_icon_document);
                        FileClassify zip = new FileClassify();
                        zip.setType("压缩包");
                        zip.setMipmapId(R.mipmap.c_icon_archive);
                        FileClassify apk = new FileClassify();
                        apk.setType("安装包");
                        apk.setMipmapId(R.mipmap.c_icon_apk);
                        FileClassify recently = new FileClassify();
                        recently.setType("最近下载");
                        recently.setMipmapId(R.mipmap.c_icon_download);
                        FileClassify look = new FileClassify();
                        look.setType("最近浏览");
                        look.setMipmapId(R.mipmap.c_icon_recent);
                        FileClassify app = new FileClassify();
                        app.setType("应用文件");
                        app.setMipmapId(R.mipmap.c_icon_appfile);
                        int length = mList.size();
                        for (int i=0;i<length;i++) {
                            File file = mList.get(i);
                            //是否音乐
                            String name = file.getName();
                            if (name.endsWith(".mp3") || name.endsWith(".mid") || name.endsWith(".wav")) {
                                music.getFiles().add(file);
                                music.setConut(music.getConut() + 1);
                            } else if (name.endsWith(".mp4") || name.endsWith(".avi") || name.endsWith(".rmvb")) {
                                video.getFiles().add(file);
                                video.setConut(video.getConut() + 1);
                            } else if (name.endsWith(".png") || name.endsWith(".jpg")) {
                                picture.getFiles().add(file);
                                picture.setConut(picture.getConut() + 1);
                            } else if (name.endsWith(".txt")) {
                                text.getFiles().add(file);
                                text.setConut(text.getConut() + 1);
                            } else if (name.endsWith(".rar") || name.endsWith(".zip")) {
                                zip.getFiles().add(file);
                                zip.setConut(zip.getConut() + 1);
                            } else if (name.endsWith(".apk")) {
                                apk.getFiles().add(file);
                                apk.setConut(apk.getConut() + 1);
                            }
                            if (file.getAbsolutePath().startsWith(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/")) {
                                recently.getFiles().add(file);
                                recently.setConut(recently.getConut() + 1);
                            }
                        }
                        classifyList.add(music);
                        classifyList.add(video);
                        classifyList.add(picture);
                        classifyList.add(text);
                        classifyList.add(zip);
                        classifyList.add(apk);
                        classifyList.add(recently);
                        classifyList.add(look);
                        classifyList.add(app);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
            }
        }.start();
    }

    public static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView point;
        TextView type;
        TextView count;

        public VH(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            point = (ImageView) itemView.findViewById(R.id.point);
            type = (TextView) itemView.findViewById(R.id.type);
            count = (TextView) itemView.findViewById(R.id.count);
        }


        public void setData(FileClassify fc) {
            img.setImageResource(fc.getMipmapId());
            type.setText(fc.getType());
            if (fc.isNew())
                point.setVisibility(View.VISIBLE);
            else
                point.setVisibility(View.GONE);
            count.setText("(" + fc.getConut() + ")");
        }
    }
}
