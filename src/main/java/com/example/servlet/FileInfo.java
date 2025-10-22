package com.example.servlet;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class FileInfo {
    private final String name;
    private final String path;
    private final boolean directory;
    private final long size;
    private final String displaySize;

    public FileInfo(File file) {
        this.name = file.getName();
        this.path = URLEncoder.encode(file.getAbsolutePath(), StandardCharsets.UTF_8);
        this.directory = file.isDirectory();
        this.size = file.length();
        this.displaySize = formatSize(size);
    }

    private String formatSize(long size) {
        if (directory) {
            return "";
        }
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.1f Kb", size / 1024.0);
        } else {
            return String.format("%.1f Mb", size / (1024.0 * 1024.0));
        }
    }
    public String getName() {
        return name;
    }
    public String getPath() {
        return path;
    }
    public boolean isDirectory() {
        return directory;
    }
    public long getSize() {
        return size;
    }
    public String getDisplaySize() {
        return displaySize;
    }
}