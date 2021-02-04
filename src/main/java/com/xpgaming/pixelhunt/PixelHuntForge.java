package com.xpgaming.pixelhunt;

import com.envyful.acaf.api.CommandFactory;
import com.envyful.acaf.impl.ForgeCommandFactory;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.xpgaming.pixelhunt.commands.PixelHuntCommand;
import com.xpgaming.pixelhunt.config.PixelHuntConfig;
import com.xpgaming.pixelhunt.hunt.PixelHuntFactory;
import com.xpgaming.pixelhunt.listener.PokemonCaptureListener;
import com.xpgaming.pixelhunt.listener.PokemonSpawnListener;
import com.xpgaming.pixelhunt.task.ParticleDisplayTask;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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
    private final CommandFactory commandFactory = new ForgeCommandFactory();

    private PixelHuntConfig config;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        instance = this;
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        server = event.getServer();
        this.checkAndCreateConfig();
        config = PixelHuntConfig.getInstance();
        PixelHuntFactory.init(PixelHuntConfig.getConfigNode());

        MinecraftForge.EVENT_BUS.register(this.displayTask);
        MinecraftForge.EVENT_BUS.register(new PokemonSpawnListener(this));
        Pixelmon.EVENT_BUS.register(new PokemonCaptureListener(this));

        this.commandFactory.registerCommand(server, new PixelHuntCommand());
    }

    private void checkAndCreateConfig() {
        if (!PixelHuntConfig.CONFIG_PATH.toFile().exists()) {
            try {
                PixelHuntConfig.CONFIG_PATH.getParent().toFile().mkdir();
                Files.createFile(PixelHuntConfig.CONFIG_PATH);

                InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("");
                byte[] buffer = new byte[resourceAsStream.available()];
                resourceAsStream.read(buffer);

                Files.write(PixelHuntConfig.CONFIG_PATH, buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
