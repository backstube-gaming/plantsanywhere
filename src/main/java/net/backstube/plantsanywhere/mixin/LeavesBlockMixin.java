package net.backstube.plantsanywhere.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin {

    // prevent leave decay entirely
    @Inject(at = @At("HEAD"), method = "hasRandomTicks", cancellable = true)
    public void plantsanywhere$hasRandomTicks(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
