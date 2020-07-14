package com.castro.mmf.tasks;

import com.castro.mmf.data.Location;
import com.castro.mmf.data.Painting;
import com.castro.mmf.data.Setting;
import com.castro.mmf.main.framework.Sleep;
import com.castro.mmf.main.framework.Task;
import org.osbot.rs07.api.filter.ActionFilter;
import org.osbot.rs07.api.filter.AreaFilter;
import org.osbot.rs07.api.filter.NameFilter;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
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
        RS2Object exitPortal = api.getObjects().closest(new NameFilter<>("Portal"), new ActionFilter<>("Exit"));
        return Location.Misc.CLAN_WARS.getArea().contains(api.myPlayer()) && api.getInventory().contains(Setting.teleport) && api.getEquipment().getItem(x -> x.getName().contains("Ring of dueling")) != null
                ||
                Location.Misc.SWAMP.getArea().contains(api.myPlayer()) && !api.getInventory().isFull() && api.getSkills().getDynamic(Skill.PRAYER) == 0 && fungusOnLog != null
                ||
                Location.Misc.SWAMP.getArea().contains(api.myPlayer()) && !api.getInventory().isFull() && api.getSkills().getDynamic(Skill.PRAYER) > 0
                ||
                exitPortal != null;
    }

    @Override
    public void execute() {
        if (api.getBank().isOpen()) {
            Painting.status = "Closing bank";
            try {
                MethodProvider.sleep(MethodProvider.random(650, 2750));
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
                    MethodProvider.sleep(MethodProvider.random(450, 1250));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (freeForAllPortal.interact("Enter")) {
                    Sleep.sleepUntil(() -> !Location.Misc.CLAN_WARS.getArea().contains(api.myPlayer()), 15000);
                }
                return;
            } else {
                Painting.status = "Walking towards Free-for-all portal";
                api.getWalking().webWalk(new Position(3354, 3164, 0));
            }
            return;
        }
        RS2Object exitPortal = api.getObjects().closest(new NameFilter<>("Portal"), new ActionFilter<>("Exit"));
        if (exitPortal != null) {
            try {
                MethodProvider.sleep(MethodProvider.random(650, 1250));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Setting.usingRunes) {
                Painting.status = "Casting Salve graveyard teleport spell";
                if (api.getMagic().castSpell(Spells.ArceuusSpells.SALVE_GRAVEYARD_TELEPORT)) {
                    Sleep.sleepUntil(() -> Location.Misc.SWAMP.getArea().contains(api.myPlayer()), 15000);
                }
            } else {
                Painting.status = "Breaking Salve graveyard teleport tablet";
                Item salveGraveyardTeleport = api.getInventory().getItem("Salve graveyard teleport");
                if (salveGraveyardTeleport != null && salveGraveyardTeleport.interact("Break")) {
                    Sleep.sleepUntil(() -> Location.Misc.SWAMP.getArea().contains(api.myPlayer()), 15000);
                }
            }
            return;
        }
        RS2Object fungusOnLog = api.getObjects().closest(new AreaFilter<>(Location.Spot.CURRENT.getArea()), new NameFilter<>("Fungi on log"));
        if(fungusOnLog != null){
            Painting.status = "Picking "+fungusOnLog.getName();
            if(fungusOnLog.interact("Pick")){
                try {
                    MethodProvider.sleep(MethodProvider.random(450,750));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        if (!Location.Spot.CURRENT.getArea().contains(api.myPlayer())) {
            Painting.status = "Walking to blooming spot";
            api.getWalking().webWalk(Location.Spot.CURRENT.getArea());
            return;
        }
        if(!api.myPlayer().getPosition().equals(Location.Tile.CURRENT.getPosition())){
            Painting.status = "Moving to optimal blooming position";
            if(new Position(Location.Tile.CURRENT.getPosition()).interact(api.bot,"Walk here")){
                try {
                    MethodProvider.sleep(MethodProvider.random(350,850));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        Painting.status = "Casting bloom";
        if(api.getEquipment().interactWithNameThatContains("Bloom","Silver sickle (b)")){
            Sleep.sleepUntil(()-> fungusOnLog != null, 1750);
        }

    }
}
