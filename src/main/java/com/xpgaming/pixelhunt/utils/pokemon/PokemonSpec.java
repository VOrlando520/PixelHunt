package com.xpgaming.pixelhunt.utils.pokemon;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Gender;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.xpgaming.pixelhunt.utils.pokemon.requirement.Requirement;

import java.util.List;
import java.util.Objects;

public class PokemonSpec {

    private EnumSpecies species = null;
    private boolean allowEvolutions = false;
    private Gender gender = null;
    private List<EnumNature> natures = Lists.newArrayList();
    private List<EnumGrowth> growths = Lists.newArrayList();
    private Requirement<Integer> ivRequirement = null;

    public PokemonSpec() {}

    public void setSpecies(EnumSpecies species) {
        this.species = species;
    }

    public void setAllowEvolutions(boolean allowEvolutions) {
        this.allowEvolutions = allowEvolutions;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void addNature(EnumNature nature) {
        this.natures.add(nature);
    }

    public void addGrowth(EnumGrowth growth) {
        this.growths.add(growth);
    }

    public void setIVRequirement(Requirement<Integer> requirement) {
        this.ivRequirement = requirement;
    }

    public boolean matches(Pokemon pokemon) {
        if (!this.doesSpeciesMatch(pokemon)) {
            return false;
        }

        if (this.gender != null && !Objects.equals(this.gender, pokemon.getGender())) {
            return false;
        }

        if (!this.doesSpeciesMatch(pokemon)) {
            return false;
        }

        if (!this.doesNatureMatch(pokemon)) {
            return false;
        }

        return this.ivRequirement != null && this.ivRequirement.fits((int) pokemon.getIVs().getPercentage(1));
    }

    private boolean doesSpeciesMatch(Pokemon pokemon) {
        if (this.species == null) {
            return true;
        }

        if (this.allowEvolutions) {
            if (!Objects.equals(this.species, pokemon.getSpecies())) {
                for (EnumSpecies preEvolution : pokemon.getBaseStats().preEvolutions) {
                    if (Objects.equals(preEvolution, pokemon.getSpecies())) {
                        return true;
                    }
                }
            }
        }

        return Objects.equals(this.species, pokemon.getSpecies());
    }

    private boolean doesNatureMatch(Pokemon pokemon) {
        if (this.natures.isEmpty()) {
            return true;
        }

        return this.natures.contains(pokemon.getNature());
    }

    private boolean doesGrowthMatch(Pokemon pokemon) {
        if (this.growths.isEmpty()) {
            return true;
        }

        return this.growths.contains(pokemon.getGrowth());
    }

    public boolean matches(EntityPixelmon pixelmon) {
        return this.matches(pixelmon.getPokemonData());
    }
}
