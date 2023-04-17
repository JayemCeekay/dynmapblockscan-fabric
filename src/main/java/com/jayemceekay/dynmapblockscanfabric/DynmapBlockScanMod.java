package com.jayemceekay.dynmapblockscanfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.ForgeConfigAPIPort;
import net.minecraftforge.api.fml.event.config.ModConfigEvents;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.configured.ForgeConfigHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FileUtils;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class DynmapBlockScanMod implements ModInitializer {

    public static DynmapBlockScanPlugin.OurLog logger = new DynmapBlockScanPlugin.OurLog();
    // The instance of your mod that Forge uses.
    public static DynmapBlockScanMod instance;

    // Says where the client and server 'proxy' code is loaded.
    public static Proxy proxy = new Proxy();

    public static DynmapBlockScanPlugin plugin;
    public static File jarfile;


    @Override
    public void onInitialize() {
        FileUtils.getOrCreateDirectory(FabricLoader.getInstance().getConfigDir().resolve("dynmapblockscan-fabric"), "dynmapblockscan-fabric");
        // ModConfig config = new ModConfig(ModConfig.Type.COMMON, SettingsConfig.SPEC,FabricLoader.getInstance().getModContainer("dynmapblockscan-fabric").get());
        //ForgecoregisterConfig(net.minecraftforge.fml.config.  "dynmapblockscan/settings.toml" );
        logger.info("setup");

        jarfile = FabricLoader.getInstance().getGameDir().resolve("mods/"+FabricLoader.getInstance().getModContainer("dynmapblockscan-fabric").get().toString()+".jar").toFile();
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            if (plugin == null)
                plugin = proxy.startServer(server);
            plugin.setDisabledModules((List<String>) SettingsConfig.excludeModules.get());
            plugin.setDisabledBlockNames((List<String>) SettingsConfig.excludeBlockNames.get());
            plugin.serverStarting();
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            proxy.stopServer(plugin);
            plugin = null;
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            plugin.serverStarted();
        });
    }

    private MinecraftServer server;

}

