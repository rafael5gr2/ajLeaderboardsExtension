package me.rafael5gr2.ajleaderboardsextension.commands;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.Command;
import cloud.commandframework.arguments.standard.IntegerArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.paper.PaperCommandManager;

import me.rafael5gr2.ajleaderboardsextension.AjLeaderboardsExtension;

import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

import us.ajg0702.leaderboards.boards.StatEntry;
import us.ajg0702.leaderboards.boards.TimedType;

public class AjLeaderboardsCommand extends AbstractCommand {

    public AjLeaderboardsCommand(
            final @NotNull AjLeaderboardsExtension plugin,
            final @NotNull Commands commands,
            final @NotNull PaperCommandManager<CommandSender> paperCommandManager
    ) {
        super(plugin, commands, paperCommandManager);
    }

    @Override
    public void register() {

        final Command.Builder<CommandSender> commandBuilder = paperCommandManager.commandBuilder("ajleaderboardsextension", "ajlbe");

        this.commands.register(
                commandBuilder.literal("find")
                        .argument(StringArgument.of("player"), ArgumentDescription.of("The player you want to find"))
                        .argument(StringArgument.of("board"), ArgumentDescription.of("The board name"))
                        .argument(StringArgument.of("type"), ArgumentDescription.of("The timed type to use"))
                        .handler(this::onFind)
        );


        this.commands.register(
                commandBuilder.literal("list")
                        .argument(StringArgument.of("board"), ArgumentDescription.of("The board name"))
                        .argument(StringArgument.of("type"), ArgumentDescription.of("The timed type to use"))
                        .argument(IntegerArgument.optional("page"), ArgumentDescription.of("The page number"))
                        .handler(this::onList)
        );
    }

    private void onFind(final @NotNull CommandContext<CommandSender> commandContext) {
        final String player = commandContext.get("player");
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(player);
        if (offlinePlayer == null) {
            commandContext.getSender().sendMessage(
                    MiniMessage.miniMessage().deserialize("<red>Could not find the player!</red>")
            );
            return;
        }
        final String board = commandContext.get("board");
        if (!this.plugin.getLeaderboardPluginInstance().getTopManager().boardExists(board)) {
            commandContext.getSender().sendMessage(
                    MiniMessage.miniMessage().deserialize("<red>Board does not exist!</red>")
            );
            return;
        }
        final String type = commandContext.get("type").toString().toUpperCase();
        final TimedType timedType;
        try {
            timedType = TimedType.valueOf(type);
        } catch (IllegalArgumentException exception) {
            commandContext.getSender().sendMessage(
                    MiniMessage.miniMessage().deserialize("<red>Please use a valid type!</red>")
            );
            return;
        }
        final StatEntry playerStatEntry = this.plugin.getLeaderboardPluginInstance().getTopManager().getStatEntry(offlinePlayer, board, timedType);
        final int position = playerStatEntry.getPosition();
        final int page = (position / 10) + 1;
        commandContext.getSender().sendMessage(
                MiniMessage.miniMessage().deserialize("<color:#2980B9>-------------------- Page: " + page + " --------------------</color>")
        );
        for (int i = (((page - 1) * 10) + 1); i <= page * 10; i++) {
            final StatEntry statEntry = this.plugin.getLeaderboardPluginInstance().getTopManager().getStat(i, board, timedType);
            commandContext.getSender().sendMessage(
                    MiniMessage.miniMessage().deserialize(
                            "<color:#6DD5FA>" + i + ".</color> " + statEntry.getPrefix() + statEntry.getPlayerName() +
                                    "<reset> <color:#6DD5FA>- " + statEntry.getScoreFormatted() + "</color>"
                    )
            );
        }
    }

    private void onList(final @NotNull CommandContext<CommandSender> commandContext) {
        final String board = commandContext.get("board");
        if (!this.plugin.getLeaderboardPluginInstance().getTopManager().boardExists(board)) {
            commandContext.getSender().sendMessage(
                    MiniMessage.miniMessage().deserialize("<red>Board does not exist!</red>")
            );
            return;
        }
        final String type = commandContext.get("type").toString().toUpperCase();
        final TimedType timedType;
        try {
            timedType = TimedType.valueOf(type);
        } catch (IllegalArgumentException exception) {
            commandContext.getSender().sendMessage(
                    MiniMessage.miniMessage().deserialize("<red>Please use a valid type!</red>")
            );
            return;
        }
        final Integer page = commandContext.getOrDefault("page", 1);
        if (page == null) {
            commandContext.getSender().sendMessage(
                    MiniMessage.miniMessage().deserialize("<red>Something went wrong while getting the page!</red>")
            );
            return;
        }
        if (page <= 0) {
            commandContext.getSender().sendMessage(
                    MiniMessage.miniMessage().deserialize("<red>The page must be grater or equal to 1!</red>")
            );
            return;
        }
        if (page > Integer.MAX_VALUE / 10) {
            commandContext.getSender().sendMessage(
                    MiniMessage.miniMessage().deserialize("<red>You reached the maximum number of pages!</red>")
            );
            return;
        }
        commandContext.getSender().sendMessage(
                MiniMessage.miniMessage().deserialize("<color:#2980B9>-------------------- Page: " + page + " --------------------</color>")
        );
        for (int i = (((page - 1) * 10) + 1); i <= page * 10; i++) {
            final StatEntry statEntry = this.plugin.getLeaderboardPluginInstance().getTopManager().getStat(i, board, timedType);
            commandContext.getSender().sendMessage(
                    MiniMessage.miniMessage().deserialize(
                            "<color:#6DD5FA>" + i + ".</color> " + statEntry.getPrefix() + statEntry.getPlayerName() +
                                    "<reset> <color:#6DD5FA>- " + statEntry.getScoreFormatted() + "</color>"
                    )
            );
        }
    }
}
