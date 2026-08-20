package com.neoloxal.overheated;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class OverheatedStartupConfig {
    public static final OverheatedStartupConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.ConfigValue<Integer> default_overheated_time;

    private OverheatedStartupConfig(ModConfigSpec.Builder builder) {
        default_overheated_time = builder
                .translation("overheated.config.default_overheated_time")
                .comment("Default Overheated Time")
                .gameRestart()
                .define("default_overheated_time", 3000);
    }

    static {
        Pair<OverheatedStartupConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(OverheatedStartupConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
