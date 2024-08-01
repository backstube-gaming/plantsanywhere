package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MultifaceGrowthBlock;
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

@Mixin(MultifaceGrowthBlock.class)
public abstract class MultifaceGrowthBlockMixin {
    @Unique
    private MultifaceGrowthBlock getThis() {
        return (MultifaceGrowthBlock) (Object) this;
    }

    @Inject(at = @At("HEAD"), method = "appendProperties")
    protected void plantsanywhere$multiface_appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
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
}
