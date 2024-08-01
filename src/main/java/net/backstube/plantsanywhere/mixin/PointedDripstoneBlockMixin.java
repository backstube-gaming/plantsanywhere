package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PointedDripstoneBlock.class)
public abstract class PointedDripstoneBlockMixin {

    @Inject(at = @At("HEAD"), method = "appendProperties")
    protected void plantsanywhere$dripstone_appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(BlockProperties.VANILLA_PLACING);
    }

    @Inject(at = @At("RETURN"), method = "getPlacementState", cancellable = true)
    public void plantsanywhere$getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        if(cir.getReturnValue() != null) {
            cir.setReturnValue(cir.getReturnValue()
                    .with(BlockProperties.VANILLA_PLACING, false));
        }
    }

    @Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
    public void plantsanywhere$canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (BlockProperties.allowAnywhere(state))
            cir.setReturnValue(true);
    }

    // currently this is not working, as it would need the placing blockstate.
    /*@Inject(at = @At("HEAD"), method = "canPlaceAtWithDirection", cancellable = true)
    public void plantsanywhere$canPlaceAtWithDirection(WorldView world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (BlockProperties.allowAnywhere(world.))
            cir.setReturnValue(true);
    }*/
}
