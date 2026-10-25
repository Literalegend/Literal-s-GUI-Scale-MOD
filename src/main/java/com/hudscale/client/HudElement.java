package com.hudscale.client;

public final class HudElement {
    public final String id;
    public final String name;
    public final int defaultX, defaultY, previewWidth, previewHeight;
    public int x, y;
    public float scale;

    public HudElement(String id, String name, int x, int y, int w, int h) {
        this.id=id; this.name=name; this.defaultX=x; this.defaultY=y;
        this.previewWidth=w; this.previewHeight=h; this.x=x; this.y=y; this.scale=1.0f;
    }
    public int width(){ return Math.max(8, Math.round(previewWidth*scale)); }
    public int height(){ return Math.max(8, Math.round(previewHeight*scale)); }
    public boolean contains(double mx,double my){ return mx>=x && mx<=x+width() && my>=y && my<=y+height(); }
    public void reset(){ x=defaultX; y=defaultY; scale=1.0f; }
}
