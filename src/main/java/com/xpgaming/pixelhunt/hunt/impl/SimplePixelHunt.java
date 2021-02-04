package com.xpgaming.pixelhunt.hunt.impl;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.xpgaming.pixelhunt.hunt.PixelHunt;
import com.xpgaming.pixelhunt.utils.UtilServer;
import com.xpgaming.pixelhunt.utils.math.UtilRandom;
import com.xpgaming.pixelhunt.utils.pokemon.PokemonGenerator;
import com.xpgaming.pixelhunt.utils.pokemon.PokemonSpec;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Collections;
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
        this.randomCommands = config.node("random-reward-commands").getBoolean(false);
        this.rewardCommands.addAll(this.getStringList(config, "reward-commands"));

        PokemonGenerator.Builder builder = PokemonGenerator.builder();

        builder.setSpeciesRequired(config.node("require-species").getBoolean())
                .setAllowLegends(config.node("allow-legends").getBoolean())
                .setAllowUltraBeasts(config.node("allow-ultra-beasts").getBoolean())
                .setAllowEvolutions(config.node("allow-evolutions").getBoolean())
                .setGenderRequirement(config.node("require-gender").getBoolean())
                .setGrowthRequirement(config.node("require-growth").getBoolean())
                .setPotentialRequiredGrowths(config.node("number-of-possible-growths").getInt())
                .setNatureRequirement(config.node("require-nature").getBoolean())
                .setPotentialNatureRequirements(config.node("number-of-possible-natures").getInt())
                .setIVRequirement(config.node("require-iv-percentage").getBoolean())
                .setRandomIVGeneration(config.node("random-iv-generation").getBoolean());

        if (config.node("random-iv-generation").getBoolean()) {
            builder.setMinimumIVPercentage(config.node("minimum-iv-percentage").getInt())
                    .setMaximumIVPercentage(config.node("maximum-iv-percentage").getInt());
        } else {
            builder.setMinimumIVPercentage(config.node("required-iv-percentage").getInt())
                    .setMaximumIVPercentage(config.node("required-iv-percentage").getInt());
        }


        for (String type : this.getStringList(config, "blocked-types")) {
            builder.addBlockedType(EnumSpecies.getFromNameAnyCase(type));
        }

        this.generator = builder.build();
    }

    private List<String> getStringList(ConfigurationNode node, String... path) {
        try {
            return node.node((Object[]) path).getList(String.class);
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    @Override
    public ItemStack getDisplay() {
        return this.displayItem;
    }

    @Override
    public PokemonSpec generatePokemon() {
        this.currentPokemon = this.generator.generate();
        this.displayItem = this.currentPokemon.getPhoto();
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
