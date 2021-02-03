package com.xpgaming.pixelhunt.utils.pokemon.requirement.impl;

import com.xpgaming.pixelhunt.utils.math.UtilRandom;

public class RandomIntegerRequirement extends SingleIntegerRequirement {

    public RandomIntegerRequirement(int min, int max) {
        super(UtilRandom.getRandomInteger(min, max));
    }
}
