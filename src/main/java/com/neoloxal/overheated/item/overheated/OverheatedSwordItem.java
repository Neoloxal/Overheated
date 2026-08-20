package com.neoloxal.overheated.item.overheated;

import com.neoloxal.overheated.OverheatedServerConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class OverheatedSwordItem extends SwordItem implements IOverheatable {
    public OverheatedSwordItem(Tier tier, Properties properties) {
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
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean canHurtEnemy = super.hurtEnemy(stack, target, attacker);
        if (canHurtEnemy) {
            if (attacker instanceof Player player) {
                if (player.getAttackStrengthScale(0f) == 1f) {
                    target.setRemainingFireTicks(OverheatedServerConfig.CONFIG.overheated_sword_flame_time.get());
                }
            }
        }
        return canHurtEnemy;
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
