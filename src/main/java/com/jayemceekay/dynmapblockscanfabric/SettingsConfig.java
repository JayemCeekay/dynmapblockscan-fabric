package com.jayemceekay.dynmapblockscanfabric;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class SettingsConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> excludeModules;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> excludeBlockNames;

    static {
        BUILDER.comment("DynmapBlockScan settings");
        BUILDER.push("settings");
        excludeModules = BUILDER.comment("Which modules to exclude").defineList("exclude_modules", Arrays.asList("minecraft"), entry -> true);
        excludeBlockNames = BUILDER.comment("Which block names to exclude").defineList("exclude_blocknames", Arrays.asList(), entry -> true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
