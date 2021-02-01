package com.xpgaming.pixelhunt;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(
        modid = PixelHuntForge.MOD_ID,
        name = PixelHuntForge.MOD_NAME,
        version = PixelHuntForge.MOD_VERSION,
        acceptableRemoteVersions = "*",
        dependencies = "required-after:pixelmon;required-after:gooeylibs"
)
public class PixelHuntForge {

    public static final String MOD_ID = "pixelhunt";
    public static final String MOD_NAME = "PixelHunt Forge";
    public static final String MOD_VERSION = "1.12.2-forge-1.0.1";

    private static PixelHuntForge instance;
    private static MinecraftServer server;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        instance = this;
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        server = event.getServer();
    }

    @Mod.EventHandler
    public void onServerShutdown(FMLServerStoppingEvent event) {

    }

    public static final PixelHuntForge getInstance() {
        return instance;
    }

    public static final MinecraftServer getServer() {
        return server;
    }
}
