package com.castro.mmf.data;

/**
 * TODO
 * Fill in IDS
 */

public enum Price {

    MORT_MYRE_FUNGUS(-1, -1),
    SALVE_GRAVEYARD_TELEPORT(-1, -1),
    LAW_RUNE(-1, -1),
    SOUL_RUNE(-1, -1),
    RING_OF_DUELING(-1, -1);

    private final int id;
    private int price;

    Price(int id, int price) {
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
