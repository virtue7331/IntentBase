package intentbase.modules.movement;

import intentbase.events.Event;
import intentbase.events.listeners.EventKey;
import intentbase.events.listeners.EventUpdate;
import intentbase.modules.Module;
import intentbase.util.Printer;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {
    public Fly() {
        super("Fly", Keyboard.KEY_F, Category.MOVEMENT);
    }

    boolean spedUp;

    public void onEnable() {
        mc.timer.timerSpeed = 1.0f;
        spedUp = false;
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
        spedUp = false;
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (event.isPre()) {
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.thePlayer.motionY = 1;
                    mc.timer.timerSpeed = 1.0f;
                } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.thePlayer.motionY = -1;
                    mc.timer.timerSpeed = 1.0f;
                } else {
                    mc.thePlayer.motionY = 0;
                } if (spedUp) {
                    mc.timer.timerSpeed = 5.0f;
                } else {
                    mc.timer.timerSpeed = 1.0f;
                }

            }
        } if (event instanceof EventKey) {
            int code = ((EventKey)event).code;

            if (code == Keyboard.KEY_LMENU) {
                if (spedUp) {
                    Printer.print("Slowing down");
                    spedUp = false;
                    return;
                } else {
                    Printer.print("Speeding up");
                    spedUp = true;
                }
            }
        }
    }
}
