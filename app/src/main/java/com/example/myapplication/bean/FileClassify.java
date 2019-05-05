package com.example.myapplication.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileClassify {

    String type;

    int mipmapId;

    int conut;

    boolean isNew;

    List<File> files = new ArrayList<>();


    public List<File> getFiles() {
        return files;
    }

    public FileClassify(String type, int mipmapId, int conut, boolean isNew, List<File> files) {
        this.type = type;
        this.mipmapId = mipmapId;
        this.conut = conut;
        this.isNew = isNew;
        this.files = files;
    }

    public FileClassify(String type, int mipmapId, int conut, boolean isNew) {
        this.type = type;
        this.mipmapId = mipmapId;
        this.conut = conut;
        this.isNew = isNew;
    }

    public FileClassify() {
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMipmapId() {
        return mipmapId;
    }

    public void setMipmapId(int mipmapId) {
        this.mipmapId = mipmapId;
    }

    public int getConut() {
        return conut;
    }

    public void setConut(int conut) {
        this.conut = conut;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
