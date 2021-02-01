package com.xpgaming.pixelhunt.task;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class ParticleDisplayTask {

    private final List<EntityPixelmon> huntPokemon = Lists.newArrayList();

    private int currentTick = 0;

    public void addPokemon(EntityPixelmon pixelmon) {
        this.huntPokemon.add(pixelmon);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        ++this.currentTick;

        if (this.currentTick % 10 != 0) {
            return;
        }

        //TODO: play particles
    }

}
