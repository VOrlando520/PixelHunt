package com.xpgaming.pixelhunt.hunt.impl;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.xpgaming.pixelhunt.hunt.PixelHunt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;

public class SimplePixelHunt implements PixelHunt {

    private final String identifier;
    private final List<String> rewardCommands = Lists.newArrayList();

    private ItemStack displayItem;
    private PokemonSpec currentPokemon;
    private boolean randomCommands;

    public SimplePixelHunt(String identifier, ConfigurationNode node) {
        this.identifier = identifier;

        this.load(node);
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public void load(ConfigurationNode config) {
        ConfigurationNode configNode = config.node(this.identifier);

        this.randomCommands = configNode.node("random-reward-commands").getBoolean(false);

        try {
            this.rewardCommands.addAll(configNode.node("reward-commands").getList(String.class));
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ItemStack getDisplay() {
        return this.displayItem;
    }

    @Override
    public PokemonSpec generatePokemon() {
        return null; //TODO:
    }

    @Override
    public boolean isBeingHunted(Pokemon pokemon) {
        return this.currentPokemon.matches(pokemon);
    }

    @Override
    public void rewardCatch(EntityPlayerMP player, Pokemon caught) {
        //TODO:
    }
}
