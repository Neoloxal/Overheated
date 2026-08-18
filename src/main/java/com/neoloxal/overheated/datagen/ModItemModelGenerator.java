package com.neoloxal.overheated.datagen;

import com.neoloxal.overheated.Overheated;
import com.neoloxal.overheated.item.ModItems;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelGenerator extends ItemModelProvider {
    public ModItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Overheated.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        handheldItem(ModItems.OVERHEATED_SWORD.get());
        handheldItem(ModItems.OVERHEATED_PICKAXE.get());
        handheldItem(ModItems.OVERHEATED_AXE.get());
        handheldItem(ModItems.OVERHEATED_SHOVEL.get());
        handheldItem(ModItems.OVERHEATED_HOE.get());
    }
}
