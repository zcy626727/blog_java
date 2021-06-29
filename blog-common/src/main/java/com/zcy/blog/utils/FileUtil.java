package com.zcy.blog.utils;


import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;


public class FileUtil {

    private static String port = "8000";

    private static String articleAvatarPath = "img/articleAvatar";

    private static String articleIllustrationPath = "img/Illustration";

    private static String classpath;

    static {
        try {
            classpath = ResourceUtils.getURL("classpath:").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取项目绝对路径 /F:/mynote/blog/back/target/test-classes/
     * @return
     */
    public static String getBasePath(){
        return classpath;
    }


    /**
     * 获取本地ip地址 192.168.228.1
     * @return
     */
    public static String getIP() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = address.getHostAddress();//返回IP地址
        return ip;
    }


    /**
     * 获取服务器端口号 8000
     * @return
     */
    public static String getPort() {
        return port;
    }

    /**
     * 获取 ip地址:端口号 192.168.228.1:8000/
     * @return
     */
    public static String getBaseUrl(){
        return getIP()+":"+getPort()+"/";
    }


    /**
     * 获取文章封面文件url http://192.168.228.1:8000/img/articleAvatar/
     * @return
     */
    public static String getArticleAvatarUrl(){
        return "http://"+getBaseUrl()+articleAvatarPath+"/";
    }

    /**
     * 获取文章插图url
     * @return
     */
    public static String getArticleIllustrationUrl(){
        return "http://"+getBaseUrl()+articleIllustrationPath+"/";
    }

    /**
     * 获取文章封面保存路径 /F:/mynote/blog/back/target/test-classes/static/img/articleAvatar/
     * @return
     */
    public static String getArticleAvatarPath(){
        return getBasePath()+"static/"+articleAvatarPath+"/";
    }

    /**
     * 获取图片插图保存路径
     * @return
     */
    public static String getArticleIllustrationPath(){
        return getBasePath()+"static/"+articleIllustrationPath+"/";
    }


    /**
     * 删除文章封面
     * @param fileName
     * @return
     */
    public static boolean deleteArticleAvatar(String fileName){
        File file = new File(FileUtil.getArticleAvatarPath(),fileName);
        if (file.exists()) {//文件是否存在
            if (file.delete()){
                return true;
            }
        }
        return false;
    }

    public static boolean deleteArticleIllustration(String fileName){
        File file = new File(FileUtil.getArticleIllustrationPath(),fileName);
        if (file.exists()) {//文件是否存在
            if (file.delete()){
                return true;
            }
        }
        return false;
    }


    /**
     * 保存文章封面
     * @param picture
     * @return
     */
    public static String saveArticleAvatar(MultipartFile picture){
        //获取文件在服务器的储存位置
        String path = getArticleAvatarPath();
        return savePicture(picture,path);
    }

    /**
     * 保存文章插图
     * @param picture
     * @return
     */
    public static String saveArticleIllustration(MultipartFile picture){
        //获取文件在服务器的储存位置
        String path = getArticleIllustrationPath();
        return savePicture(picture,path);
    }

    /**
     * 保存图片方法
     * @param picture
     * @param path
     * @return
     */
    public static String savePicture(MultipartFile picture,String path){
        File filePath = new File(path);

        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

        String fileName = UUID.randomUUID() + "." + type;

        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);
        //将文件保存到服务器指定位置
        try {
            //保存图片
            picture.transferTo(targetFile);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
