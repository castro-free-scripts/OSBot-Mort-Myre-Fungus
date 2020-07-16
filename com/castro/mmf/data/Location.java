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

    public enum Tile {

        CURRENT(null),
        ONE(new Position(3421, 3437, 0));

        private Position position;

        Tile(Position position) {
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

        CLAN_WARS(new Area(3124, 3619, 3155, 3646)),
        POOL_OF_REFRESHMENT(new Area(3127, 3634, 3130, 3639)),
        SALVE_GRAVEYARD(new Area(
                new int[][]{
                        {3413, 3456},
                        {3418, 3456},
                        {3419, 3457},
                        {3421, 3457},
                        {3423, 3459},
                        {3425, 3459},
                        {3427, 3457},
                        {3431, 3457},
                        {3432, 3456},
                        {3436, 3456},
                        {3437, 3457},
                        {3440, 3457},
                        {3441, 3457},
                        {3442, 3458},
                        {3445, 3458},
                        {3448, 3456},
                        {3449, 3457},
                        {3453, 3457},
                        {3454, 3456},
                        {3455, 3456},
                        {3460, 3461},
                        {3460, 3463},
                        {3460, 3482},
                        {3432, 3482},
                        {3426, 3469}
                }
        )),
        SWAMP(new Area(
                new int[][]{
                        {3414, 3456},
                        {3418, 3456},
                        {3419, 3457},
                        {3421, 3457},
                        {3423, 3459},
                        {3425, 3459},
                        {3427, 3457},
                        {3431, 3457},
                        {3432, 3456},
                        {3435, 3456},
                        {3436, 3456},
                        {3437, 3457},
                        {3441, 3457},
                        {3442, 3458},
                        {3445, 3458},
                        {3447, 3456},
                        {3448, 3456},
                        {3449, 3457},
                        {3453, 3457},
                        {3454, 3456},
                        {3455, 3456},
                        {3460, 3461},
                        {3461, 3448},
                        {3467, 3441},
                        {3487, 3417},
                        {3488, 3383},
                        {3490, 3359},
                        {3490, 3344},
                        {3396, 3347},
                        {3400, 3392},
                        {3403, 3400},
                        {3401, 3407},
                        {3407, 3416},
                        {3401, 3425},
                        {3405, 3437},
                        {3400, 3446}
                }
        ));

        private final Area area;

        Misc(Area area) {
            this.area = area;
        }

        public Area getArea() {
            return area;
        }
    }
}