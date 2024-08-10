package net.neocosmic.neovoidless.commands;

import net.neocosmic.neovoidless.NeoVoidless;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("neovoidless.reload")) {
            return true;
        }

        NeoVoidless.getInstance().loadPlugin();
        sender.sendMessage("Plugin reloaded!");

        return true;
    }
}
