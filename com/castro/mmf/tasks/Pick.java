package com.castro.mmf.tasks;

import com.castro.mmf.data.Location;
import com.castro.mmf.data.Painting;
import com.castro.mmf.data.Setting;
import com.castro.mmf.main.framework.Sleep;
import com.castro.mmf.main.framework.Task;
import org.osbot.rs07.api.filter.AreaFilter;
import org.osbot.rs07.api.filter.NameFilter;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.script.MethodProvider;

public class Pick extends Task {
    public Pick(MethodProvider api) {
        super(api);
    }

    @Override
    public boolean validate() {
        RS2Object fungusOnLog = api.getObjects().closest(new AreaFilter<>(Location.Spot.CURRENT.getArea()), new NameFilter<>("Fungi on log"));
        return Location.Misc.CLAN_WARS.getArea().contains(api.myPlayer()) && api.getInventory().contains(Setting.teleport) && api.getEquipment().getItem(x -> x.getName().contains("Ring of dueling")) != null
                ||
                Location.Misc.SWAMP.getArea().contains(api.myPlayer()) && !api.getInventory().isFull() && api.getSkills().getDynamic(Skill.PRAYER) == 0 && fungusOnLog != null
                ||
                Location.Misc.SWAMP.getArea().contains(api.myPlayer()) && !api.getInventory().isFull() && api.getSkills().getDynamic(Skill.PRAYER) > 0
                ||
                Location.Misc.INSIDE_CLAN_WARS_PORTAL.getArea().contains(api.myPlayer())
                ||
                Location.Misc.SALVE_GRAVEYARD.getArea().contains(api.myPlayer()) && api.getSkills().getDynamic(Skill.HITPOINTS) > Setting.healthToTeleportAt;
    }

    @Override
    public void execute() {
        if (api.getBank().isOpen()) {
            Painting.status = "Closing bank";
            try {
                MethodProvider.sleep(MethodProvider.random(250, 450));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (api.getBank().close()) {
                Sleep.sleepUntil(() -> !api.getBank().isOpen(), 1250);
            }
            return;
        }
        RS2Object freeForAllPortal = api.getObjects().closest(new NameFilter<>("Free-for-all portal"), new AreaFilter<>(Location.Misc.CLAN_WARS.getArea()));
        if (Location.Misc.CLAN_WARS.getArea().contains(api.myPlayer()) && api.getInventory().contains(Setting.teleport)) {
            if (freeForAllPortal != null && freeForAllPortal.getPosition().distance(api.myPlayer()) <= 10) {
                Painting.status = "Entering " + freeForAllPortal.getName();
                try {
                    MethodProvider.sleep(MethodProvider.random(350, 750));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (freeForAllPortal.interact("Enter")) {
                    Sleep.sleepUntil(() -> Location.Misc.INSIDE_CLAN_WARS_PORTAL.getArea().contains(api.myPlayer()), 15000);
                }
                return;
            } else {
                Painting.status = "Walking towards Free-for-all portal";
                api.getWalking().webWalk(new Position(3354, 3164, 0));
            }
            return;
        }
        if (Location.Misc.INSIDE_CLAN_WARS_PORTAL.getArea().contains(api.myPlayer())) {
            try {
                MethodProvider.sleep(MethodProvider.random(250, 650));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Setting.usingRunes) {
                Painting.status = "Casting Salve graveyard teleport spell";
                if (api.getMagic().castSpell(Spells.ArceuusSpells.SALVE_GRAVEYARD_TELEPORT)) {
                    Sleep.sleepUntil(() -> Location.Misc.SALVE_GRAVEYARD.getArea().contains(api.myPlayer()), 15000);
                }
            } else {
                Painting.status = "Breaking Salve graveyard teleport tablet";
                Item salveGraveyardTeleport = api.getInventory().getItem("Salve graveyard teleport");
                if (salveGraveyardTeleport != null && salveGraveyardTeleport.interact("Break")) {
                    Sleep.sleepUntil(() -> Location.Misc.SALVE_GRAVEYARD.getArea().contains(api.myPlayer()), 15000);
                }
            }
            return;
        }
        final RS2Widget ENTER_SWAMP_WARNING = api.getWidgets().getWidgetContainingText("Mort Myre is a dangerous");
        if (ENTER_SWAMP_WARNING != null) {
            final RS2Widget TOGGLE = api.getWidgets().get(580, 20);
            if (TOGGLE != null) {
                Painting.status = "Toggling off warning";
                try {
                    MethodProvider.sleep(MethodProvider.random(250, 450));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (TOGGLE.interact("Off/On")) {
                    try {
                        MethodProvider.sleep(MethodProvider.random(250, 450));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            final RS2Widget ENTER = api.getWidgets().getWidgetContainingText("Enter the swamp");
            if (ENTER != null) {
                Painting.status = "Entering Mort Myre swamp";
                try {
                    MethodProvider.sleep(MethodProvider.random(250, 450));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (ENTER.interact("Yes")) {
                    Sleep.sleepUntil(() -> Location.Misc.SWAMP.getArea().contains(api.myPlayer()), 7500);
                }
            }
            return;
        }
        if (Location.Misc.SALVE_GRAVEYARD.getArea().contains(api.myPlayer())) {
            RS2Object gate = api.getObjects().closest("Gate");
            if (gate != null) {
                Painting.status = "Handling gate";
                try {
                    MethodProvider.sleep(MethodProvider.random(250, 650));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (gate.isVisible() && gate.interact("Open")) {
                    Sleep.sleepUntil(() -> Location.Misc.SWAMP.getArea().contains(api.myPlayer()), 7500);
                    return;
                }
                Painting.status = "Walking towards " + gate.getName();
                api.getWalking().webWalk(gate.getPosition());
            }
            return;
        }
        RS2Object fungusOnLog = api.getObjects().closest(new AreaFilter<>(Location.Spot.CURRENT.getArea()), new NameFilter<>("Fungi on log"));
        if (fungusOnLog != null) {
            Painting.status = "Picking " + fungusOnLog.getName();
            if (fungusOnLog.interact("Pick")) {
                Sleep.sleepUntil(() -> api.myPlayer().getAnimation() == 827, 2500);
            }
            return;
        }
        if (!Location.Spot.CURRENT.getArea().contains(api.myPlayer())) {
            Painting.status = "Walking to blooming spot";
            api.getWalking().webWalk(Location.Spot.CURRENT.getArea());
            return;
        }
        if (!api.myPlayer().getPosition().equals(Location.Tile.CURRENT.getPosition())) {
            Painting.status = "Moving to optimal blooming position";
            if (new Position(Location.Tile.CURRENT.getPosition()).interact(api.bot, "Walk here")) {
                try {
                    MethodProvider.sleep(MethodProvider.random(125, 250));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (api.getEquipment().getItem("Silver sickle (b)").hover()) {
                    try {
                        MethodProvider.sleep(MethodProvider.random(350, 850));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return;
        }
        Painting.status = "Casting bloom";
        if (api.getEquipment().interactWithNameThatContains("Bloom", "Silver sickle (b)")) {
            try {
                MethodProvider.sleep(MethodProvider.random(1050, 1550));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
