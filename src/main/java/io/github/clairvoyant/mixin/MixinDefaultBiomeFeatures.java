/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.mixin;

import io.github.clairvoyant.world.feature.ClairvoyantWorldFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


/*
@Mixin(MixinDefaultBiomeFeatures.class)
public class MixinDefaultBiomeFeatures {

    @Inject(at = @At("RETURN"), method = "addDefaultStructures")
    private static void addDefaultStructures(Biome biome, CallbackInfo info) {
        biome.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, ClairvoyantWorldFeatures.getConfiguredFeature(ClairvoyantWorldFeatures.RUIN_STRUCTURE));
    }

}
*/
