package com.monstahhh.croniserver.plugin.advancements.enums;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.AdvancementDisplay;
import eu.endercentral.crazy_advancements.AdvancementDisplay.AdvancementFrame;
import eu.endercentral.crazy_advancements.AdvancementVisibility;
import eu.endercentral.crazy_advancements.NameKey;
import org.bukkit.Material;

import java.util.ArrayList;

public enum AdvancementEnum {

    ROOT(Material.EMERALD_BLOCK, 0, "Cronibet Server", "Made by you!", AdvancementFrame.GOAL, "textures/block/lime_concrete_powder.png", false, false, AdvancementVisibility.ALWAYS),

    BUSINESSMAN(AdvancementEnum.ROOT, Material.HAY_BLOCK, 0, "Businessman", "Craft a Hay Block\n-monstahhhy", AdvancementFrame.GOAL, 1, -3.5F, true, true),
    CRAFTACOOKIE(AdvancementEnum.BUSINESSMAN, Material.COOKIE, 0, "Cookies!", "Cookietastic\n-Troloze", AdvancementFrame.TASK, 2, -4, true, true),
    GUYSIFOUNDTHEFLOWER(AdvancementEnum.BUSINESSMAN, Material.AZURE_BLUET, 0, "Guys I found the flower", "ok guys we need an azure bluet and a dandelion\n-Darmuth", AdvancementFrame.TASK, 2, -3, true, true),
    THELIE(AdvancementEnum.CRAFTACOOKIE, Material.CAKE, 0, "The Lie", "My health goes down, I need a cow\n-Guaka25", AdvancementFrame.TASK, 3, -4, true, true),

    SNOWBALL(AdvancementEnum.ROOT, Material.SNOWBALL, 0, "Finland", "This is Finland\n-iiiomiii", AdvancementFrame.TASK, 1, -1, true, true),
    NOTPACIFIST(AdvancementEnum.SNOWBALL, Material.IRON_SWORD, 0, "Not Pacifist", "RIP PACIFIST\n-DSpectrumNGK", AdvancementFrame.TASK, 2, -2, true, true),
    CREEPER(AdvancementEnum.NOTPACIFIST, Material.CREEPER_HEAD, 0, "Creeper", "Aw Man\n(below y32)\n-Troloze", AdvancementFrame.TASK, 3, -2, true, true),
    GETKILLEDBYSHOVEL(AdvancementEnum.SNOWBALL, Material.WOODEN_SHOVEL, 0, "You Suck", "Die to a Shovel\n-iiiomiii", AdvancementFrame.TASK, 2, 0.25F, true, true),
    GUAKAAPPROVED(AdvancementEnum.GETKILLEDBYSHOVEL, Material.BLUE_CONCRETE_POWDER, 0, "Guaka Approved!", "Guaka broke your legs\n-GabrielArt", AdvancementFrame.TASK, 3, -0.5F, true, true),
    FURNITURYDEATH(AdvancementEnum.GUAKAAPPROVED, Material.LIME_BED, 5, "Furnitury Death", "Get IKEA'd by Cronibet 5 times\n-iiiomiii", AdvancementFrame.CHALLENGE, 4, -1, true, true),
    SACRIFICE(AdvancementEnum.GUAKAAPPROVED, Material.BREAD, 0, "Sacrifice", "Pray for the Holy Baguette\n-Runanova", AdvancementFrame.TASK, 4, -2, true, true),
    NURSERYRHYMES(AdvancementEnum.GUAKAAPPROVED, Material.CHICKEN, 0, "Nursery Rhymes", "AAAAAAA\n-jvsTSX", AdvancementFrame.TASK, 4, 0, true, true),
    KILLEDBYNURSERY(AdvancementEnum.NURSERYRHYMES, Material.COOKED_CHICKEN, 0, "Have A Nice Day!", "One-Way Freedom\n-MyZone03", AdvancementFrame.GOAL, 5.25F, 0, true, true),
    NETHERLANDS(AdvancementEnum.GETKILLEDBYSHOVEL, Material.ORANGE_WOOL, 0, "Welkom in Nederland", "Ga nu dood.\n-Darmuth", AdvancementFrame.TASK, 3, 1, true, true),
    SWEETDREAMS(AdvancementEnum.NETHERLANDS, Material.RED_BED, 0, "Sweet Dreams Are Made Of This", "Hot Dreams :)\n-Guaka25", AdvancementFrame.TASK, 4, 1, true, true),

    BIGBOY(AdvancementEnum.ROOT, Material.BREAD, 10, "Big Boy", "You're officially a big boy\n-iiiomiii", AdvancementFrame.TASK, 1, 2.5F, true, true),
    POUNDS(AdvancementEnum.BIGBOY, Material.ANVIL, 0, "Pounds!", "Pounds!\n-DSpectrumNGK", AdvancementFrame.CHALLENGE, 3, 2, true, true),
    SHINYSTONE(AdvancementEnum.BIGBOY, Material.EMERALD_ORE, 0, "Shiny Stone!", "Mined using high quality tools\n-Guaka25", AdvancementFrame.GOAL, 2, 2, true, true),
    WHY(AdvancementEnum.SHINYSTONE, Material.DIAMOND_ORE, 0, "Why", "Why would you do that?\n-Troloze", AdvancementFrame.TASK, 4, 2, true, true),
    GUAKAHOUSE(AdvancementEnum.BIGBOY, Material.BLUE_CONCRETE, 0, "Guaka51", "Enter Guaka's Base\n-Sebas", AdvancementFrame.CHALLENGE, 2, 3, true, true),

