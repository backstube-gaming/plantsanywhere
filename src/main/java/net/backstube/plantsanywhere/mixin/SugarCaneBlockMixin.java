package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin {

    // prevent plant breakage on block updates and allow them to be placed anywhere
    @Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
    public void plantsanywhere$canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (BlockProperties.allowAnywhere(state))
            cir.setReturnValue(true);
    }

    @Inject(at = @At("HEAD"), method = "appendProperties")
    protected void plantsanywhere$sugarcane_appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(BlockProperties.VANILLA_PLACING);
    }
}