package com.xpgaming.pixelhunt.hunt;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.spongepowered.configurate.ConfigurationNode;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PixelHuntFactory {

    private static final Map<String, PixelHunt> LOADED_HUNTS = Maps.newHashMap();

    public static void init(ConfigurationNode config) {
        //TODO:
    }

    public static List<PixelHunt> getAllHunts() {
        return Collections.unmodifiableList(Lists.newArrayList(LOADED_HUNTS.values()));
    }

    public static PixelHunt getHunt(String identifier) {
        return LOADED_HUNTS.get(identifier);
    }
}
