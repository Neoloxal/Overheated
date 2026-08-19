package com.neoloxal.overheated.item.overheated;

import com.neoloxal.overheated.Overheated;
import com.neoloxal.overheated.item.ModDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.List;

public interface IOverheatable {
    default void tickHeat(ItemStack stack, Level level, Entity entity, int slotId) {
        if (stack.has(ModDataComponents.OVERHEAT_TIME.get())) {
            if (entity instanceof Player player) {
                if (stack.get(ModDataComponents.OVERHEAT_TIME.get()) == 0) {
                    ItemStack newStack = Overheated.cooling_map.get(stack.getItem()).getDefaultInstance();
                    newStack.applyComponents(stack.getComponents());
                    newStack.set(DataComponents.ATTRIBUTE_MODIFIERS,
                            Overheated.cooling_map.get(stack.getItem()).getDefaultInstance().get(DataComponents.ATTRIBUTE_MODIFIERS));
                    newStack.remove(ModDataComponents.OVERHEAT_TIME.get());
                    player.getInventory().setItem(slotId, newStack);
                    return;
                }
                int ticksPerDecrement = 1;
                if (level.dimension() == Level.NETHER) {ticksPerDecrement = 2;}
                if (level.getGameTime() % ticksPerDecrement == 0) {
                    stack.set(ModDataComponents.OVERHEAT_TIME.get(), stack.get(ModDataComponents.OVERHEAT_TIME.get()) - 1);
                }
            }
        }
    }

    default void appendOverheatText(ItemStack stack, List<Component> tooltipComponents) {
        if (stack.has(ModDataComponents.OVERHEAT_TIME.get())) {
            int timeLeft = stack.get(ModDataComponents.OVERHEAT_TIME.get());
            tooltipComponents.add(Component.translatable("tooltip.overheated.overheat_time",
                    ((int) Math.floor(timeLeft / 20) / 60),
                    ((int) Math.floor(timeLeft / 20) % 60)/* +
                            ((float) ((timeLeft / 2) % 10)) / 10f)*/));
        }
    }
}
