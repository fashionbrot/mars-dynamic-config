package com.github.fashionbrot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Test {

    public static void main(String[] args) throws Exception {
        //请求路径
        String toUrl="http://192.168.1.15:8081/open/data/dynamic/version?envCode=beta&appCode=app&version=0";
        URL url=new URL(toUrl);
        //建立连接
        URLConnection urlConnection = url.openConnection();
        //连接对象类型转换
        HttpURLConnection httpURLConnection=(HttpURLConnection)urlConnection;
        //设定POST请求方法
        httpURLConnection.setRequestMethod("POST");
        //开启post请求的输出功能
        httpURLConnection.setDoOutput(true);
        //获取输出流对象,并写数据
        httpURLConnection.getOutputStream().write("txtUser=admin&txtPassword=123456".getBytes());
        //获取字节输入流信息
        InputStream inputStream = httpURLConnection.getInputStream();
        //字节流转换成字符流
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"gb2312");
        //把字符流写入到字符流缓存中
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String  line=null;
        //读取缓存中的数据
        while((line=bufferedReader.readLine())!=null){
            System.out.println(line);
        }
        //关闭流
        inputStream.close();


    }
}
