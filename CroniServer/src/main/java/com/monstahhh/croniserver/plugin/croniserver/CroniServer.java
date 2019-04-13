package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.HubCommand;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import com.monstahhh.croniserver.plugin.croniserver.events.ServerListener;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;

public final class CroniServer extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new ServerListener(), this);

        this.getCommand("hub").setExecutor(new HubCommand());
        this.getCommand("distance").setExecutor(new DistanceCommand());
        this.getCommand("pdistance").setExecutor((new DistanceCommand()));
        this.getCommand("lol").setExecutor(new DistanceCommand());

        remove(Material.GLISTERING_MELON_SLICE);
        remove(Material.GOLDEN_CARROT);

        ItemStack goldenCarrot = new ItemStack(Material.GOLDEN_CARROT, 1);

        ShapedRecipe carrotRecipe = new ShapedRecipe(goldenCarrot);

        carrotRecipe.shape("%%%","%C%","%%%");

        carrotRecipe.setIngredient('%', Material.GOLD_INGOT);
        carrotRecipe.setIngredient('C', Material.CARROT);

        getServer().addRecipe(carrotRecipe);

        ItemStack glisterLemon = new ItemStack(Material.GLISTERING_MELON_SLICE, 1);

        ShapedRecipe lemonRecipe = new ShapedRecipe(glisterLemon);

        lemonRecipe.shape("%%%","%C%","%%%");

        lemonRecipe.setIngredient('%', Material.GOLD_INGOT);
        lemonRecipe.setIngredient('C', Material.MELON_SLICE);

        getServer().addRecipe(lemonRecipe);
    }

    void remove(Material m) {

        Iterator<Recipe> it = getServer().recipeIterator();
        Recipe recipe;
        while(it.hasNext())
        {
            recipe = it.next();
            if (recipe != null && recipe.getResult().getType() == m)
            {
                it.remove();
            }
        }
    }
}
