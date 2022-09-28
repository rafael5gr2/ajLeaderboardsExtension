package me.rafael5gr2.ajleaderboardsextension.commands;

import cloud.commandframework.paper.PaperCommandManager;

import me.rafael5gr2.ajleaderboardsextension.AjLeaderboardsExtension;

import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

import us.ajg0702.leaderboards.LeaderboardPlugin;

public abstract class AbstractCommand {

    protected final AjLeaderboardsExtension plugin;
    protected final LeaderboardPlugin ajLeaderboardsInstance;
    protected final CommandManager commandManager;
    protected final PaperCommandManager<CommandSender> paperCommandManager;

    public AbstractCommand(
            final @NotNull AjLeaderboardsExtension plugin,
            final @NotNull LeaderboardPlugin ajLeaderboardsInstance,
            final @NotNull CommandManager commandManager,
            final @NotNull PaperCommandManager<CommandSender> paperCommandManager
    ) {
        this.plugin = plugin;
        this.ajLeaderboardsInstance = ajLeaderboardsInstance;
        this.commandManager = commandManager;
        this.paperCommandManager = paperCommandManager;
    }

    public abstract void register();
}
