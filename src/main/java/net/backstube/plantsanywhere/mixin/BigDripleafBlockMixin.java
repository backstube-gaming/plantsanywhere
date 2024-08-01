package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.BigDripleafBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BigDripleafBlock.class)
public abstract class BigDripleafBlockMixin {

    private BigDripleafBlock getThis() {
        return (BigDripleafBlock) (Object) this;
    }

    @Inject(at = @At("HEAD"), method = "appendProperties")
    protected void plantsanywhere$bigdripleaf_appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(BlockProperties.VANILLA_PLACING);
    }

    @Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
    public void plantsanywhere$canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (BlockProperties.allowAnywhere(state)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "getPlacementState", cancellable = true)
    public void plantsanywhere$getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        var state = getThis().getDefaultState();
        if (state.contains(BlockProperties.VANILLA_PLACING)) {
            cir.setReturnValue(state.with(BlockProperties.VANILLA_PLACING, false));
        }
    }
}
