/******************************************************************************
 * Multiverse 2 Copyright (c) the Multiverse Team 2020.                       *
 * Multiverse 2 is licensed under the BSD License.                            *
 * For more information please check the README.md file included              *
 * with this project.                                                         *
 ******************************************************************************/

package com.onarandombox.MultiverseCore.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.event.MVConfigReloadEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("mv")
public class ReloadCommand extends MultiverseCoreCommand {

    public ReloadCommand(@NotNull MultiverseCore plugin) {
        super(plugin);
    }

    @Subcommand("reload")
    @CommandPermission("multiverse.core.reload")
    @Description("Reloads config files for all multiverse modules.")
    public void onReloadCommand(@NotNull CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "Reloading all Multiverse Plugin configs...");
        this.plugin.loadConfigs();
        this.plugin.getAnchorManager().loadAnchors();
        this.plugin.getMVWorldManager().loadWorlds(true);

        List<String> configsLoaded = new ArrayList<>();
        configsLoaded.add("Multiverse-Core - config.yml");
        configsLoaded.add("Multiverse-Core - worlds.yml");
        configsLoaded.add("Multiverse-Core - anchors.yml");

        MVConfigReloadEvent configReload = new MVConfigReloadEvent(configsLoaded);
        this.plugin.getServer().getPluginManager().callEvent(configReload);

        configReload.getAllConfigsLoaded().forEach(sender::sendMessage);
        sender.sendMessage(String.format("%sReload Complete!", ChatColor.GREEN));
    }
}
