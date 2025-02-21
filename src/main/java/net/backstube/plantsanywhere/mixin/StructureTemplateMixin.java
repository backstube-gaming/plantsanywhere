package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.BlockState;
import net.minecraft.structure.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(StructureTemplate.class)
public abstract class StructureTemplateMixin {

    // give all plants the allow anywhere flag when spawned in by a structure
    @ModifyArg(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private BlockState troubleamongvillagers$place$setBlock(BlockState state){
        if (state.contains(BlockProperties.VANILLA_PLACING)) {
            return state.with(BlockProperties.VANILLA_PLACING, false);
        }
        return state;
    }
}
