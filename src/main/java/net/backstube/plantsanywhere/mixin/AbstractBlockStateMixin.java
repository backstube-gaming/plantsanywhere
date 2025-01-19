package net.backstube.plantsanywhere.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

    @Unique
    private AbstractBlock.AbstractBlockState getThis() {
        return (AbstractBlock.AbstractBlockState) (Object) this;
    }

    @Inject(at = @At("RETURN"), method = "canReplace", cancellable = true)
    public void plantsanywhere$canReplace(ItemPlacementContext context, CallbackInfoReturnable<Boolean> cir) {
        var state = getThis();
        var player = context.getPlayer();
        if (state == null || player == null)
            return;

        if (player.isSneaking() && (state.isOf(Blocks.SHORT_GRASS)
                || state.isOf(Blocks.SEAGRASS))) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("RETURN"), method = "isReplaceable", cancellable = true)
    public void plantsanywhere$isReplaceable(CallbackInfoReturnable<Boolean> cir) {
        var state = getThis();
        if (state == null)
            return;

        if (state.isOf(Blocks.FERN)
                || state.isOf(Blocks.DEAD_BUSH)
                || state.isOf(Blocks.TALL_SEAGRASS)
                || state.isOf(Blocks.VINE)
                || state.isOf(Blocks.GLOW_LICHEN)
                || state.isOf(Blocks.TALL_GRASS)
                || state.isOf(Blocks.LARGE_FERN)
                || state.isOf(Blocks.WARPED_ROOTS)
                || state.isOf(Blocks.NETHER_SPROUTS)
                || state.isOf(Blocks.CRIMSON_ROOTS)
                || state.isOf(Blocks.HANGING_ROOTS)
        ) {
            cir.setReturnValue(false);
        }
    }
}
