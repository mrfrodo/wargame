package com.frodo.wargame.domain;

public class HexTile {
    private int q; // axial coordinate q
    private int r; // axial coordinate r
    private String terrain;

    public HexTile(int q, int r, String terrain) {
        this.q = q;
        this.r = r;
        this.terrain = terrain;
    }

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    public String getTerrain() {
        return terrain;
    }
}
