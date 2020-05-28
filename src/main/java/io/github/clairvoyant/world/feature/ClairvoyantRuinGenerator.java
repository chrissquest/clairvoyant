/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.world.feature;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.List;
import java.util.Random;

public class ClairvoyantRuinGenerator {
    public static final Identifier id = new Identifier("clairvoyant:ruin");

    public static void addParts(StructureManager structureManager, BlockPos blockPos, BlockRotation rotation, List<StructurePiece> pieces, DefaultFeatureConfig defaultFeatureConfig) {
        pieces.add(new ClairvoyantRuinGenerator.Piece(structureManager, id, blockPos, rotation));
    }

    public static class Piece extends SimpleStructurePiece {
        private final BlockRotation rotation;
        private final Identifier template;

        public Piece(StructureManager structureManager_1, CompoundTag compoundTag_1) {
            super(ClairvoyantWorldFeatures.RUIN_PIECE, compoundTag_1);

            this.template = new Identifier(compoundTag_1.getString("Template"));
            this.rotation = BlockRotation.valueOf(compoundTag_1.getString("Rot"));

            this.setStructureData(structureManager_1);
        }

        public Piece(StructureManager structureManager, Identifier template, BlockPos pos, BlockRotation rotation) {
            super(ClairvoyantWorldFeatures.RUIN_PIECE, 0);

            this.rotation = rotation;
            this.template = template;
            this.pos = pos;

            this.setStructureData(structureManager);
        }

        @Override
        protected void toNbt(CompoundTag compoundTag_1) {
            super.toNbt(compoundTag_1);
            compoundTag_1.putString("Template", this.template.toString());
            compoundTag_1.putString("Rot", this.rotation.name());
        }

        public void setStructureData(StructureManager structureManager) {
            Structure structure_1 = structureManager.getStructureOrBlank(this.template);
            StructurePlacementData structurePlacementData_1 = (new StructurePlacementData()).setRotation(this.rotation).setMirrored(BlockMirror.NONE).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            this.setStructureData(structure_1, this.pos, structurePlacementData_1);
        }

        @Override
        protected void handleMetadata(String s, BlockPos blockPos, IWorld iWorld, Random random, BlockBox blockBox) {
        }

        @Override
        public boolean generate(IWorld world, ChunkGenerator<?> generator, Random rand, BlockBox box, ChunkPos pos) {
            int yHeight = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, this.pos.getX(), this.pos.getZ());
            BlockPos oldPos = this.pos;
            this.pos = this.pos.add(0, yHeight - 1, 0);
            boolean result = super.generate(world, generator, rand, box, pos);
            this.pos = oldPos;
            return result;
        }
    }
}
