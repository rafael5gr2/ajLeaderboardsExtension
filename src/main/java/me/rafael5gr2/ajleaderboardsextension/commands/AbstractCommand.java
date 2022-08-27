package me.rafael5gr2.ajleaderboardsextension.commands;

import cloud.commandframework.paper.PaperCommandManager;

import me.rafael5gr2.ajleaderboardsextension.AjLeaderboardsExtension;

import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public abstract class AbstractCommand {

    protected final AjLeaderboardsExtension plugin;
    protected final Commands commands;
    protected final PaperCommandManager<CommandSender> paperCommandManager;

    public AbstractCommand(
            final @NotNull AjLeaderboardsExtension plugin,
            final @NotNull Commands commands,
            final @NotNull PaperCommandManager<CommandSender> paperCommandManager
    ) {
        this.plugin = plugin;
        this.commands = commands;
        this.paperCommandManager = paperCommandManager;
    }

    public abstract void register();
}
