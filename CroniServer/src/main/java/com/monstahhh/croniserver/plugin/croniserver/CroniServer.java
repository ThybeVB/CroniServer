package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.HomeCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.InfoCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.WarpCommands;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import com.monstahhh.croniserver.plugin.dangerapi.configapi.Config;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import com.monstahhh.croniserver.plugin.sleep.Sleep;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CroniServer extends JavaPlugin {

    public static Config playerData;

    public static String version = null;
    public static String author = null;

    private DangerAPI dangerApi;
    private Sleep sleep;
    private MrWorldWide mrWorldWide;

    public static Logger logger;

    @Override
    public void onEnable() {

        logger = this.getLogger();

        version = this.getDescription().getVersion();
        author = (this.getDescription().getAuthors().toArray())[0].toString();

        playerData = new Config("plugins/CroniServer", "player_data.yml", this);

        this.enableExtensions();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        this.registerCommands();

        this.remove();
        this.addRecipes();

        System.out.println("[CroniServer] Loaded CroniServer v" + version);
    }

    private void registerCommands() {
        this.getCommand("hub").setExecutor(new WarpCommands());
        this.getCommand("spawn").setExecutor(new WarpCommands());
        this.getCommand("home").setExecutor(new HomeCommand());
        this.getCommand("sethome").setExecutor(new HomeCommand());
        this.getCommand("distance").setExecutor(new DistanceCommand());
        this.getCommand("pdistance").setExecutor((new DistanceCommand()));
        this.getCommand("lol").setExecutor(new DistanceCommand());
        this.getCommand("crinfo").setExecutor(new InfoCommand());
    }

    private void enableExtensions() {
        dangerApi = new DangerAPI(this);
        dangerApi.enable();

        mrWorldWide = new MrWorldWide(this);
        mrWorldWide.enable();

        sleep = new Sleep(this);
        sleep.enable();
    }

    private void remove() {
        List<Recipe> backup = new ArrayList<>();

        Iterator<Recipe> iterator = getServer().recipeIterator();

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
                    case GOLDEN_CARROT:
                        backupIterator.remove();
                }
            }
        }

        getServer().clearRecipes();

        for (Recipe r : backup) {
            getServer().addRecipe(r);
        }
    }

    private void addRecipes () {
        ItemStack goldenCarrot = new ItemStack(Material.GOLDEN_CARROT, 1);

        ShapedRecipe carrotRecipe = new ShapedRecipe(new NamespacedKey(this, "gayboy"), goldenCarrot);

        carrotRecipe.shape("%%%","%C%","%%%");

        carrotRecipe.setIngredient('%', Material.GOLD_INGOT);
        carrotRecipe.setIngredient('C', Material.CARROT);

        this.getServer().addRecipe(carrotRecipe);

        ItemStack glisterLemon = new ItemStack(Material.GLISTERING_MELON_SLICE, 1);

        ShapedRecipe lemonRecipe = new ShapedRecipe(new NamespacedKey(this, "gayboy2"), glisterLemon);

        lemonRecipe.shape("%%%","%C%","%%%");

        lemonRecipe.setIngredient('%', Material.GOLD_INGOT);
        lemonRecipe.setIngredient('C', Material.MELON_SLICE);

        this.getServer().addRecipe(lemonRecipe);
    }

    @Override
    public void onDisable() {

        dangerApi.disable();
        mrWorldWide.disable();
        sleep.disable();

        System.out.println("[CroniServer] Disabled CroniServer");
    }
}
