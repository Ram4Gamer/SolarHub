package com.solardev.SolarHub.utils.config;

import com.solardev.SolarHub.SolarHub;

public class ConfigUtils {

    private static SolarHub plugin;

    public ConfigUtils(SolarHub plugin) {
        this.plugin = plugin;
    }

    public static String getStringfromConfig(String key) {
        return plugin.getConfig().getString(key);
    }

    public static Boolean getBooleanfromConfig(String key) {
        return plugin.getConfig().getBoolean(key);
    }

    public static Integer getIntfromConfig(String key) {
        return plugin.getConfig().getInt(key);
    }

    public static String getStringfromScoreboard(String key) {
        return plugin.getScoreboard().getString(key);
    }

    public static Boolean getBooleanfromScoreboard(String key) {
        return plugin.getScoreboard().getBoolean(key);
    }

    public static Integer getIntfromScoreboard(String key) {
        return plugin.getScoreboard().getInt(key);
    }

    public static String getStringfromLang(String key) {
        return plugin.getLang().getString(key);
    }

}