    AGIRLHASNONAME(AdvancementEnum.ROOT, Material.ZOMBIE_HEAD, 0, "A girl has no name", "The many-faced god has requested a name\n-Guaka25", AdvancementFrame.GOAL, 1, 4, true, true),
    LUDICROUS(AdvancementEnum.AGIRLHASNONAME, Material.FEATHER, 0, "Ludicrous Speed", "I Am Speed.\n-Guaka25", AdvancementFrame.TASK, 2, 4, true, true),
    FALLINGUP(AdvancementEnum.LUDICROUS, Material.FIREWORK_ROCKET, 0, "Falling Up", "NAAAAA NAAAAAAA NAAANANANANANANANAAAA NAAAAA\n-Guaka25", AdvancementFrame.CHALLENGE, 3, 4, true, true),
    HACKEDBALLS(AdvancementEnum.FALLINGUP, Material.DRAGON_EGG, 0, "Hacked Balls", "Where's the dragon eggplant?\n-Guaka25", AdvancementFrame.CHALLENGE, 4, 4, true, true),

    SHALOM(AdvancementEnum.ROOT, Material.SHEARS, 0, "Shalom", "I am here to take your foreskin\n-Guaka25", AdvancementFrame.CHALLENGE, -1, 0, true, true);

    private final Material icon;
    private final int required;
    private final AdvancementDisplay.AdvancementFrame frame;
    private final boolean showToast, announceChat;
    private final AdvancementVisibility visibility;
    private Advancement advancement;
    private AdvancementEnum parent;
    private String title, description;
    private String backgroundTexture;
    private float x, y;

    AdvancementEnum(Material icon, int required, String title, String description, AdvancementFrame frame,
                    String backgroundTexture, boolean showToast, boolean announceChat, AdvancementVisibility visibility) {
        this.parent = null;
        this.icon = icon;
        this.required = required;
        this.title = title;
        this.description = description;
        this.frame = frame;
        this.backgroundTexture = backgroundTexture;
        this.showToast = showToast;
        this.announceChat = announceChat;
        this.visibility = visibility;
    }

    AdvancementEnum(AdvancementEnum parent, Material icon, int required, String title, String description,
                    AdvancementFrame frame, float x, float y, boolean showToast, boolean announceChat) {
        this.parent = parent;
        this.icon = icon;
        this.required = required;
        this.title = title;
        this.description = description;
        this.frame = frame;
        this.x = x;
        this.y = y;
        this.showToast = showToast;
        this.announceChat = announceChat;
        this.visibility = AdvancementVisibility.VANILLA;
    }

    public static void registerAdvancements() {
        ArrayList<Advancement> advList = new ArrayList<>();
        for (AdvancementEnum adv : AdvancementEnum.values()) {

            AdvancementDisplay display;
            if (adv.getVisibility() == null) {
                display = new AdvancementDisplay(adv.getIcon(), adv.getTitle(), adv.getDescription(),
                        adv.getFrame(), adv.getToast(), adv.getAnnounce(), AdvancementVisibility.VANILLA);
            } else {
                display = new AdvancementDisplay(adv.getIcon(), adv.getTitle(), adv.getDescription(),
                        adv.getFrame(), adv.getToast(), adv.getAnnounce(), adv.getVisibility());
            }

            if (adv.getParent() == null)
                display.setBackgroundTexture(adv.getBackground());
            else
                display.setCoordinates(adv.getX(), adv.getY());

            adv.advancement = new Advancement(adv.getParent() == null ? null : adv.getParent().getAdvancement(),
                    new NameKey(CustomAdvancements.namespace, adv.name().toLowerCase()), display);
            if (adv.getRequired() != 0) {
                adv.advancement.setCriteria(adv.getRequired());
            }

            advList.add(adv.getAdvancement());
        }
        CustomAdvancements._manager.addAdvancement(advList.toArray(new Advancement[0]));
    }

    public Advancement getAdvancement() {
        return this.advancement;
    }

    public AdvancementEnum getParent() {
        return this.parent;
    }

    public Material getIcon() {
        return this.icon;
    }

    public int getRequired() {
        return this.required;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public AdvancementFrame getFrame() {
        return this.frame;
    }

    public String getBackground() {
        return this.backgroundTexture;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public boolean getToast() {
        return this.showToast;
    }

    public boolean getAnnounce() {
        return this.announceChat;
    }

    public AdvancementVisibility getVisibility() {
        return this.visibility;
    }
}