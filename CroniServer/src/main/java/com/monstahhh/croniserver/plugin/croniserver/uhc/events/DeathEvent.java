package com.monstahhh.croniserver.plugin.croniserver.uhc.events;

import fr.xephi.authme.api.v3.AuthMeApi;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.user.User;
import net.luckperms.api.track.DemotionResult;
import net.luckperms.api.track.Track;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Random;

public class DeathEvent implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getWorld().getName().startsWith("uhctest")) {
            Random random = new Random();

            if (event.getEntityType() == EntityType.WITHER_SKELETON) {
                event.getDrops().clear();
                if (random.nextInt(100) <= 33) {
                    ItemStack coal = new ItemStack(Material.COAL, 1);
                    event.getDrops().add(coal);
                }
                if (random.nextInt(1000) <= 35) {
                    ItemStack witherSkull = new ItemStack(Material.WITHER_SKELETON_SKULL, 1);
                    event.getDrops().add(witherSkull);
                }
                if (random.nextInt(10) <= 5) {
                    ItemStack oneBone = new ItemStack(Material.BONE, 1);
                    event.getDrops().add(oneBone);
                } else {
                    ItemStack twoBones = new ItemStack(Material.BONE, 2);
                    event.getDrops().add(twoBones);
                }
            }
            if (event.getEntityType() == EntityType.GHAST) {
                event.getDrops().clear();
                if (random.nextInt(10) <= 5) {
                    ItemStack gold = new ItemStack(Material.GOLD_INGOT, 1);
                    event.getDrops().add(gold);
                }
                if (random.nextInt(10) <= 5) {
                    ItemStack onePowder = new ItemStack(Material.GUNPOWDER, 1);
                    event.getDrops().add(onePowder);
                } else {
                    ItemStack twoPowder = new ItemStack(Material.GUNPOWDER, 2);
                    event.getDrops().add(twoPowder);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        if (p.getWorld().getName().startsWith("uhctest")) {
            AuthMeApi api = AuthMeApi.getInstance();
            api.forceLogin(p);
            p.sendMessage(ChatColor.DARK_GREEN + "You have been automatically logged in due to being the UHC world.");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();

        if (!p.getWorld().getName().startsWith("uhctest")) return;

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();
            User u = api.getUserManager().getUser(p.getDisplayName());
            Track track = api.getTrackManager().getTrack("default");

            if (track != null && u != null) {
                ImmutableContextSet contextSet = ImmutableContextSet.empty();
                DemotionResult result = track.demote(u, contextSet);

                if (result.wasSuccessful()) {
                    api.getUserManager().saveUser(u);
                    p.sendMessage(ChatColor.DARK_GRAY + "-------------------------------------------");
                    p.sendMessage(ChatColor.WHITE + p.getDisplayName() + " is no more :v");
                    p.sendMessage(ChatColor.DARK_RED + "See you in the nursery");
                    p.sendMessage(ChatColor.DARK_RED + "    - MyZone03");
                    p.sendMessage(ChatColor.DARK_GRAY + "-------------------------------------------");
                } else {
                    p.sendMessage(ChatColor.DARK_RED + result.toString());
                }
            }
        }
    }
}
