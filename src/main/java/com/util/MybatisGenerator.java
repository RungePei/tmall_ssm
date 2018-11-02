package com.util;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MybatisGenerator {
    public static void gen() throws Exception {
        List<String> warnings=new ArrayList<>();
        boolean overwrite=true;
        InputStream is=MybatisGenerator.class.getClassLoader().getResource("generatorConfig.xml").openStream();
        ConfigurationParser cp=new ConfigurationParser(warnings);
        Configuration config=cp.parseConfiguration(is);
        is.close();
        DefaultShellCallback callback=new DefaultShellCallback(overwrite);
        MyBatisGenerator mybatisGenerator=new MyBatisGenerator(config,callback,warnings);
        mybatisGenerator.generate(null);
        System.out.println("生成成功");
    }

    public static void main(String[] args) throws Exception {
//        gen();
    }
}
