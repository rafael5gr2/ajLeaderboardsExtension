package me.rafael5gr2.ajleaderboardsextension.commands;

import cloud.commandframework.Command;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;

import me.rafael5gr2.ajleaderboardsextension.AjLeaderboardsExtension;
import me.rafael5gr2.ajleaderboardsextension.commands.implementations.AjLeaderboardsCommand;

import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

import us.ajg0702.leaderboards.LeaderboardPlugin;

import java.util.List;

public class CommandManager {

    private final AjLeaderboardsExtension plugin;
    private final LeaderboardPlugin ajLeaderboardsInstance;
    private final PaperCommandManager<CommandSender> paperCommandManager;

    public CommandManager(final @NotNull AjLeaderboardsExtension plugin, final @NotNull LeaderboardPlugin ajLeaderboardsInstance) throws Exception {

        this.plugin = plugin;

        this.ajLeaderboardsInstance = ajLeaderboardsInstance;

        this.paperCommandManager = PaperCommandManager.createNative(plugin, CommandExecutionCoordinator.simpleCoordinator());

        if (paperCommandManager.hasCapability(CloudBukkitCapabilities.BRIGADIER)) {
            paperCommandManager.registerBrigadier();
        }

        if (paperCommandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            paperCommandManager.registerAsynchronousCompletions();
        }
    }

    public void register(final @NotNull Command.Builder<CommandSender> commandBuilder) {
        this.paperCommandManager.command(commandBuilder);
    }

    public void registerCommands() {
        List.of(
                new AjLeaderboardsCommand(this.plugin, ajLeaderboardsInstance, this, this.paperCommandManager)
        ).forEach(AbstractCommand::register);
    }
}
