package intentbase.ui;

import intentbase.IntentBase;
import intentbase.events.listeners.EventRenderGUI;
import intentbase.modules.Module;
import intentbase.modules.render.Hud;
import intentbase.util.RainbowUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.util.Comparator;

public class HUD {

    Minecraft mc = Minecraft.getMinecraft();

    Hud hudModule = new Hud();

    public void draw() {
        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRendererObj;

        IntentBase.INSTANCE.modules.sort(Comparator.comparingInt(m ->
                mc.fontRendererObj.getStringWidth(((Module) m).name))
                .reversed()
        );

        GlStateManager.pushMatrix();
        GlStateManager.scale(1, 1, 1);
        fr.drawString("FPS: " + Minecraft.getDebugFPS(), 4, 500, (Hud.simpleRGBEnabled ? RainbowUtil.getRainbow(6, 0.8f, 3) : (Hud.rainbowEnabled ? RainbowUtil.getRainbow(6, 0.8f, 5, 0 * 175) : 0xff00ffff)));
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate(4, 4, 0);
        GlStateManager.scale(2, 2, 1);
        GlStateManager.translate(-4, -4, 0);
        fr.drawString("Intent Base", 4, 4, (Hud.simpleRGBEnabled ? RainbowUtil.getRainbow(6, 0.8f, 3) : (Hud.rainbowEnabled ? RainbowUtil.getRainbow(6, 0.8f, 5, 0 * 175) : 0xff00ffff)));
        GlStateManager.popMatrix();

        int count = 0;

        for (Module m : IntentBase.INSTANCE.modules) {
            if (!m.toggled || m.name.equals("HUD")) {
                continue;
            }

            double offset = count * (fr.FONT_HEIGHT + 6);

            Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.name) - 10, offset, sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, 6 + fr.FONT_HEIGHT + offset, Hud.simpleRGBEnabled ? RainbowUtil.getRainbow(6, 0.8f, 3) : (Hud.rainbowEnabled ? RainbowUtil.getRainbow(6, 0.8f, 5, count * 175) : 0xff00ffff));
            Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, offset, sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset, 0x90000000);
            fr.drawStringWithShadow(m.name, sr.getScaledWidth() - fr.getStringWidth(m.name) - 4, 4 + offset, Hud.simpleRGBEnabled ? RainbowUtil.getRainbow(6, 0.8f, 3) : -1);

            count++;
        }
    }

    public void callEvent() {
        IntentBase.INSTANCE.onEvent(new EventRenderGUI());
    }

}
