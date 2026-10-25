package com.hudscale.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class HudEditorScreen extends Screen {
    private HudElement selected;
    private boolean wasDown;
    private int grabX, grabY;

    public HudEditorScreen() { super(Text.literal("HUD Editor")); }

    @Override protected void init() { HudRegistry.initialize(width,height); }

    @Override public void render(DrawContext c,int mouseX,int mouseY,float delta) {
        long window=client.getWindow().getHandle();
        boolean down=GLFW.glfwGetMouseButton(window,GLFW.GLFW_MOUSE_BUTTON_LEFT)==GLFW.GLFW_PRESS;
        if(down&&!wasDown){
            selected=null;
            var list=HudRegistry.all();
            for(int i=list.size()-1;i>=0;i--){ HudElement e=list.get(i); if(e.contains(mouseX,mouseY)){selected=e;grabX=mouseX-e.x;grabY=mouseY-e.y;break;} }
        }
        if(down&&selected!=null){ selected.x=mouseX-grabX; selected.y=mouseY-grabY; }
        if(!down&&wasDown) HudConfig.save(HudRegistry.all());
        wasDown=down;

        c.fill(0,0,width,34,0x99000000);
        c.drawCenteredTextWithShadow(textRenderer,Text.literal("HUD Editor"),width/2,7,0xFFFFFF);
        c.drawCenteredTextWithShadow(textRenderer,Text.literal("Drag an element • select it, then use - / = to resize • R reset • ESC save & close"),width/2,20,0xCFCFCF);
        for(HudElement e:HudRegistry.all()){
            int border=e==selected?0xFFFFFFFF:0xAAFFFFFF;
            c.fill(e.x,e.y,e.x+e.width(),e.y+e.height(),0x44000000);
            c.fill(e.x,e.y,e.x+e.width(),e.y+1,border); c.fill(e.x,e.y+e.height()-1,e.x+e.width(),e.y+e.height(),border);
            c.fill(e.x,e.y,e.x+1,e.y+e.height(),border); c.fill(e.x+e.width()-1,e.y,e.x+e.width(),e.y+e.height(),border);
            String label=e.name+"  "+Math.round(e.scale*100)+"%";
            c.drawTextWithShadow(textRenderer,Text.literal(label),e.x+4,e.y+4,0xFFFFFF);
        }
        super.render(c,mouseX,mouseY,delta);
    }

    @Override public boolean keyPressed(int keyCode,int scanCode,int modifiers) {
        if(selected!=null){
            if(keyCode==GLFW.GLFW_KEY_MINUS){selected.scale=Math.max(.25f,selected.scale-.05f);HudConfig.save(HudRegistry.all());return true;}
            if(keyCode==GLFW.GLFW_KEY_EQUAL){selected.scale=Math.min(4f,selected.scale+.05f);HudConfig.save(HudRegistry.all());return true;}
            if(keyCode==GLFW.GLFW_KEY_R){selected.reset();HudConfig.save(HudRegistry.all());return true;}
        }
        return super.keyPressed(keyCode,scanCode,modifiers);
    }
    @Override public void close(){ HudConfig.save(HudRegistry.all()); super.close(); }
    @Override public boolean shouldPause(){return false;}
    @Override public boolean shouldCloseOnEsc(){return true;}
}
