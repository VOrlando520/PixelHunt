package com.xpgaming.pixelhunt;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.xpgaming.pixelhunt.listener.PokemonCaptureListener;
import com.xpgaming.pixelhunt.task.ParticleDisplayTask;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
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

    private final ParticleDisplayTask displayTask = new ParticleDisplayTask();

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        instance = this;
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        server = event.getServer();

        MinecraftForge.EVENT_BUS.register(this.displayTask);
        Pixelmon.EVENT_BUS.register(new PokemonCaptureListener(this));
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

    public ParticleDisplayTask getDisplayTask() {
        return this.displayTask;
    }
}
