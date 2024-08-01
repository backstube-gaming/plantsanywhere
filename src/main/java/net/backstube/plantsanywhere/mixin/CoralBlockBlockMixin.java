package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.CoralBlockBlock;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CoralBlockBlock.class)
public abstract class CoralBlockBlockMixin {
    @Inject(at = @At("RETURN"), method = "getPlacementState", cancellable = true)
    public void plantsanywhere$getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        var state = cir.getReturnValue();
        if (state != null && state.contains(BlockProperties.VANILLA_PLACING)) {
            cir.setReturnValue(state.with(BlockProperties.VANILLA_PLACING, false));
        }
    }
}
