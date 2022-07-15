package intentbase.modules.render;

import intentbase.util.Printer;
import intentbase.events.Event;
import intentbase.IntentBase;
import intentbase.events.listeners.*;
import intentbase.modules.Module;
import intentbase.settings.ModeSetting;
import org.lwjgl.input.Keyboard;

public class Hud extends Module {

    static ModeSetting hudRGBMode = new ModeSetting("RBG Mode", "Off", "SimpleRGB", "Rainbow", "Off");

    public static boolean simpleRGBEnabled;
    public static boolean rainbowEnabled;

    public Hud() {
        super("HUD", Keyboard.KEY_LBRACKET, Category.RENDER);
        this.addSettings(hudRGBMode);
        toggled = true;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (event.isPre()) {
                if (hudRGBMode.is("SimpleRGB")) {
                    simpleRGBEnabled = true;
                    rainbowEnabled = false;
                } else if (hudRGBMode.is("Rainbow")) {
                    simpleRGBEnabled = false;
                    rainbowEnabled = true;
                } else {
                    simpleRGBEnabled = false;
                    rainbowEnabled = false;
                }
            }
        }

        if (event instanceof EventRenderGUI) {
            IntentBase.INSTANCE.hud.draw();
        }
    }
}
