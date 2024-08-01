package net.backstube.plantsanywhere.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CocoaBlock.class)
public abstract class CocoaBlockMixin {
    @Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
    public void plantsanywhere$canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (BlockProperties.allowAnywhere(state))
            cir.setReturnValue(true);
    }

    @Inject(at = @At("HEAD"), method = "appendProperties")
    protected void plantsanywhere$cocoa_appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(BlockProperties.VANILLA_PLACING);
    }

    @Inject(method = "getPlacementState", at = @At(value="INVOKE", target = "Lnet/minecraft/item/ItemPlacementContext;getWorld()Lnet/minecraft/world/World;"),  cancellable = true)
    public void plantsanywhere$getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir,
                                                 @Local LocalRef<BlockState> blockStateLocalRef) {
        blockStateLocalRef.set(blockStateLocalRef.get()
                .with(BlockProperties.VANILLA_PLACING, false));
    }
}
