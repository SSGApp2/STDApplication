package com.soft.app.controller;

import com.soft.app.service.FileUtilsService;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Controller
@RequestMapping("api/common")
public class CommonFileController {
    private static final Logger LOGGER = LogManager.getLogger(CommonFileController.class);

    @Autowired
    FileUtilsService fileUtilsService;

    //Preview Image
    @GetMapping(value = "imageByModule", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ResponseBody
    public byte[] getImage(@RequestParam(value = "fileName") String fileName,
                           @RequestParam(value = "module") String module
    ) throws IOException {
        File file = fileUtilsService.getFileByModule(fileName, module);
        URL url = file.toURI().toURL();
        InputStream in = url.openStream();
        return IOUtils.toByteArray(in);
    }

    //Preview Image
    @GetMapping(value = "image")
    public void testImage(@RequestParam(value = "fileName") String fileName,
                            @RequestParam(value = "module") String module,
                            HttpServletResponse response
    ) throws IOException {
        File file = fileUtilsService.getFileByModule(fileName, module);
        URL url = file.toURI().toURL();
        InputStream in = url.openStream();
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getName();
        byte[] bytes = multipartFile.getBytes();
        String pathFile = "/home/thongchai/Pictures/images/";
        if (!multipartFile.isEmpty()) {
            File file = new File(pathFile + fileName);
        }
        return "success";
    }

}
