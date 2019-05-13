package com.soft.app.controller;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Controller
@RequestMapping("api/common")
public class CommonFileController {
    private static final Logger LOGGER = LogManager.getLogger(CommonFileController.class);

//    @GetMapping("image")
//    public void urlImage(HttpServletRequest request, HttpServletResponse response
//            , @RequestParam(value = "moduleCode", required = false) String moduleCode
//            , @RequestParam(value = "fileName", required = false) String fileName
//    ) {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType(MediaType.IMAGE_PNG_VALUE);
//        OutputStream outputStream = null;
//        InputStream inputStream = null;
//        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
//        try {
//
//            if (file.exists() && !file.isDirectory()) {
//                switch (BeanUtils.getFileExtension(file)) {
//                    case "png":
//                        response.setContentType(MediaType.IMAGE_PNG_VALUE);
//                        break;
//                    case "jpeg":
//                        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//                        break;
//                    case "jpg":
//                        response.setContentType(MediaType.IMAGE_PNG_VALUE);
//                        break;
//                    default:
//                        response.setContentType(mimetypesFileTypeMap.getContentType(fileName)) break;
//
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error("error {}", e.getMessage(), e);
//        }
//    }

    @GetMapping(value = "/image", produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getImage( @RequestParam(value = "fileName", required = false) String fileName) throws IOException {
        String fullPath = "/home/thongchai/Pictures/" + fileName;
        File file = new File(fullPath);
        URL url = file.toURI().toURL();
        InputStream in = url.openStream();
        return IOUtils.toByteArray(in);
    }
}
