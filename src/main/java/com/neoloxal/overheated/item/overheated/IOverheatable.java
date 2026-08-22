package com.neoloxal.overheated.item.overheated;

import com.neoloxal.overheated.ModEffects;
import com.neoloxal.overheated.Overheated;
import com.neoloxal.overheated.OverheatedServerConfig;
import com.neoloxal.overheated.item.ModDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public interface IOverheatable {
    default void tickHeat(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide()) {
            if (stack.has(ModDataComponents.OVERHEAT_TIME.get())) {
                if (entity instanceof Player player) {
                    if (stack.get(ModDataComponents.OVERHEAT_TIME.get()) <= 0) {
                        player.getInventory().setItem(slotId, coolDownItem(stack));
                        ModEffects.coolingEffect(((ServerLevel) level), entity.blockPosition(), false);
                        return;
                    }
                    if (stack.get(ModDataComponents.OVERHEAT_TIME.get()) >= 999999) {
                        stack.remove(ModDataComponents.CHANGED.get());
                        return;
                    }
                    int ticksPerDecrement = 20;
                    if (level.dimension() == Level.NETHER) {
                        ticksPerDecrement = 40;
                    }
                    if (level.isRainingAt(player.getOnPos())) {
                        ticksPerDecrement = 5;
                    }
                    if (level.getGameTime() % ticksPerDecrement == 0) {
                        stack.remove(ModDataComponents.CHANGED.get());
                        stack.set(ModDataComponents.OVERHEAT_TIME.get(), stack.get(ModDataComponents.OVERHEAT_TIME.get()) - 20);
                    }

                    if (isSelected) {
                        if (player.isInWater() && OverheatedServerConfig.CONFIG.water_cooling.get()) {
                            player.getInventory().setItem(slotId, coolDownItem(stack));

                            ModEffects.coolingEffect(((ServerLevel) level), entity.blockPosition());
                        }
                    }
                }
            }
        }
    }

    private ItemStack coolDownItem(ItemStack stack) {
        ItemStack newStack = Overheated.cooling_map.get(stack.getItem()).getDefaultInstance();
        newStack.applyComponents(stack.getComponents());
        newStack.set(DataComponents.ATTRIBUTE_MODIFIERS,
                Overheated.cooling_map.get(stack.getItem()).getDefaultInstance().get(DataComponents.ATTRIBUTE_MODIFIERS));
        newStack.remove(ModDataComponents.OVERHEAT_TIME.get());
        return newStack;
    }

    default void appendOverheatText(ItemStack stack, List<Component> tooltipComponents) {
        if (stack.has(ModDataComponents.OVERHEAT_TIME.get())) {
            if (stack.get(ModDataComponents.OVERHEAT_TIME.get()) < 999999) {
                int timeLeft = stack.get(ModDataComponents.OVERHEAT_TIME.get());
                tooltipComponents.add(Component.translatable("tooltip.overheated.overheat_time",
                        ((int) Math.floor(timeLeft / 20) / 60),
                        ((int) Math.floor(timeLeft / 20) % 60)/* +
                            ((float) ((timeLeft / 2) % 10)) / 10f)*/));
            } else {
                tooltipComponents.add(Component.translatable("tooltip.overheated.overheat_time.infinite"));
            }
        }
    }

    default boolean shouldReset(ItemStack oldStack, ItemStack newStack, boolean fallBack) {
        if (newStack.has(ModDataComponents.CHANGED.get())) {
            return true;
        }

        ItemStack oldStackCopy = oldStack.copy();
        oldStackCopy.remove(ModDataComponents.OVERHEAT_TIME.get());

        ItemStack newStackCopy = newStack.copy();
        newStackCopy.remove(ModDataComponents.OVERHEAT_TIME.get());

        if (ItemStack.isSameItemSameComponents(oldStackCopy, newStackCopy)) {
            return false;
        }
        return fallBack;
    }

    default void updateOverheatTime(ItemStack stack) {
        if (stack.has(ModDataComponents.OVERHEAT_TIME.get()) && stack.has(ModDataComponents.CHANGED.get())) {
            if (stack.get(ModDataComponents.OVERHEAT_TIME.get()) == 3000) {
                stack.set(ModDataComponents.OVERHEAT_TIME.get(), OverheatedServerConfig.CONFIG.default_overheated_time.get());
            }
        }
    }
}
