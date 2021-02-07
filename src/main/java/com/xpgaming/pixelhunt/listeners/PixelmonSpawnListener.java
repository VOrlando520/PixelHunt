package com.xpgaming.pixelhunt.listeners;


import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.util.Scheduling;
import com.xpgaming.pixelhunt.utils.Utils;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;

public class PixelmonSpawnListener {

	@SubscribeEvent
	public void onPixelmonSpawn (EntityJoinWorldEvent event){
		final Entity entity = (Entity) event.getEntity();
		if(!(entity instanceof EntityPixelmon)) {
			return;
		}

		EntityPixelmon pixelmon = (EntityPixelmon) entity;

		if(pixelmon.hasOwner()){
			return;
		}

		int isInHunt = Utils.getInstance().isInHunt(pixelmon.getPokemonData());
		if (isInHunt != 0) {
			Scheduling.schedule(10, task -> {
				if (pixelmon.isEntityAlive() && !pixelmon.hasOwner() && pixelmon.getPokemonData().getPixelmonIfExists() != null) {
					entity.getWorld().spawnParticles(ParticleEffect.builder().type(ParticleTypes.MOBSPAWNER_FLAMES).build(), entity.getLocation().getPosition(), 5);
				} else {
					task.repeats = false;
				}
			}, true);
		}
	}
}
