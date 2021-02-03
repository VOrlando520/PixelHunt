package com.xpgaming.pixelhunt.utils;

import com.google.common.collect.Sets;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;

import java.util.Set;

public class PokemonGenerator {

    private final Set<EnumSpecies> blockedTypes;
    private final boolean speciesRequirement;
    private final boolean genderRequirement;
    private final boolean growthRequirement;
    private final int potentialGrowthRequirements;
    private final int potentialNatureRequirements;
    private final boolean allowEvolutions;
    private final boolean ivRequirement;
    private final boolean randomIVGeneration;
    private final float minIVPercentage;
    private final float maxIVPercentage;

    private PokemonGenerator(Set<EnumSpecies> blockedTypes, boolean speciesRequirement, boolean genderRequirement,
                             boolean growthRequirement, int potentialGrowthRequirements, int potentialNatureRequirements,
                             boolean allowEvolutions, boolean ivRequirement, boolean randomIVGeneration,
                             float minIVPercentage, float maxIVPercentage) {
        this.blockedTypes = blockedTypes;
        this.speciesRequirement = speciesRequirement;
        this.genderRequirement = genderRequirement;
        this.growthRequirement = growthRequirement;
        this.potentialGrowthRequirements = potentialGrowthRequirements;
        this.potentialNatureRequirements = potentialNatureRequirements;
        this.allowEvolutions = allowEvolutions;
        this.ivRequirement = ivRequirement;
        this.randomIVGeneration = randomIVGeneration;
        this.minIVPercentage = minIVPercentage;
        this.maxIVPercentage = maxIVPercentage;
    }

    public PokemonSpec generate() {
        return null; //TODO:
    }

    public static PokemonGenerator.Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Set<EnumSpecies> blockedTypes = Sets.newHashSet();
        private boolean speciesRequirement = true;
        private boolean genderRequirement = true;
        private boolean growthRequirement = false;
        private int potentialGrowthRequirements = -1;
        private int potentialNatureRequirements = -1;
        private boolean allowEvolutions = true;
        private boolean randomIVGeneration = false;
        private boolean ivRequirement;
        private float minIVPercentage = 0;
        private float maxIVPercentage = 100;

        protected Builder() {}

        public Builder addBlockedType(EnumSpecies species) {
            this.blockedTypes.add(species);
            return this;
        }

        public Builder setSpeciesRequired(boolean speciesRequirement) {
            this.speciesRequirement = speciesRequirement;
            return this;
        }

        public Builder setGenderRequirement(boolean genderRequirement) {
            this.genderRequirement = genderRequirement;
            return this;
        }

    }
}
