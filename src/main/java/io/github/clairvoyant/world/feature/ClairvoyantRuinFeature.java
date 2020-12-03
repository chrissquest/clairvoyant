/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ClairvoyantRuinFeature extends StructureFeature<DefaultFeatureConfig> {

    public ClairvoyantRuinFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return ClairvoyantRuinFeature.Start::new;
    }

    @Override
    public String getName() {
        return "clairvoyant_ruin";
    }

    public static class Start extends StructureStart<DefaultFeatureConfig> {

        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int chunkX, int chunkZ, BlockBox box, int references, long l) {
            super(structureFeature, chunkX, chunkZ, box, references, l);
        }

        public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, int chunkX, int chunkZ, Biome biome, DefaultFeatureConfig config) {
            //DefaultFeatureConfig defaultFeatureConfig = chunkGenerator.getStructureConfig(biome, ClairvoyantWorldFeatures.RUIN_FEATURE);
            int x = chunkX * 16;
            int z = chunkZ * 16;
            BlockPos startingPos = new BlockPos(x, 0, z);
            BlockRotation rotation = BlockRotation.values()[this.random.nextInt(BlockRotation.values().length)];
            //ClairvoyantRuinGenerator.addParts(manager, startingPos, rotation, this.children, defaultFeatureConfig);
            this.setBoundingBoxFromChildren();
        }
    }
}
