package com.monstahhh.croniserver.plugin.uhc4;

import com.monstahhh.croniserver.plugin.uhc4.commands.GivePlayersUhcPlayerCommand;
import com.monstahhh.croniserver.plugin.uhc4.events.DeathEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Objects;

public class UHC {

    private JavaPlugin _plugin;

    public UHC(JavaPlugin plugin) {
        this._plugin = plugin;
    }

    public void enable() {
        _plugin.getServer().getPluginManager().registerEvents(new DeathEvent(), _plugin);

        Objects.requireNonNull(_plugin.getCommand("giveplayersuhcplayer")).setExecutor(new GivePlayersUhcPlayerCommand());
        addRecipe();

        System.out.println("[CroniServer] Enabled UHC");
    }

    private void addRecipe() {
        ItemStack dragonBreath = new ItemStack(Material.DRAGON_BREATH, 1);
        ShapedRecipe recipe = new ShapedRecipe(dragonBreath);

        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.STRENGTH));
        potion.setItemMeta(meta);

        recipe.shape("GGG", "GSG", "GGG");
        recipe.setIngredient('G', Material.GOLD_NUGGET);
        recipe.setIngredient('S', potion);

        _plugin.getServer().addRecipe(recipe);
    }

    public void disable() {
        System.out.println("[CroniServer] Disabled UHC");
    }
}
