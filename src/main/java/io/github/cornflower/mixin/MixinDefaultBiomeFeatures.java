package io.github.cornflower.mixin;

import io.github.cornflower.world.feature.CornflowerWorldFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(DefaultBiomeFeatures.class)
public class MixinDefaultBiomeFeatures {
    @Inject(at = @At("RETURN"), method = "addDefaultStructures")
    private static void addDefaultStructures(Biome biome, CallbackInfo info) {
        biome.addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, CornflowerWorldFeatures.getConfiguredFeature(CornflowerWorldFeatures.RUIN_STRUCTURE));
    }
}
