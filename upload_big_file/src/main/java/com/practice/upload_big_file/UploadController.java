package com.practice.upload_big_file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Scope("prototype")
@RequestMapping(value = "/upload")
public class UploadController {

    /**
     * 上传文件
     *
     * @param request
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping(value = "/uploadFile")
    public Map<String, Object> upload(
            HttpServletRequest request, @RequestParam(value = "data", required = false) MultipartFile multipartFile) {



//获取form中的数据
        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");
        String fileName = request.getParameter("name");
        String size = request.getParameter("size");//总大小
        int total = Integer.valueOf(request.getParameter("total"));//总片数
        int index = Integer.valueOf(request.getParameter("index"));//当前是第几片
        String fileMd5 = request.getParameter("filemd5"); //整个文件的md5
        String date = request.getParameter("date"); //文件第一个分片上传的日期(如:20170122)
        String md5 = request.getParameter("md5"); //分片的md5

        //生成上传文件的路径信息，按天生成
        String savePath = "file" + File.separator + date;
        String saveDirectory = "C:\\Users\\Runge\\Desktop\\"+ savePath + File.separator + uuid;
        //验证路径是否存在，不存在则创建目录
        File path = new File(saveDirectory);
        if (!path.exists()) {
            path.mkdirs();
        }
        //文件分片位置
        File file = new File(saveDirectory, uuid + "_" + index);

        //根据action不同执行不同操作. check:校验分片是否上传过; upload:直接上传分片
        Map<String, Object> map = null;
        if ("check".equals(action)) {
            System.out.println("正在上传--"+index+"  /  "+total);
            String md5Str = FileMd5Util.getFileMD5(file);
            if (md5Str != null && md5Str.length() == 31) {
                System.out.println("check length =" + md5.length() + " md5Str length" + md5Str.length() + "   " + md5 + " " + md5Str);
                md5Str = "0" + md5Str;
            }
            if (md5Str != null && md5Str.equals(md5)) {
                //分片已上传过
                map = new HashMap<>();
                map.put("flag", "2");
                map.put("fileId", uuid);
                map.put("status", true);
                return map;
            } else {
                //分片未上传
                map = new HashMap<>();
                map.put("flag", "1");
                map.put("fileId", uuid);
                map.put("status", true);
                return map;
            }
        } else if ("upload".equals(action)) {
            //分片上传过程中出错,有残余时需删除分块后,重新上传
            if (file.exists()) {
                file.delete();
            }
            try {
                multipartFile.transferTo(new File(saveDirectory, uuid + "_" + index));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("文件转换失败");
            }
        }

        if (path.isDirectory()) {
            File[] fileArray = path.listFiles();
            if (fileArray != null) {
                if (fileArray.length == total) {
                    //分块全部上传完毕,合并
                    String suffix = NameUtil.getExtensionName(fileName);

                    File newFile = new File("C:\\Users\\Runge\\Desktop\\", uuid + "." + suffix);
                    FileOutputStream outputStream = null;//文件追加写入
                    try {
                        outputStream = new FileOutputStream(newFile, true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        System.out.println(newFile.getName() + "文件不存在");
                    }
                    byte[] byt = new byte[10 * 1024 * 1024];
                    int len;

                    FileInputStream temp = null;//分片文件
                    for (int i = 0; i < total; i++) {
                        int j = i + 1;
                        try {
                            temp = new FileInputStream(new File(saveDirectory, uuid + "_" + j));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            while ((len = temp.read(byt)) != -1) {
                                outputStream.write(byt, 0, len);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                    //关闭流
                    try {
                        temp.close();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //修改FileRes记录为上传成功
//                    Example example = new Example(FileRes.class);
//                    Example.Criteria criteria = example.createCriteria();
//                    criteria.andEqualTo("md5", fileMd5);
//                    FileRes fileRes = new FileRes();
//                    fileRes.setStatus(1);
//                    fileResService.updateByExampleSelective(fileRes, example);
                    for (FileRes fileRes: FileDatabase.fileData) {
                        if (fileRes.getMd5().equals(fileMd5)){
                            fileRes.setStatus(1);
                        }
                    }

                } else if (index == 1) {
                    //文件第一个分片上传时记录到数据库
                    FileRes fileRes = new FileRes();
                    String name = NameUtil.getFileNameNoEx(fileName);
                    if (name.length() > 50) {
                        name = name.substring(0, 50);
                    }
                    fileRes.setName(name);
                    fileRes.setSuffix(NameUtil.getExtensionName(fileName));
                    fileRes.setUuid(uuid);
                    fileRes.setPath(savePath + File.separator + uuid + "." + fileRes.getSuffix());
                    fileRes.setSize(Integer.parseInt(size));
                    fileRes.setMd5(fileMd5);
                    fileRes.setStatus(0);
                    fileRes.setCreateTime(new Date());
                    FileDatabase.fileData.add(fileRes);
                }
            }
        }

        map = new HashMap<>();
        map.put("flag", "3");
        map.put("fileId", uuid);
        map.put("status", true);
        return map;
    }

    /**
     * 上传文件前校验
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/isUpload")
    public Map<String, Object> isUpload(HttpServletRequest request) throws Exception {

        String md5 = request.getParameter("md5");

//        Example example = new Example(FileRes.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("md5", md5);
//        List<FileRes> list = fileResService.selectByExample(example);
        boolean judge=true;
        FileRes fileRes;
        for (FileRes file:FileDatabase.fileData) {
            if (file.getMd5()==md5){
                judge=false;
                fileRes=file;
            }
        }
        fileRes=null;
        Map<String, Object> map = null;
        if (judge) {
            //没有上传过文件
            String uuid = UUID.randomUUID().toString();
            map = new HashMap<>();
            map.put("flag", "1");
            map.put("fileId", uuid);
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
            Date date = new Date();
            String strDate = sdf.format(date);
            map.put("date", strDate);
            map.put("status", true);
        } else {

            //求文件上传日期
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
            Date date = new Date();
            String strDate = sdf.format(date);
            if (fileRes.getStatus() == 0) {
                //文件上传部分
                map = new HashMap<>();
                map.put("flag", "2");
                map.put("fileId", fileRes.getUuid());
                map.put("date", strDate);
                map.put("status", true);
            } else if (fileRes.getStatus() == 1) {
                //文件上传成功
                map = new HashMap<>();
                map.put("flag", "3");
                map.put("fileId", fileRes.getUuid());
                map.put("date", strDate);
                map.put("status", true);
            }

        }

        return map;
    }


}
