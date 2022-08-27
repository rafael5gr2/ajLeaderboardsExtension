package me.rafael5gr2.ajleaderboardsextension;

import me.rafael5gr2.ajleaderboardsextension.commands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;

import us.ajg0702.leaderboards.LeaderboardPlugin;

@SuppressWarnings("unused")
public class AjLeaderboardsExtension extends JavaPlugin {

    private LeaderboardPlugin leaderboardPluginInstance;

    @Override
    public void onEnable() {
        final long initialTimeMillis = System.currentTimeMillis();

        getLogger().info("The plugin is enabling...");

        final Plugin ajLeaderboardsPlugin = Bukkit.getPluginManager().getPlugin("ajLeaderboards");
        if (ajLeaderboardsPlugin instanceof final LeaderboardPlugin leaderboardPlugin) {
            this.leaderboardPluginInstance = leaderboardPlugin;
        } else {
            getLogger().severe("Something went wrong while enabling support for ajLeaderboards! Disabling the plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        try {
            new Commands(this).registerCommands();
        } catch (Exception exception) {
            getLogger().severe("An error occurred while registering the command(s)! (Error message: " + exception.getMessage() + ")");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("The plugin has been enabled successfully! (after: " + (System.currentTimeMillis() - initialTimeMillis) + "ms)");
    }

    @Override
    public void onDisable() {
        final long initialTimeMillis = System.currentTimeMillis();

        getLogger().info("The plugin is disabling...");



        getLogger().info("The plugin has been disabled successfully! (after: " + (System.currentTimeMillis() - initialTimeMillis) + "ms)");
    }

    public @NotNull LeaderboardPlugin getLeaderboardPluginInstance() {
        return this.leaderboardPluginInstance;
    }
}
