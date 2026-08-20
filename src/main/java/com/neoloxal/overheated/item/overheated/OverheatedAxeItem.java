package com.neoloxal.overheated.item.overheated;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;

import java.util.List;

public class OverheatedAxeItem extends AxeItem implements IOverheatable {
    public OverheatedAxeItem(Tier tier, Properties properties) {
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
        Level level = attacker.level();

        if (canHurtEnemy && !level.isClientSide()) {
            if (attacker instanceof Player player) {
                if (player.getAttackStrengthScale(0f) == 1.0f) {
                    level.explode(
                            target,
                            new DamageSources(level.registryAccess()).explosion(target, attacker),
                            new ExplosionDamageCalculator() {
                                @Override
                                public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
                                    boolean shouldDamage = super.shouldDamageEntity(explosion, entity);
                                    if (entity == attacker) {
                                        return false;
                                    }
                                    return shouldDamage;
                                }
                            },
                            target.position(),
                            2,
                            false,
                            Level.ExplosionInteraction.TRIGGER
                    );
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
