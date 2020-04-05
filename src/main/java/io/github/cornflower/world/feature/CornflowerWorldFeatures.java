/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.world.feature;

import io.github.cornflower.Cornflower;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class CornflowerWorldFeatures {
    public static final StructurePieceType RUIN_PIECE = Registry.register(Registry.STRUCTURE_PIECE, "cornflower:ruin_piece", CornflowerRuinGenerator.Piece::new);
    public static final StructureFeature<DefaultFeatureConfig> RUIN_FEATURE = Registry.register(Registry.FEATURE, "cornflower:ruin_feature", new CornflowerRuinFeature());
    public static final StructureFeature<DefaultFeatureConfig> RUIN_STRUCTURE = Registry.register(Registry.STRUCTURE_FEATURE, "cornflower:ruin_structure", RUIN_FEATURE);

    public static void init() {
        Feature.STRUCTURES.put("cornflower_ruin", RUIN_FEATURE);

        for(Biome biome : Registry.BIOME)
        {
            if(biome.getCategory() == Biome.Category.TAIGA)
            {
                biome.addStructureFeature(RUIN_STRUCTURE.configure(FeatureConfig.DEFAULT));
                biome.addFeature(
                        GenerationStep.Feature.SURFACE_STRUCTURES,
                        RUIN_FEATURE.configure(FeatureConfig.DEFAULT)
                                .createDecoratedFeature(
                                        Decorator.CHANCE_PASSTHROUGH
                                                .configure(new ChanceDecoratorConfig(32))));
            }
        }
    }
}
