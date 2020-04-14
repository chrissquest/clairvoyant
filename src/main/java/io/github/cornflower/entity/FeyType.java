/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.entity;

public enum FeyType {
    NONE,
    TRANSPORT_ITEM,
    TRANSPORT_ANIMAL;

    public FeyType shift() {
        // Depending on what this currently is, return the next mode
        if (this == NONE) return TRANSPORT_ITEM;
        else if (this == TRANSPORT_ITEM) return TRANSPORT_ANIMAL;
        else return NONE;
    }
}
