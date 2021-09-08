package com.tabwu.changgou.file.utils;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.omg.CORBA.NameValuePair;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class FdsUtil {

    static {
        try {
            String configPath = new ClassPathResource("fds_client.conf").getPath();
            ClientGlobal.init(configPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] upload(FdsFileInfo file) {
        String[] strings = null;
        try {
            strings = getStorageClient().upload_file(file.getContent(), file.getExtendName(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * strings[0]   文件上传所存储的组名，例如:group1
         * string[1]   文件存储路径,例如：M00/00/00/wKjThF0DBzaAP23MAAXz2mMp9oM26.jpeg
         */
        return strings;
    }


    public static void delete(String group, String filePath) {
        try {
            getStorageClient().delete_file(group, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static InputStream dowmload(String group, String filePath) {
        InputStream inputStream = null;
        try {
            byte[] bytes = getStorageClient().download_file(group, filePath);
            inputStream = new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }


    public static String getTrackerUrl() {
        try {
            return "http://"+getTrackerServer().getInetSocketAddress().getHostString()+":"+ClientGlobal.getG_tracker_http_port();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static TrackerServer getTrackerServer() {
        try {
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            return trackerServer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static StorageClient getStorageClient() {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }

}
