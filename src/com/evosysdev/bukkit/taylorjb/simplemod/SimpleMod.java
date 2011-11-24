package com.evosysdev.bukkit.taylorjb.simplemod;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class SimpleMod extends JavaPlugin
{
    // create our listeners
    private final SimpleModPlayerListener playerListener = new SimpleModPlayerListener(this);

    private PermissionHandler permissions; // Permissions handler
    private SimpleModHandler smHandler; // our moderation action handler

    /**
     * Set up Permissions and set up listeners
     */
    public void onEnable()
    {
        PluginManager pm = getServer().getPluginManager();
        smHandler = new SimpleModHandler();

        // if setting up Permissions fails, disable
        if (!setupPermissions())
        {
            pm.disablePlugin(this);
            return;
        }

        // register our events
        pm.registerEvent(Event.Type.PLAYER_LOGIN, playerListener, Priority.High, this);
        pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.High, this);
        System.out.println(getDescription().getName() + " version " + getDescription().getVersion() + " enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player player = (Player) sender;

        // help command
        if (command.getName().equalsIgnoreCase("simplemoderator"))
        {
            if (permissions.has(player, "simplemod"))
            {
                if (permissions.has(player, "simplemod.ban") || permissions.has(player, "simplemod.ban.temp"))
                {
                    sender.sendMessage(ChatColor.AQUA + "/" + getCommand("ban").getName() + ChatColor.WHITE + " | " + ChatColor.BLUE
                            + getCommand("ban").getDescription());
                }

                if (permissions.has(player, "simplemod.ipban"))
                {
                    sender.sendMessage(ChatColor.AQUA + "/" + getCommand("banip").getName() + ChatColor.WHITE + " | " + ChatColor.BLUE
                            + getCommand("banip").getDescription());
                }

                if (permissions.has(player, "simplemod.unban"))
                {
                    sender.sendMessage(ChatColor.AQUA + "/" + getCommand("unban").getName() + ChatColor.WHITE + " | " + ChatColor.BLUE
                            + getCommand("unban").getDescription());
                }

                if (permissions.has(player, "simplemod.mute") || permissions.has(player, "simplemod.mute.temp"))
                {
                    sender.sendMessage(ChatColor.AQUA + "/" + getCommand("mute").getName() + ChatColor.WHITE + " | " + ChatColor.BLUE
                            + getCommand("mute").getDescription());
                }

                if (permissions.has(player, "simplemod.unmute"))
                {
                    sender.sendMessage(ChatColor.AQUA + "/" + getCommand("unmute").getName() + ChatColor.WHITE + " | " + ChatColor.BLUE
                            + getCommand("unmute").getDescription());
                }

                if (permissions.has(player, "simplemod.kick"))
                {
                    sender.sendMessage(ChatColor.AQUA + "/" + getCommand("kick").getName() + ChatColor.WHITE + " | " + ChatColor.BLUE
                            + getCommand("kick").getDescription());
                }
            }
            else
                sender.sendMessage(ChatColor.RED + "You do not have permissions for that!");

            return true;
        }
        else if (command.getName().equalsIgnoreCase("ban"))
        {
            if (permissions.has(player, "simplemod.ban") || permissions.has(player, "simplemod.ban.temp"))
            {
                // if there are actual args
                if (args.length > 0)
                {
                    String playerName = args[0];

                    // if there is a time arg
                    if (args.length > 1)
                    {
                        try
                        {
                            int hours = Integer.parseInt(args[1]);
                            smHandler.ban(playerName, hours);
                            sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' successfully banned for " + hours + " hours!");

                            // if player is online, kick them
                            Player banning = getServer().getPlayer(playerName);
                            if (banning != null) banning.kickPlayer("You have been banned!");
                            
                            Logger.getLogger("minecraft").info(player.getName() + " banned player " + playerName + " for " + hours + " hours.");
                        }
                        catch (NumberFormatException nfe)
                        {
                            sender.sendMessage(ChatColor.RED + "Error parsing hour/length!");
                            return false;
                        }
                    }
                    else
                    {
                        // check for perm ban permissions
                        if (permissions.has(player, "simplemod.ban"))
                        {
                            smHandler.ban(playerName);
                            sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' successfully banned!");
                            Logger.getLogger("minecraft").info(player.getName() + " banned player " + playerName);
                        }
                        else
                            sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                    }

                    return true;
                }
                else
                    return false;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                return true;
            }
        }
        else if (command.getName().equalsIgnoreCase("banip"))
        {
            if (permissions.has(player, "simplemod.ipban"))
            {
                if (args.length > 0)
                {
                    String playerName = args[0];
                    Player banning = getServer().getPlayer(playerName);

                    if (banning != null)
                    {
                        smHandler.banIP(banning.getAddress().getHostString());
                        banning.kickPlayer("You have been banned!");
                        sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' successfully IP banned!");
                        Logger.getLogger("Minecraft").info(player.getName() + " IP banned player " + playerName + "(" + banning.getAddress().getHostString() + ")");
                    }
                    else
                        sender.sendMessage(ChatColor.RED + "Error! Player not online, cannot ban their IP!");
                    
                    return true;
                }
                else
                    return false;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                return true;
            }
        }
        else if (command.getName().equalsIgnoreCase("unban"))
        {
            if (permissions.has(player, "simplemod.unban"))
            {
                if (args.length > 0)
                {
                    if (smHandler.unBan(args[0]))
                    {
                        sender.sendMessage(ChatColor.GREEN + "Player '" + args[0] + "' unbanned!");
                        Logger.getLogger("Minecraft").info(player.getName() + " unbanned player " + args[0]);
                    }
                    else
                        sender.sendMessage(ChatColor.RED + "No player named '" + args[0] + "' is banned.");
                    
                    return true;
                }
                else
                    return false;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                return true;
            }
        }
        else if (command.getName().equalsIgnoreCase("mute"))
        {
            if (permissions.has(player, "simplemod.mute") || permissions.has(player, "simplemod.mute.temp"))
            {
                // if there are actual args
                if (args.length > 0)
                {
                    String playerName = args[0];

                    // if there is a time arg
                    if (args.length > 1)
                    {
                        try
                        {
                            int hours = Integer.parseInt(args[1]);
                            smHandler.mute(playerName, hours);
                            sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' successfully muted for " + hours + " hours!");
                            Logger.getLogger("minecraft").info(player.getName() + " muted player " + playerName + " for " + hours + " hours.");
                        }
                        catch (NumberFormatException nfe)
                        {
                            sender.sendMessage(ChatColor.RED + "Error parsing hour/length!");
                            return false;
                        }
                    }
                    else
                    {
                        // check for perm mute permissions
                        if (permissions.has(player, "simplemod.mute"))
                        {
                            smHandler.mute(playerName);
                            sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' successfully muted!");
                            Logger.getLogger("minecraft").info(player.getName() + " muted player " + playerName);
                        }
                        else
                            sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                    }

                    return true;
                }
                else
                    return false;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                return true;
            }
        }
        else if (command.getName().equalsIgnoreCase("unmute"))
        {
            if (permissions.has(player, "simplemod.unmute"))
            {
                if (args.length > 0)
                {
                    if (smHandler.unMute(args[0]))
                    {
                        sender.sendMessage(ChatColor.GREEN + "Player '" + args[0] + "' unmuted!");
                        Logger.getLogger("Minecraft").info(player.getName() + " unmuted player " + args[0]);
                    }
                    else
                        sender.sendMessage(ChatColor.RED + "No player named '" + args[0] + "' is muted.");
                    
                    return true;
                }
                else
                    return false;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                return true;
            }
        }
        else if (command.getName().equalsIgnoreCase("kick"))
        {
            if (permissions.has(player, "simplemod.kick"))
            {
                if (args.length > 0)
                {
                    String playerName = args[0];
                    Player kicking = getServer().getPlayer(playerName);

                    if (kicking != null)
                    {
                        kicking.kickPlayer("You have been kicked!");
                        sender.sendMessage(ChatColor.GREEN + "Player '" + playerName + "' kicked!");
                        Logger.getLogger("Minecraft").info(player.getName() + " kicked player " + playerName);
                    }
                    else
                        sender.sendMessage(ChatColor.RED + "Error! Player not online, cannot kick!");
                    
                    return true;
                }
                else
                    return false;
            }
            else
            {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                return true;
            }
        }

        return false;
    }

    /**
     * plugin disabled
     */
    public void onDisable()
    {
        Logger.getLogger("Minecraft").info("SimpleModerator disabled.");
    }

    /**
     * Set up the permissions handler
     * 
     * @return if the Permissions are set up correctly
     */
    private boolean setupPermissions()
    {
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.permissions == null)
        {
            if (test != null)
            {
                this.permissions = ((Permissions) test).getHandler();
                return true;
            }
            else
            {
                Logger.getLogger("Minecraft").info("Permission system not detected, disabling SimpleModerator.");
                return false;
            }
        }
        return true;
    }

    /**
     * Get the permissions handler
     * 
     * @return the permissions handler
     */
    public PermissionHandler getPermissions()
    {
        return permissions;
    }

    /**
     * Get our moderation handler
     * 
     * @return our moderation handler
     */
    public SimpleModHandler getHandler()
    {
        return smHandler;
    }
}