package org.helixd2s.yavulkanmod;

import net.fabricmc.api.ModInitializer;
import org.helixd2s.yavulkanmod.alter.header.CreateInfo;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Initializer implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("modid");

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");

        //var instanceCInfo = new CreateInfo.InstanceCreateInfo();

    }

} 