package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("UnreachableCode")
@Mixin(Block.class)
public abstract class BlockMixin {

    @Unique
    private Block getThis(){
        return (Block)(Object) this;
    }

    @Inject(at = @At("HEAD"), method = "appendProperties")
    protected void plantsanywhere$appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        // only the blocks that do not override appendProperties themselves!
        if (getThis() instanceof PlantBlock
            || getThis() instanceof BambooShootBlock
            || getThis() instanceof SporeBlossomBlock) {
            builder.add(BlockProperties.VANILLA_PLACING);
        }
    }

    @Inject(at = @At("HEAD"), method = "getPlacementState", cancellable = true)
    public void plantsanywhere$getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        var state = ((Block)(Object)this).getDefaultState();
        if(state.contains(BlockProperties.VANILLA_PLACING)){
            cir.setReturnValue(state
                    .with(BlockProperties.VANILLA_PLACING,false));
        }
    }
}
