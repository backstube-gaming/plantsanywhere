package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SmallDripleafBlock;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmallDripleafBlock.class)
public abstract class SmallDripleafBlockMixin {

    @Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
    public void plantsanywhere$canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (BlockProperties.allowAnywhere(state)) {
            cir.setReturnValue(true);
        }
    }

    // prevent plant breakage on block updates and allow them to be placed anywhere
    @Inject(at = @At("HEAD"), method = "canPlantOnTop", cancellable = true)
    public void plantsanywhere$canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        // to check for already planted blocks
        var actualBlock = world.getBlockState(pos.up());
        if (BlockProperties.allowAnywhere(actualBlock)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "appendProperties")
    protected void plantsanywhere$smalldripleaf_appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(BlockProperties.VANILLA_PLACING);
    }
}
