package com.xpgaming.pixelhunt.hunt.impl;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.xpgaming.pixelhunt.hunt.PixelHunt;
import com.xpgaming.pixelhunt.utils.UtilServer;
import com.xpgaming.pixelhunt.utils.item.UtilItemStack;
import com.xpgaming.pixelhunt.utils.math.UtilRandom;
import com.xpgaming.pixelhunt.utils.pokemon.PokemonGenerator;
import com.xpgaming.pixelhunt.utils.pokemon.PokemonSpec;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;

public class SimplePixelHunt implements PixelHunt {

    private final String identifier;
    private final List<String> rewardCommands = Lists.newArrayList();

    private  PokemonGenerator generator;
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
        this.currentPokemon = this.generator.generate();
        this.displayItem = this.currentPokemon.getPhoto();
        UtilItemStack.setLore(this.displayItem, this.generator.getDisplayDescription());
        return this.currentPokemon;
    }

    @Override
    public boolean isBeingHunted(Pokemon pokemon) {
        return this.currentPokemon.matches(pokemon);
    }

    @Override
    public void rewardCatch(EntityPlayerMP player, Pokemon caught) {
        if (this.randomCommands) {
            UtilServer.executeCommand(UtilRandom.getRandomElement(this.rewardCommands).replace("%player%", player.getName()));
        } else {
            for (String rewardCommand : this.rewardCommands) {
                UtilServer.executeCommand(rewardCommand.replace("%player%", player.getName()));
            }
        }
    }
}
