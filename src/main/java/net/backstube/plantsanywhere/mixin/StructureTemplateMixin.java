package net.backstube.plantsanywhere.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.BlockState;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructureTemplate.class)
public abstract class StructureTemplateMixin {

    // give all plants the allow anywhere flag when spawned in by a structure
    @Inject(method = "place", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/block/BlockState;rotate(Lnet/minecraft/util/BlockRotation;)Lnet/minecraft/block/BlockState;", shift = At.Shift.AFTER))
    private void troubleamongvillagers$place(ServerWorldAccess world, BlockPos pos, BlockPos pivot,
                                             StructurePlacementData placementData, Random random, int flags,
                                             CallbackInfoReturnable<Boolean> cir, @Local LocalRef<BlockState> blockStateLocalRef) {

        if (blockStateLocalRef.get().contains(BlockProperties.VANILLA_PLACING)) {
            blockStateLocalRef.set(blockStateLocalRef.get()
                    .with(BlockProperties.VANILLA_PLACING, false));
        }
    }
}
