package org.versiongamma.betterenchanting;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterEnchanting extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Using BetterEnchanting v0.1");
        getServer().getPluginManager().registerEvents(new AnvilEventListener(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("Shutting down BetterEnchanting...");
        HandlerList.unregisterAll(this);
    }
}
