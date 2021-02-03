package com.xpgaming.pixelhunt.utils.pokemon.requirement;

public interface Requirement<T> {

    boolean fits(T data);

}
