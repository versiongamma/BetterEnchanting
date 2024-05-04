package versiongamma.betterenchanting;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class BetterEnchanting extends JavaPlugin {
    private Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.log(Level.INFO, "Using BetterEnchanting v0.1.3");
        getServer().getPluginManager().registerEvents(new AnvilEventListener(), this);
    }

    @Override
    public void onDisable() {
        logger.log(Level.INFO, "Shutting down BetterEnchanting...");
        HandlerList.unregisterAll(this);
    }
}
