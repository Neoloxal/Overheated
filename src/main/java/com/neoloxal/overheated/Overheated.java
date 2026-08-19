package com.neoloxal.overheated;

import com.mojang.logging.LogUtils;
import com.neoloxal.overheated.item.ModDataComponents;
import com.neoloxal.overheated.item.ModItems;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
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
        ModDataComponents.register(modEventBus);
        ModItems.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(ModInteractions.class);
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
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.insertAfter(Items.IRON_SWORD.getDefaultInstance(), ModItems.OVERHEATED_SWORD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.insertAfter(Items.IRON_HOE.getDefaultInstance(), ModItems.OVERHEATED_SHOVEL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.OVERHEATED_SHOVEL.toStack(), ModItems.OVERHEATED_PICKAXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.OVERHEATED_PICKAXE.toStack(), ModItems.OVERHEATED_AXE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(ModItems.OVERHEATED_AXE.toStack(), ModItems.OVERHEATED_HOE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
