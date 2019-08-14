package com.practice.upload_big_file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class FileDatabase {
    static Collection<FileRes> fileData=Collections.synchronizedCollection(new ArrayList<>());

}
