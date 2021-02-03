package com.xpgaming.pixelhunt.utils.pokemon.requirement.impl;

import com.xpgaming.pixelhunt.utils.pokemon.requirement.Requirement;

public class SingleIntegerRequirement implements Requirement<Integer> {

    private final int requirement;

    public SingleIntegerRequirement(int requirement) {
        this.requirement = requirement;
    }


    @Override
    public boolean fits(Integer data) {
        if (data == null) {
            return false;
        }

        return data == this.requirement;
    }
}
