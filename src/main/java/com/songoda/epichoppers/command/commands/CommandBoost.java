package com.songoda.epichoppers.command.commands;

import com.songoda.epichoppers.EpicHoppers;
import com.songoda.epichoppers.boost.BoostData;
import com.songoda.epichoppers.command.AbstractCommand;
import com.songoda.epichoppers.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandBoost extends AbstractCommand {

    public CommandBoost(AbstractCommand parent) {
        super(parent, false, "boost");
    }

    @Override
    protected ReturnType runCommand(EpicHoppers instance, CommandSender sender, String... args) {
        if (args.length < 3) {
            instance.getLocale().newMessage("&7Syntax error...").sendPrefixedMessage(sender);
            return ReturnType.SYNTAX_ERROR;
        }
        if (!Methods.isInt(args[2])) {
            instance.getLocale().newMessage("&6" + args[2] + " &7is not a number...").sendPrefixedMessage(sender);
            return ReturnType.SYNTAX_ERROR;
        }

        long duration = 0L;

        if (args.length > 3) {
            for (int i = 1; i < args.length; i++) {
                String line = args[i];
                long time = Methods.parseTime(line);
                duration += time;

            }
        }

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            instance.getLocale().newMessage("&cThat player does not exist or is not online...").sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }

        BoostData boostData = new BoostData(Integer.parseInt(args[2]), duration == 0L ? Long.MAX_VALUE : System.currentTimeMillis() + duration, player.getUniqueId());
        instance.getBoostManager().addBoostToPlayer(boostData);
        instance.getLocale().newMessage("&7Successfully boosted &6" + Bukkit.getPlayer(args[1]).getName()
                + "'s &7hopper transfer rates by &6" + args[2] + "x" + (duration == 0L ? "" : (" for " + Methods.makeReadable(duration))) + "&7.").sendPrefixedMessage(sender);
        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(EpicHoppers instance, CommandSender sender, String... args) {
        if (args.length == 2) {
            List<String> players = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                players.add(player.getName());
            }
            return players;
        } else if (args.length == 3) {
            return Arrays.asList("1", "2", "3", "4", "5");
        } else if (args.length == 4) {
            return Arrays.asList("1m", "1h", "1d");
        }
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "epichoppers.admin";
    }

    @Override
    public String getSyntax() {
        return "/eh boost <player> <amount> [duration]";
    }

    @Override
    public String getDescription() {
        return "This allows you to boost a players hoppers transfer speeds by a multiplier (Put 2 for double, 3 for triple and so on).";
    }
}
