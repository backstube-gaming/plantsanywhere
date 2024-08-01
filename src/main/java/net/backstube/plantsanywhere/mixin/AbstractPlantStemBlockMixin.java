package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractPlantStemBlock.class)
public abstract class AbstractPlantStemBlockMixin {

    @Inject(at = @At("HEAD"), method = "appendProperties")
    protected void plantsanywhere$abstractstem_appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(BlockProperties.VANILLA_PLACING);
    }
}
