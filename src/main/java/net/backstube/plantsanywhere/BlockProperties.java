package net.backstube.plantsanywhere;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.BooleanProperty;

public final class BlockProperties {
    public static final BooleanProperty VANILLA_PLACING = BooleanProperty.of("vanillaplacing");

    public static boolean allowAnywhere(BlockState blockState){
        return blockState.contains(BlockProperties.VANILLA_PLACING) && !blockState.get(BlockProperties.VANILLA_PLACING);
               // || SharedStateProxy.get().getRoundStage() != RoundStages.Overworld; // TODO: add an api hook or just mixin
    }
}
