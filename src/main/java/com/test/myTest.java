package com.test;

import org.junit.Test;

import java.util.Arrays;

public class myTest {
    @Test
    public void test() {
        String[] arr = new String[]{
                "aaa", "bbb", "ccc", "abd"};
        boolean has= Arrays.asList(arr).contains("aaa");
        System.out.println(has);
    }
}
