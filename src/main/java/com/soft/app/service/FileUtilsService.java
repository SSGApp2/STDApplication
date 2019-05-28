package com.soft.app.service;

import com.soft.app.entity.app.ParameterDetail;
import com.soft.app.repository.ParameterDetailRepository;
import com.soft.app.repository.custom.ParameterDetailRepositoryCustom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileUtilsService {
    private static final Logger LOGGER = LogManager.getLogger(FileUtilsService.class);


    @Autowired
    ParameterDetailRepositoryCustom parameterDetailRepositoryCustom;
    @Autowired
    ParameterDetailRepository parameterDetailRepository;


    public void saveFileModule(String filename, byte[] bytes, String module) throws IOException {
        ParameterDetail parameterDetail = parameterDetailRepository.findByCodeAndHeaderCode(module, "01");
        String fullPath = parameterDetail.getParameterValue1() + filename;
        File path = new File(fullPath);
        FileCopyUtils.copy(bytes, new FileOutputStream(path));
        LOGGER.debug("SaveFile : {}", fullPath);
    }

    public File getFileByModule(String filename, String module) throws IOException {
        ParameterDetail parameterDetail = parameterDetailRepository.findByCodeAndHeaderCode(module, "01");
        String fullPath = parameterDetail.getParameterValue1() + filename;
        File file = new File(fullPath);
        LOGGER.debug("getFile : {}", fullPath);
        return file;
    }

}
