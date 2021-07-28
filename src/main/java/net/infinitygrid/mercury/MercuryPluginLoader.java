package net.infinitygrid.mercury;

import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileFormat;
import net.infinitygrid.mercury.command.MercuryCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class MercuryPluginLoader extends JavaPlugin {

    private final PluginManager pluginManager = Bukkit.getPluginManager();
    private final CommandMap commandMap = Bukkit.getCommandMap();
    private final long initUnixTimeMs = System.currentTimeMillis();
    private final Set<MercuryComponent> componentSet = new HashSet<>();
    private Commodore commodore;

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

    public void registerCommand(MercuryCommand... mercuryCommands) {
        Arrays.stream(mercuryCommands).forEach(mercuryCommand -> {
            commandMap.register(mercuryCommand.getName(), mercuryCommand);
            if (CommodoreProvider.isSupported()) commodore = CommodoreProvider.getCommodore(this);
            if (commodore != null) {
                final InputStream commodoreFile = mercuryCommand.getCommodoreFileInStream();
                if (commodoreFile != null) {
                    try {
                        commodore.register(mercuryCommand, CommodoreFileFormat.parse(commodoreFile));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void registerComponent(MercuryComponent... components) {
        componentSet.addAll(Arrays.asList(components));
        Arrays.stream(components).forEach(MercuryComponent::initiate);
    }

}
