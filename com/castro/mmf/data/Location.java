package com.castro.mmf.data;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

public class Location {

    public enum Spot {

        CURRENT(null),
        ONE(new Area(3420, 3436, 3423, 3438));

        private Area area;

        Spot(Area area) {
            this.area = area;
        }

        public Area getArea() {
            return area;
        }

        public void setArea(Area area) {
            this.area = area;
        }

    }

    public enum Tile{

        CURRENT(null),
        ONE(new Position(3421,3437,0));

        private Position position;

        Tile(Position position){
            this.position = position;
        }

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }
    }

    public enum Misc {

        CLAN_WARS(new Area(3346, 3180, 3396, 3132)),
        SWAMP(new Area(3402, 3481, 3496, 3321));

        private final Area area;

        Misc(Area area) {
            this.area = area;
        }

        public Area getArea() {
            return area;
        }
    }
}