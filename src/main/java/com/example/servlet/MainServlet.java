package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

///  w.s.c.ServletWebServerApplicationContext ?


@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Path baseDir = Paths.get(System.getProperty("user.home")).toAbsolutePath();
        String path = req.getParameter("path");
        if (path == null || path.trim().isEmpty()) {
            path = baseDir.toAbsolutePath().toString();
        }
        Path normilizedPath = Paths.get(path).normalize();
        if (!normilizedPath.startsWith(baseDir)) {
            path = baseDir.toAbsolutePath().toString();
        }
        File currentDir = new File(path);

        if (!currentDir.exists() || !currentDir.isDirectory()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Directory not found: " + path);
            return;
        }
        String parentPath =  null;
        if (!path.equals(baseDir.toAbsolutePath().toString())) {
            parentPath =  currentDir.getParent();
        }
        String encodedParentPath = parentPath != null ?
                URLEncoder.encode(parentPath, StandardCharsets.UTF_8) : null;

        File[] filesArray = currentDir.listFiles();
        List<FileInfo> fileList = new ArrayList<>();

        if (filesArray != null) {
            Arrays.sort(filesArray, (f1, f2) -> {
                if (f1.isDirectory() && !f2.isDirectory()) {
                    return -1;
                } else if (!f1.isDirectory() && f2.isDirectory()) {
                    return 1;
                } else {
                    return f1.getName().compareToIgnoreCase(f2.getName());
                }
            });
            for (File file : filesArray) {
                fileList.add(new FileInfo(file));
            }
        }

        req.setAttribute("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        req.setAttribute("currentPath", currentDir.getAbsolutePath());
        req.setAttribute("parentPath", parentPath);
        req.setAttribute("encodedParentPath", encodedParentPath);
        req.setAttribute("fileList", fileList);
        req.getRequestDispatcher("mypage.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doPost(req, resp);
    }
}