package com.castro.mmf.main;

import com.castro.mmf.data.Location;
import com.castro.mmf.data.Painting;
import com.castro.mmf.data.Setting;
import com.castro.mmf.main.framework.Task;
import com.castro.mmf.tasks.Banking;
import com.castro.mmf.tasks.Pick;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.canvas.paint.Painter;
import org.osbot.rs07.listener.MessageListener;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.util.ArrayList;

@ScriptManifest(author = "Castro", name = "cMMF", info = "The original MMF script", logo = "https://i.imgur.com/HAmh8Dv.png", version = 0.1)
public class Main extends Script implements MessageListener, Painter {

    ArrayList<Task> tasks = new ArrayList<Task>();

    @Override
    public void onStart() {
        Painting.startTime = System.currentTimeMillis();
        Location.Spot.CURRENT.setArea(Location.Spot.ONE.getArea());
        Location.Tile.CURRENT.setPosition(Location.Tile.ONE.getPosition());
        if (getInventory().contains("Law rune", "Soul rune")) {
            Setting.teleport = new String[]{"Law rune", "Soul rune"};
            Setting.usingRunes = true;
        }
        tasks.add(new Banking(this));
        tasks.add(new Pick(this));
    }

    @Override
    public int onLoop() throws InterruptedException {
        tasks.forEach(Task::run);
        return 250;
    }

    @Override
    public void onMessage(Message msg) {
        if (msg.getMessage().contains("You pick a mushroom from the log")) {
            Painting.picked++;
        }
    }

    @Override
    public void onPaint(Graphics2D g) {
        Font titleFont = new Font("Eigenvector font", 1, 14);
        g.setFont(titleFont);
        g.drawString(getName() + " (v" + getVersion() + ") by " + getAuthor(), 27, 240);

        Font statFont = new Font("Eigenvector font", 0, 12);
        g.setFont(statFont);
        long runtime = System.currentTimeMillis() - Painting.startTime;
        g.drawString("Runtime: " + Painting.formatTime(runtime), 27, 260);
        g.drawString("Status: " + Painting.status, 27, 280);
        int pickedPerHour = (int) (Painting.picked / ((System.currentTimeMillis() - Painting.startTime) / 3600000.0D));
        g.drawString("Mort myre fungus picked: " + Painting.picked + " (" + pickedPerHour + " per hour)", 27, 300);


        g.setColor(Color.PINK);
        Point mP = getMouse().getPosition();
        g.drawLine(mP.x, 0, mP.x, 500);
        g.drawLine(0, mP.y, 800, mP.y);
    }
}
