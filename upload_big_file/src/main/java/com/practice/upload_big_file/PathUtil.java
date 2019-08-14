package com.practice.upload_big_file;

import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

public class PathUtil {
    public static String  getAppRootPath(HttpServletRequest request){
        //ServletActionContext.getServletContext().getRealPath("/")+"upload";
        String path=request.getSession().getServletContext().getRealPath("/");
        System.out.println(path);
        return path;
    }

    /**
     *自定义文件保存路径
     * @param request
     */
    public static String  getCustomFilePath(HttpServletRequest request) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath();
        System.out.println(path);

        return path;
    }

    /**
     *
     * @param request
     * @return http://www.qh.com:8080/projectName
     */
    public static String  getHttpURL(HttpServletRequest request) {
        StringBuffer buff = new StringBuffer();
        buff.append("http://");
        buff.append(request.getServerName());
        buff.append(":");
        buff.append(request.getServerPort());
        buff.append(request.getContextPath());
        return buff.toString();
    }
}
