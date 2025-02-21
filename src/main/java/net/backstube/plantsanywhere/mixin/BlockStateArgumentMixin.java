package net.backstube.plantsanywhere.mixin;

import net.backstube.plantsanywhere.BlockProperties;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(BlockStateArgument.class)
public abstract class BlockStateArgumentMixin {

@Mutable
@Final
@Shadow
private BlockState state;

    @Inject(at = @At(value = "TAIL"), method = "<init>")
    private void plantsanywhere$init(BlockState state, Set<Property<?>> properties, NbtCompound data, CallbackInfo ci){
        if (this.state.contains(BlockProperties.VANILLA_PLACING)) {
            this.state = this.state.with(BlockProperties.VANILLA_PLACING, false);
        }
    }
}
