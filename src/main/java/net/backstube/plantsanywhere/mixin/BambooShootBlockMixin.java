package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.BambooShootBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BambooShootBlock.class)
public abstract class BambooShootBlockMixin {

    // allow bamboo placement anywhere
    @Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
    public void plantsanywhere$canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (BlockProperties.allowAnywhere(state))
            cir.setReturnValue(true);
    }
}
