package net.backstube.plantsanywhere.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LilyPadBlock.class)
public abstract class LilyPadBlockMixin {

    @Inject(at = @At("HEAD"), method = "onEntityCollision", cancellable = true)
    public void plantsanywhere$onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (world instanceof ServerWorld && entity instanceof BoatEntity) {
            ci.cancel(); // prevent boats breaking lily pads
        }
    }

}
