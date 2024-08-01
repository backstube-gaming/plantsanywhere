package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.PitcherCropBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PitcherCropBlock.class)
public abstract class PitcherCropBlockMixin {
    @Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
    public void plantsanywhere$canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (BlockProperties.allowAnywhere(state))
            cir.setReturnValue(true);
    }

    @Inject(at = @At("RETURN"), method = "getPlacementState", cancellable = true)
    public void plantsanywhere$getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        var state = cir.getReturnValue();
        if (state.contains(BlockProperties.VANILLA_PLACING)) {
            cir.setReturnValue(state.with(BlockProperties.VANILLA_PLACING, false));
        }
    }

    @Inject(at = @At("HEAD"), method = "canPlantOnTop", cancellable = true)
    public void plantsanywhere$canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        // to check for already planted blocks
        var actualBlock = world.getBlockState(pos.up());
        if (BlockProperties.allowAnywhere(actualBlock)) {
            cir.setReturnValue(true);
        }
    }
}
