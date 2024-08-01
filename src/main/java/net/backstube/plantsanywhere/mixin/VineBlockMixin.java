package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VineBlock.class)
public abstract class VineBlockMixin {

    @Unique
    private VineBlock getThis(){
        return (VineBlock) (Object)this;
    }

    @Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
    public void plantsanywhere$canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (BlockProperties.allowAnywhere(state))
            cir.setReturnValue(true);
    }

/*    @Inject(at = @At("HEAD"), method = "getStateForNeighborUpdate", cancellable = true)
    private void plantsanywhere$getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        if (BlockProperties.allowAnywhere(state)) {
            var shapeState = getThis().getPlacementShape(state, world, pos);
            cir.setReturnValue(shapeState);
        }
    }*/

    @Inject(at = @At("RETURN"), method = "getPlacementState", cancellable = true)
    public void plantsanywhere$getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        var state = cir.getReturnValue();
        if(state == null){
            var direction = ctx.getPlayerLookDirection();
            if(direction == Direction.DOWN)
                return;

            var prop = VineBlock.getFacingProperty(direction);
            cir.setReturnValue(getThis().getDefaultState()
                    .with(prop, true)
                    .with(BlockProperties.VANILLA_PLACING, false));

        } else if (state.contains(BlockProperties.VANILLA_PLACING)) {
            cir.setReturnValue(state.with(BlockProperties.VANILLA_PLACING, false));
        }
    }

    @Inject(at = @At("HEAD"), method = "appendProperties")
    protected void plantsanywhere$vine_appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(BlockProperties.VANILLA_PLACING);
    }
}
