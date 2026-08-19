package com.neoloxal.overheated;

import com.neoloxal.overheated.item.ModItemTags;
import com.neoloxal.overheated.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LavaCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Map;

public class ModInteractions {
    private static final Map<Item, Item> OVERHEAT_MAP = Map.of(
            Items.IRON_SWORD, ModItems.OVERHEATED_SWORD.get(),
            Items.IRON_PICKAXE, ModItems.OVERHEATED_PICKAXE.get(),
            Items.IRON_AXE, ModItems.OVERHEATED_AXE.get(),
            Items.IRON_SHOVEL, ModItems.OVERHEATED_SHOVEL.get(),
            Items.IRON_HOE, ModItems.OVERHEATED_HOE.get()
    );

    @SubscribeEvent
    public static void interactWithBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();

        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack stack = event.getItemStack();

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (stack.is(ModItemTags.OVERHEATABLE_ITEMS)) {
            if (state.is(Blocks.LAVA_CAULDRON)) {
                LavaCauldronBlock cauldron = (LavaCauldronBlock) state.getBlock();
                if (cauldron.isFull(state)) {
                    ItemStack newStack = OVERHEAT_MAP.get(stack.getItem()).getDefaultInstance();
                    newStack.applyComponents(stack.getComponents());
                    newStack.set(DataComponents.ATTRIBUTE_MODIFIERS,
                            OVERHEAT_MAP.get(stack.getItem()).getDefaultInstance().get(DataComponents.ATTRIBUTE_MODIFIERS));
                    player.setItemInHand(hand, newStack);

                    level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());

                    event.setCanceled(true);
                }
            }
        }
    }
}
