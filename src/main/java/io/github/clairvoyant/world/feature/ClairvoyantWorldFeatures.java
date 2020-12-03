/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.world.feature;

import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;

public class ClairvoyantWorldFeatures {
    public static final StructurePieceType RUIN_PIECE = Registry.register(Registry.STRUCTURE_PIECE, "clairvoyant:ruin_piece", ClairvoyantRuinGenerator.Piece::new);
    //public static final StructureFeature<DefaultFeatureConfig> RUIN_STRUCTURE = Registry.register(Registry.STRUCTURE_FEATURE, "clairvoyant:ruin_structure", new ClairvoyantRuinFeature());
    //public static final StructureFeature<DefaultFeatureConfig> RUIN_FEATURE = Registry.register(Registry.FEATURE, "clairvoyant:ruin_feature", RUIN_STRUCTURE);

    /*
    public static void init() {

        Feature.STRUCTURES.put("clairvoyant_ruin", RUIN_FEATURE);


        for (Biome biome : Registry.BIOME) {
            if (biome.getCategory() == Biome.Category.TAIGA) {
                biome.addStructureFeature(RUIN_STRUCTURE.configure(FeatureConfig.DEFAULT));
            }
        }
    }

    public static ConfiguredFeaturne<?, ?> getConfiguredFeature(StructureFeature<DefaultFeatureConfig> feature) {
        return feature.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT));
    }
    */
}
