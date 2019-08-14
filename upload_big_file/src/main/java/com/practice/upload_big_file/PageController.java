package com.practice.upload_big_file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/up")
    public String up(){
        return "uploadFile";
    }
}
