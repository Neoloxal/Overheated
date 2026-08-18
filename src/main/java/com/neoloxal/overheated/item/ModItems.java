package com.neoloxal.overheated.item;

import com.neoloxal.overheated.Overheated;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Overheated.MODID);


    public static final DeferredItem<SwordItem> OVERHEATED_SWORD = ITEMS.register("overheated_iron_sword",
            () -> new SwordItem(Tiers.IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 5, -2.8f))));

    public static final DeferredItem<PickaxeItem> OVERHEATED_PICKAXE = ITEMS.register("overheated_iron_pickaxe",
            () -> new PickaxeItem(Tiers.IRON, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(Tiers.IRON, 1.5f, -2.2f))));

    public static final DeferredItem<AxeItem> OVERHEATED_AXE = ITEMS.register("overheated_iron_axe",
            () -> new AxeItem(Tiers.IRON, new Item.Properties()
                    .attributes(AxeItem.createAttributes(Tiers.IRON, 6.5f, -3.6f))));

    public static final DeferredItem<ShovelItem> OVERHEATED_SHOVEL = ITEMS.register("overheated_iron_shovel",
            () -> new ShovelItem(Tiers.IRON, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(Tiers.IRON, 2f, -1.5f))));

    public static final DeferredItem<HoeItem> OVERHEATED_HOE = ITEMS.register("overheated_iron_hoe",
            () -> new HoeItem(Tiers.IRON, new Item.Properties()
                    .attributes(HoeItem.createAttributes(Tiers.IRON, -1.5F, -1.5F))));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
