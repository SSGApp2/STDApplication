package com.soft.app.util;


import com.fasterxml.uuid.Generators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class UUIDUtils {
    private static final Logger LOGGER = LogManager.getLogger(UUIDUtils.class);

    public static String timeBasedGenerator() {
        UUID uuid = Generators.timeBasedGenerator().generate();
        LOGGER.debug("UUID : " + uuid);
        LOGGER.debug("UUID Version : " + uuid.version());
        LOGGER.debug("UUID Timestamp : " + uuid.timestamp());
        return uuid.toString();
    }
}
