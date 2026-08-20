package com.neoloxal.overheated.item;

import com.neoloxal.overheated.Overheated;
import com.neoloxal.overheated.OverheatedServerConfig;
import com.neoloxal.overheated.OverheatedStartupConfig;
import com.neoloxal.overheated.item.overheated.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    private static final int OVERHEAT_TIME = OverheatedStartupConfig.CONFIG.default_overheated_time.get();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Overheated.MODID);


    public static final DeferredItem<SwordItem> OVERHEATED_SWORD = ITEMS.register("overheated_iron_sword",
            () -> new OverheatedSwordItem(Tiers.IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 5, -2.8f))
                    .component(ModDataComponents.OVERHEAT_TIME, OVERHEAT_TIME)
            ));

    public static final DeferredItem<PickaxeItem> OVERHEATED_PICKAXE = ITEMS.register("overheated_iron_pickaxe",
            () -> new OverheatedPickaxeItem(Tiers.IRON, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(Tiers.IRON, 1.5f, -2.2f))
                    .component(ModDataComponents.OVERHEAT_TIME, OVERHEAT_TIME)
            ));

    public static final DeferredItem<AxeItem> OVERHEATED_AXE = ITEMS.register("overheated_iron_axe",
            () -> new OverheatedAxeItem(Tiers.IRON, new Item.Properties()
                    .attributes(AxeItem.createAttributes(Tiers.IRON, 7f, -3.6f))
                    .component(ModDataComponents.OVERHEAT_TIME, OVERHEAT_TIME)
            ));

    public static final DeferredItem<ShovelItem> OVERHEATED_SHOVEL = ITEMS.register("overheated_iron_shovel",
            () -> new OverheatedShovelItem(Tiers.IRON, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(Tiers.IRON, 2f, -1.5f))
                    .component(ModDataComponents.OVERHEAT_TIME, OVERHEAT_TIME)
            ));

    public static final DeferredItem<HoeItem> OVERHEATED_HOE = ITEMS.register("overheated_iron_hoe",
            () -> new OverheatedHoeItem(Tiers.IRON, new Item.Properties()
                    .attributes(HoeItem.createAttributes(Tiers.IRON, -1.5F, -1.5F))
                    .component(ModDataComponents.OVERHEAT_TIME, OVERHEAT_TIME)
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
