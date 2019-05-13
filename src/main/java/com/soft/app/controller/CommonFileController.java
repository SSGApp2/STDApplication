package com.soft.app.controller;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Controller
@RequestMapping("api/common")
public class CommonFileController {
    private static final Logger LOGGER = LogManager.getLogger(CommonFileController.class);

    //Preview Image
    @GetMapping(value = "/image", produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getImage( @RequestParam(value = "fileName", required = false) String fileName) throws IOException {
        String fullPath = "/home/thongchai/Pictures/" + fileName;
        File file = new File(fullPath);
        URL url = file.toURI().toURL();
        InputStream in = url.openStream();
        return IOUtils.toByteArray(in);
    }

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName=multipartFile.getName();
        byte[] bytes = multipartFile.getBytes();
        String pathFile="/home/thongchai/Pictures/images/";
        if (!multipartFile.isEmpty()) {
            File file = new File(pathFile+fileName);
        }
        return "success";
    }

}
