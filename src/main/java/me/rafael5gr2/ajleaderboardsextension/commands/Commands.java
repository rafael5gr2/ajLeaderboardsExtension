package me.rafael5gr2.ajleaderboardsextension.commands;

import cloud.commandframework.Command;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;

import me.rafael5gr2.ajleaderboardsextension.AjLeaderboardsExtension;

import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Commands {

    private final AjLeaderboardsExtension plugin;
    private final PaperCommandManager<CommandSender> paperCommandManager;

    public Commands(final @NotNull AjLeaderboardsExtension plugin) throws Exception {

        this.plugin = plugin;

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
                new AjLeaderboardsCommand(this.plugin, this, this.paperCommandManager)
        ).forEach(AbstractCommand::register);
    }
}
