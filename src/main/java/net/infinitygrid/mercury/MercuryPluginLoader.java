package net.infinitygrid.mercury;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class MercuryPluginLoader extends JavaPlugin {

    private final PluginManager pluginManager = Bukkit.getPluginManager();
    private final long initUnixTimeMs = System.currentTimeMillis();
    private final Set<MercuryComponent> componentSet = new HashSet<>();

    @Override
    public final void onLoad() {
        onPluginLoad();
    }

    @Override
    public final void onEnable() {
        onPluginEnable();
        getLogger().log(Level.INFO, "Plugin has been enabled! (Took " + (System.currentTimeMillis() - initUnixTimeMs) + "ms!)");
        new BukkitRunnable() {
            @Override
            public void run() {
                afterPluginEnable();
            }
        };
        afterPluginEnable();
    }

    @Override
    public final void onDisable() {
        componentSet.forEach(MercuryComponent::shutdown);
        onPluginDisable();
        getLogger().log(Level.INFO, "Plugin has been disabled.");
    }

    public void onPluginLoad() {}
    public void onPluginEnable() {}
    public void afterPluginEnable() {}
    public void onPluginDisable() {}

    public void registerListener(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    public void registerComponent(MercuryComponent... components) {
        componentSet.addAll(Arrays.asList(components));
        Arrays.stream(components).forEach(MercuryComponent::initiate$Mercury_main);
    }

}
