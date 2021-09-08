package com.tabwu.changgou.file.utils;

import java.io.Serializable;

public class FdsFileInfo implements Serializable {
    private String filename;
    private String extendName;
    private byte[] content;
    private String author;
    private String md5;

    private String url;
    private String[] group_remotePath;

    public FdsFileInfo(String filename, String extendName, byte[] content, String author, String md5) {
        this.filename = filename;
        this.extendName = extendName;
        this.content = content;
        this.author = author;
        this.md5 = md5;
    }

    public FdsFileInfo(String filename, String extendName, byte[] content) {
        this.filename = filename;
        this.extendName = extendName;
        this.content = content;
    }

    public FdsFileInfo(String url, String[] group_remotePath) {
        this.url = url;
        this.group_remotePath = group_remotePath;

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExtendName() {
        return extendName;
    }

    public void setExtendName(String extendName) {
        this.extendName = extendName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getGroup_remotePath() {
        return group_remotePath;
    }

    public void setGroup_remotePath(String[] group_remotePath) {
        this.group_remotePath = group_remotePath;
    }
}
