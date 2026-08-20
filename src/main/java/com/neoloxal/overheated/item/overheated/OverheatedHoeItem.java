package com.neoloxal.overheated.item.overheated;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class OverheatedHoeItem extends HoeItem implements IOverheatable {
    public OverheatedHoeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        tickHeat(stack, level, entity, slotId, isSelected);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        appendOverheatText(stack, tooltipComponents);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        boolean vanillaBool = super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
        return shouldReset(oldStack, newStack, vanillaBool);
    }

    @Override
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
        boolean vanillaBool = super.shouldCauseBlockBreakReset(oldStack, newStack);
        return shouldReset(oldStack, newStack, vanillaBool);
    }
}
