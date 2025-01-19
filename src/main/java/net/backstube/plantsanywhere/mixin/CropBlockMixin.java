package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin {

    @Unique
    private CropBlock getThis() {
        return (CropBlock) (Object) this;
    }

    @Inject(at = @At("HEAD"), method = "appendProperties")
    protected void plantsanywhere$appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(BlockProperties.VANILLA_PLACING);
    }

    @Inject(at = @At("HEAD"), method = "withAge", cancellable = true)
    protected void plantsanywhere$withAge(int age, CallbackInfoReturnable<BlockState> cir) {
        // this is called when a crop grows. This will also alter non-player or structure placed blocks that grow, but
        // currently I cannot see any downside of it
        var that = getThis();
        var state = that.getDefaultState();
        if (state != null && state.contains(BlockProperties.VANILLA_PLACING)) {
            cir.setReturnValue(
                    state.with(that.getAgeProperty(), Integer.valueOf(age))
                            .with(BlockProperties.VANILLA_PLACING, false));
        }
    }
}
