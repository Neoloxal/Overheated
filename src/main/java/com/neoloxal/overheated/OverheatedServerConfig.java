package com.neoloxal.overheated;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class OverheatedServerConfig {
    public static final OverheatedServerConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.ConfigValue<Integer> cooling_heal;
    public final ModConfigSpec.ConfigValue<Integer> overheated_sword_flame_time;

    private OverheatedServerConfig(ModConfigSpec.Builder builder) {
        cooling_heal = builder
                .translation("overheated.config.cooling_heal")
                .comment("Cooling Heal")
                .define("cooling_heal", 5);

        overheated_sword_flame_time = builder
                .translation("overheated.config.overheated_sword_flame_time")
                .comment("Sword Flame Time")
                .define("overheated_sword_flame_time", 40);
    }

    static {
        Pair<OverheatedServerConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(OverheatedServerConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
