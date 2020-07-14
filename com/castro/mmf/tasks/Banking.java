package com.castro.mmf.tasks;

import com.castro.mmf.data.Location;
import com.castro.mmf.data.Painting;
import com.castro.mmf.data.Setting;
import com.castro.mmf.main.framework.Sleep;
import com.castro.mmf.main.framework.Task;
import org.osbot.rs07.api.filter.AreaFilter;
import org.osbot.rs07.api.filter.NameFilter;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;

import javax.sound.sampled.FloatControl;

public class Banking extends Task {
    public Banking(MethodProvider api) {
        super(api);
    }

    @Override
    public boolean validate() {
        RS2Object fungusOnLog = api.getObjects().closest(new NameFilter<>("Fungus on log"), new AreaFilter<>(Location.Spot.CURRENT.getArea()));
        return api.getSkills().getDynamic(Skill.PRAYER) == 0 && api.getInventory().isFull()
                ||
                api.getSkills().getDynamic(Skill.PRAYER) == 0 && fungusOnLog == null && !Location.Misc.CLAN_WARS.getArea().contains(api.myPlayer())
                ||
                !api.getInventory().contains(Setting.teleport) && Location.Misc.CLAN_WARS.getArea().contains(api.myPlayer())
                ||
                api.getInventory().isFull()
                ||
                api.getSkills().getDynamic(Skill.HITPOINTS) <= Setting.healthToTeleportAt && !api.getInventory().contains(Setting.teleport);
    }

    @Override
    public void execute() {
        if (!Location.Misc.CLAN_WARS.getArea().contains(api.myPlayer())) {
            Painting.status = "Teleporting to Clan Wars";
            try {
                MethodProvider.sleep(MethodProvider.random(250, 750));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (api.getEquipment().interactWithNameThatContains("Clan Wars", "Ring of dueling")) {
                Sleep.sleepUntil(() -> Location.Misc.CLAN_WARS.getArea().contains(api.myPlayer()), 15000);
            }
            return;
        }
        if (!api.getBank().isOpen()) {
            Painting.status = "Opening bank";
            try {
                if (api.getBank().open()) {
                    try {
                        MethodProvider.sleep(MethodProvider.random(650, 1250));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(api.getInventory().contains("Mort myre fungus")){
            Painting.status = "Depositing Mort myre fungus";
            try {
                MethodProvider.sleep(MethodProvider.random(450,950));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(api.getBank().depositAll("Mort myre fungus")){
                try {
                    MethodProvider.sleep(MethodProvider.random(650,950));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        if (api.getEquipment().getItem(x -> x.getName().contains("Ring of dueling")) == null) {
            if (!api.getInventory().contains(x -> x.getName().contains("Ring of dueling"))) {
                Painting.status = "Withdrawing Ring of dueling";
                try {
                    MethodProvider.sleep(MethodProvider.random(650, 1250));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (api.getBank().withdraw(x -> x.getName().contains("Ring of dueling"), 1)) {
                    Sleep.sleepUntil(() -> api.getInventory().contains(x -> x.getName().contains("Ring of dueling")), 1250);
                }
                return;
            }
            Item ringOfDueling = api.getInventory().getItem(x -> x.getName().contains("Ring of dueling"));
            if (ringOfDueling != null) {
                Painting.status = "Equipping " + ringOfDueling.getName();
                try {
                    MethodProvider.sleep(MethodProvider.random(650, 1250));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (ringOfDueling.interact("Wear")) {
                    Sleep.sleepUntil(() -> api.getEquipment().getItem(new NameFilter<>("Ring of dueling")) != null, 1250);
                }
            }
            return;
        }
        if (!api.getInventory().contains(Setting.teleport)) {
            for (int i = 0; i < Setting.teleport.length; i++) {
                Painting.status = "Withdrawing " + Setting.teleport[i];
                try {
                    MethodProvider.sleep(MethodProvider.random(450, 950));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                api.getBank().withdraw(Setting.teleport[i], i + 1);
            }
            return;
        }
    }
}
