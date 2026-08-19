package com.neoloxal.overheated.datagen;

import com.neoloxal.overheated.Overheated;
import com.neoloxal.overheated.item.ModItemTags;
import com.neoloxal.overheated.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Overheated.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModItemTags.OVERHEATED_ITEMS)
                .add(ModItems.OVERHEATED_SWORD.get())
                .add(ModItems.OVERHEATED_PICKAXE.get())
                .add(ModItems.OVERHEATED_AXE.get())
                .add(ModItems.OVERHEATED_SHOVEL.get())
                .add(ModItems.OVERHEATED_HOE.get());

        tag(ModItemTags.OVERHEATABLE_ITEMS)
                .add(Items.IRON_SWORD)
                .add(Items.IRON_PICKAXE)
                .add(Items.IRON_AXE)
                .add(Items.IRON_SHOVEL)
                .add(Items.IRON_HOE);
    }
}
