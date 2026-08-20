package com.neoloxal.overheated;

import com.neoloxal.overheated.item.ModDataComponents;
import com.neoloxal.overheated.item.ModItemTags;
import com.neoloxal.overheated.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LavaCauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import java.util.Map;
import java.util.Optional;

import static com.neoloxal.overheated.Overheated.overheat_map;
import static com.neoloxal.overheated.Overheated.cooling_map;

public class ModInteractions {
    @SubscribeEvent
    public static void interactWithBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();

        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack stack = event.getItemStack();

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (stack.is(ModItemTags.OVERHEATABLE_ITEMS) || stack.is(ModItemTags.OVERHEATED_ITEMS)) {
            if (state.is(Blocks.LAVA_CAULDRON)) {
                LavaCauldronBlock cauldron = (LavaCauldronBlock) state.getBlock();
                if (cauldron.isFull(state)) {
                    if (stack.is(ModItemTags.OVERHEATABLE_ITEMS)) {
                        ItemStack newStack = overheat_map.get(stack.getItem()).getDefaultInstance();
                        newStack.applyComponents(stack.getComponents());
                        newStack.set(DataComponents.ATTRIBUTE_MODIFIERS,
                                overheat_map.get(stack.getItem()).getDefaultInstance().get(DataComponents.ATTRIBUTE_MODIFIERS));
                        player.setItemInHand(hand, newStack);
                    } else {
                        if (stack.has(ModDataComponents.OVERHEAT_TIME)) {
                            stack.set(ModDataComponents.OVERHEAT_TIME.get(), stack.get(ModDataComponents.OVERHEAT_TIME.get()) + OverheatedServerConfig.CONFIG.overheated_heat_time.get());
                            stack.set(ModDataComponents.CHANGED.get(), true);
                        }
                    }

                    level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());

                    event.setCanceled(true);
                }
            } else if (state.is(Blocks.WATER_CAULDRON)) {
                if (stack.is(ModItemTags.OVERHEATED_ITEMS)) {
                    ItemStack newStack = cooling_map.get(stack.getItem()).getDefaultInstance();
                    newStack.applyComponents(stack.getComponents());
                    newStack.set(DataComponents.ATTRIBUTE_MODIFIERS,
                            cooling_map.get(stack.getItem()).getDefaultInstance().get(DataComponents.ATTRIBUTE_MODIFIERS));
                    newStack.remove(ModDataComponents.OVERHEAT_TIME.get());
                    newStack.setDamageValue(Math.max(0, newStack.getDamageValue() - OverheatedServerConfig.CONFIG.cooling_heal.get()));
                    player.setItemInHand(hand, newStack);

                    int cauldronLevel = state.getValue(BlockStateProperties.LEVEL_CAULDRON);
                    if (cauldronLevel - 1 > 0) {
                        level.setBlockAndUpdate(pos, Blocks.WATER_CAULDRON.defaultBlockState().setValue(BlockStateProperties.LEVEL_CAULDRON, cauldronLevel - 1));
                    } else {
                        level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                    }

                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void blockDrops(BlockDropsEvent event) {
        Level level = event.getLevel();
        ItemStack stack = event.getTool();

        if (level.isClientSide()) {
            if (stack.is(ModItems.OVERHEATED_PICKAXE.get())) {
                for (ItemEntity drop : event.getDrops()) {
                    Optional<RecipeHolder<SmeltingRecipe>> smelted = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(drop.getItem()), level);
                    Optional<RecipeHolder<BlastingRecipe>> blasted = level.getRecipeManager().getRecipeFor(RecipeType.BLASTING, new SingleRecipeInput(drop.getItem()), level);
                    if (smelted.isPresent() || blasted.isPresent()) {
                        ItemStack newDrop;
                        if (smelted.isPresent()) {
                            newDrop = smelted.get().value().getResultItem(level.registryAccess());
                        } else {
                            newDrop = blasted.get().value().getResultItem(level.registryAccess());
                        }
                        newDrop.setCount(drop.getItem().getCount());
                        drop.setItem(newDrop);
                    }
                }
            }
        }
    }
}
