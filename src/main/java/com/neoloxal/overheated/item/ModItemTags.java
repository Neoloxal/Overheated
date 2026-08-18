package com.neoloxal.overheated.item;

import com.neoloxal.overheated.Overheated;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> OVERHEATED_ITEMS = ItemTags.create(
            ResourceLocation.fromNamespaceAndPath(Overheated.MODID, "overheated_items"));
}
