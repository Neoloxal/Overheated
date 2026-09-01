package com.neoloxal.overheated;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class OverheatedServerConfig {
    public static final OverheatedServerConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.ConfigValue<Integer> default_overheated_time;
    public final ModConfigSpec.ConfigValue<Integer> overheated_heat_time;
    public final ModConfigSpec.ConfigValue<Integer> cooling_heal;
    public final ModConfigSpec.ConfigValue<Boolean> water_cooling;
    public final ModConfigSpec.ConfigValue<Integer> overheated_sword_flame_time;
    public final ModConfigSpec.ConfigValue<Double> overheated_axe_knockback_mult;

    private OverheatedServerConfig(ModConfigSpec.Builder builder) {
        default_overheated_time = builder
                .translation("overheated.config.default_overheated_time")
                .comment("Default Overheated Time")
                .define("default_overheated_time", 18000);

        overheated_heat_time = builder
                .translation("overheated.config.overheated_heat_time")
                .comment("Overheat addon time")
                .define("overheated_heat_time", 6000);

        cooling_heal = builder
                .translation("overheated.config.cooling_heal")
                .comment("Cooling Heal")
                .define("cooling_heal", 5);

        water_cooling = builder
                .translation("overheated.config.water_cooling")
                .comment("Eable Water Cooling")
                .define("water_cooling", true);

        overheated_sword_flame_time = builder
                .translation("overheated.config.overheated_sword_flame_time")
                .comment("Sword Flame Time")
                .define("overheated_sword_flame_time", 80);

        overheated_axe_knockback_mult = builder
                .translation("overheated.config.overheated_axe_knockback_mult")
                .comment("Overheated axe knockback multiplier")
                .define("overheated_axe_knockback_mult", 1.0);
    }

    static {
        Pair<OverheatedServerConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(OverheatedServerConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
