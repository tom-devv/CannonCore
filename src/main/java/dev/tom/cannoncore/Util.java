package dev.tom.cannoncore;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class Util {

    private static final Pattern HEX_PATTERN = Pattern.compile("&(#\\w{6})");


    private static void sendMessage(CommandSender to, String message) {
        to.sendMessage(colorize(message));
    }

    public static void sendMessage(CommandSender to, List<String> message) {
        message.forEach((s) -> {
            sendMessage(to, s);
        });
    }

    public static String[] colorize(String... str) {
        for (int i = 0; i < str.length; i++) {
            str[i] = colorize(str[i]);
        }
        return str;
    }

    public static String colorize(String str) {
        Matcher matcher = HEX_PATTERN.matcher(ChatColor.translateAlternateColorCodes('&', str));
        StringBuffer buffer = new StringBuffer();

        while(matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.valueOf(matcher.group(1)).toString());
        }

        return org.bukkit.ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    public static List<String> colorize(List<String> lst) {
        List<String> newList = new ArrayList();
        lst.forEach((s) -> {
            newList.add(colorize(s));
        });
        return newList;
    }

    public static List<String> replaceList(List<String> lst, String from, String to) {
        List<String> newList = new ArrayList();
        lst.forEach((s) -> {
            newList.add(s.replace(from, to));
        });
        return newList;
    }

    public static void sendMessage (Player player, String message) {
        if (!message.isEmpty())
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendMessage(Player player, List<String> messageList) {
        for (String message : messageList)
            if (!message.isEmpty())
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
