package com.sherlochao.util;

import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Sherlock_chao on 2016/11/30.
 */
public class BASE64DecodedMultipartFileUtils implements MultipartFile{

    private final byte[] imgContent;

    public BASE64DecodedMultipartFileUtils(byte[] imgContent)
    {
        this.imgContent = imgContent;
    }

    @Override
    public String getName()
    {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public String getOriginalFilename()
    {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public String getContentType()
    {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public boolean isEmpty()
    {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize()
    {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException
    {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException
    {
        new FileOutputStream(dest).write(imgContent);
    }

    static final String success = "success";
    static final String result = "result";
    static final String fileName = "filename";

//    public static void main(String args[]) throws Exception{
////        String str = "最远的距离";
////        byte[] x = str.getBytes();
////        String a = new BASE64Encoder().encodeBuffer(x);
////        byte[] y = new BASE64Decoder().decodeBuffer(a);
////        String b = new String (y);
////        System.out.println("编码前字节数组："+x);
////        System.out.println("解码后字节数组："+y);
////        System.out.println("编码前字符串："+str);
////        System.out.println("编码后字符串："+a);
////        System.out.println("解码后字符串："+b);
//        String strImg = GetImageStr();
//        System.out.println(strImg);
//        GenerateImage(strImg);
//
//    }

//    public static void main(String args[]) throws IOException {
//        //List<String> list = new ArrayList<>();
//       // String s = GetImageStr();
//        //System.out.println(s);
//		String str1 = GetImageStr("/Users/Sherlock_chao/1480392927120.png");
//		String str2 = GetImageStr("/Users/Sherlock_chao/2013303.png");
//		System.out.println(str1);
//		System.out.println(str2);
//		String[] photo = new String[]{str1, str2};
//		Map<String, Object> map = Base64Image(photo,"/Users/Sherlock_chao","/soft");
//		if ("true".equals(map.get("success") + "")) {
//			System.out.println("success");;
//		}
//
//
//    }

    //图片转化成base64字符串
    public static String GetImageStr(String imgFile)
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        //String imgFile = "/Users/Sherlock_chao/1480392927120.png";//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    //base64字符串转化成图片
    public static boolean GenerateImage(String imgStr)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "/Users/Sherlock_chao/1480392927121.png";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    //base64字符串转化成图片
    public static Map<String,Object> Base64Image(String imgStr,
                                                 String targetDir, String imgDir)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            Map<String,Object> map = Maps.newHashMap();
            String originalFilename ;
            map.put(success,false);
            // 文件保存目录路径
            //String savePath = targetDir;
            // 文件保存目录URL
            //File uploadDir = new File(savePath);
            //mkdir(targetDir + imgDir);
            originalFilename = String.valueOf(new DateTime().getMillis() + UUID.randomUUID().toString()+ ".png");
            //System.out.println(originalFilename);
            //生成文件
            OutputStream out = new FileOutputStream(targetDir + imgDir + "/" + originalFilename);
            out.write(b);
            out.flush();
            out.close();
            map.put(result, imgDir + "/" + originalFilename);
            map.put(fileName , originalFilename);
            map.put(success, true);
            return map;
        } catch (Exception e){
            return null;
        }
    }

    public static Map<String,Object> Base64Image(String[] photos,
                                                 String targetDir,String imgDir) throws IOException {
        Map<String,Object> map = Maps.newHashMap();
        String originalFilename = "";
        try {
            for(String photo : photos){
                Map<String,Object> map1 = Base64Image(photo, targetDir, imgDir);
                if("true".equals(map1.get(success).toString())){
                    String imgPath = map1.get(result).toString();
                    originalFilename += imgPath + ",";
                }
            }
            map.put(success, true); // 成功或者失败
            if(originalFilename.length()>0){
                map.put(result, originalFilename.substring(0, originalFilename.length() - 1)); // 上传成功的所有的图片地址的路径
            }else{
                map.put(result, originalFilename); // 上传成功的所有的图片地址的路径
            }
        } catch (NullPointerException e){
            map.put(success,false);
            map.put(result, "上传失败");
            e.printStackTrace();
        }

        return map;
    }

}

