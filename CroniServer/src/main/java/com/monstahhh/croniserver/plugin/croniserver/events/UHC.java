package com.monstahhh.croniserver.plugin.croniserver.events;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UHC implements Listener {

    private JavaPlugin plugin;

    public UHC(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        remove();
        addRecipes();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("uhc3") || event.getEntity().getWorld().getName().equalsIgnoreCase("uhc3_nether")) {
            if (event.getEntityType() == EntityType.GHAST) {
                ItemStack gold = new ItemStack(Material.GOLD_INGOT, 1);
                event.getDrops().clear();
                event.getDrops().add(gold);
            }
        }
    }


    private void remove() {
        List<Recipe> backup = new ArrayList<>();

        Iterator<Recipe> iterator = plugin.getServer().recipeIterator();

        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            backup.add(recipe);
        }

        Iterator<Recipe> backupIterator = backup.iterator();

        while (backupIterator.hasNext()) {
            Recipe recipe = backupIterator.next();
            if (recipe != null) {
                switch (recipe.getResult().getType()) {
                    case GLISTERING_MELON_SLICE:
                        backupIterator.remove();
                    case GOLDEN_CARROT:
                        backupIterator.remove();
                }
            }
        }

        plugin.getServer().clearRecipes();

        for (Recipe r : backup) {
            plugin.getServer().addRecipe(r);
        }
    }

    private void addRecipes() {
        ItemStack goldenCarrot = new ItemStack(Material.GOLDEN_CARROT, 1);
        ShapedRecipe carrotRecipe = new ShapedRecipe(new NamespacedKey("croniserver", "gayboy"), goldenCarrot);

        carrotRecipe.shape("%%%", "%C%", "%%%");

        carrotRecipe.setIngredient('%', Material.GOLD_INGOT);
        carrotRecipe.setIngredient('C', Material.CARROT);

        plugin.getServer().addRecipe(carrotRecipe);

        ItemStack glisterLemon = new ItemStack(Material.GLISTERING_MELON_SLICE, 1);
        ShapedRecipe lemonRecipe = new ShapedRecipe(new NamespacedKey("croniserver", "gayboy2"), glisterLemon);

        lemonRecipe.shape("%%%", "%C%", "%%%");

        lemonRecipe.setIngredient('%', Material.GOLD_INGOT);
        lemonRecipe.setIngredient('C', Material.MELON_SLICE);

        plugin.getServer().addRecipe(lemonRecipe);
    }

}
