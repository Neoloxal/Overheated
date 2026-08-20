package com.neoloxal.overheated;

import com.mojang.logging.LogUtils;
import com.neoloxal.overheated.item.ModDataComponents;
import com.neoloxal.overheated.item.ModItems;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

import java.util.Map;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Overheated.MODID)
public class Overheated {
    public static final String MODID = "overheated";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Map<Item, Item> overheat_map;
    public static Map<Item, Item> cooling_map;

    public Overheated(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(ModInteractions.class);
        modContainer.registerConfig(ModConfig.Type.SERVER, OverheatedServerConfig.CONFIG_SPEC);
        modContainer.registerConfig(ModConfig.Type.STARTUP, OverheatedStartupConfig.CONFIG_SPEC);

        ModDataComponents.register(modEventBus);
        ModItems.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        cooling_map = Map.of(
                ModItems.OVERHEATED_SWORD.get(), Items.IRON_SWORD,
                ModItems.OVERHEATED_PICKAXE.get(), Items.IRON_PICKAXE,
                ModItems.OVERHEATED_AXE.get(), Items.IRON_AXE,
                ModItems.OVERHEATED_SHOVEL.get(), Items.IRON_SHOVEL,
                ModItems.OVERHEATED_HOE.get() ,Items.IRON_HOE
        );

         overheat_map = Map.of(
                Items.IRON_SWORD, ModItems.OVERHEATED_SWORD.get(),
                Items.IRON_PICKAXE, ModItems.OVERHEATED_PICKAXE.get(),
                Items.IRON_AXE, ModItems.OVERHEATED_AXE.get(),
                Items.IRON_SHOVEL, ModItems.OVERHEATED_SHOVEL.get(),
                Items.IRON_HOE, ModItems.OVERHEATED_HOE.get()
        );
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        ItemStack overheated_sword = ModItems.OVERHEATED_SWORD.toStack();
        overheated_sword.set(ModDataComponents.OVERHEAT_TIME, 999999);

        ItemStack overheated_shovel = ModItems.OVERHEATED_SHOVEL.toStack();
        overheated_shovel.set(ModDataComponents.OVERHEAT_TIME, 999999);

        ItemStack overheated_pickaxe = ModItems.OVERHEATED_PICKAXE.toStack();
        overheated_pickaxe.set(ModDataComponents.OVERHEAT_TIME, 999999);

        ItemStack overheated_axe = ModItems.OVERHEATED_AXE.toStack();
        overheated_axe.set(ModDataComponents.OVERHEAT_TIME, 999999);

        ItemStack overheated_hoe = ModItems.OVERHEATED_HOE.toStack();
        overheated_hoe.set(ModDataComponents.OVERHEAT_TIME, 999999);

        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.insertAfter(Items.IRON_SWORD.getDefaultInstance(), overheated_sword, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.insertAfter(Items.IRON_HOE.getDefaultInstance(), overheated_shovel, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(overheated_shovel, overheated_pickaxe, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(overheated_pickaxe, overheated_axe, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(overheated_axe, overheated_hoe, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
