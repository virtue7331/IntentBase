package intentbase.modules.player;

import intentbase.events.Event;
import intentbase.events.listeners.*;
import intentbase.modules.Module;
import org.lwjgl.input.Keyboard;
import intentbase.util.Printer;

public class Derp extends Module {
    private float yaw;

    public Derp() {
        super("Derp", Keyboard.KEY_NONE, Category.PLAYER);
    }

    public void onEnable() {
        if (mc.gameSettings.thirdPersonView > 0) {
            mc.gameSettings.thirdPersonView = 0;
            Printer.print("This will crash the game in F5");
        }
    }

    public void onEvent(Event e) {
        if (e instanceof EventMotion) {
            if (e.isPre()) {

                EventMotion event = (EventMotion) e;

                this.yaw -= 22.0f;
                if (this.yaw <= -180.0f) {
                    this.yaw = 180.0f;
                }
                this.mc.thePlayer.renderYawOffset = this.yaw;
                this.mc.thePlayer.rotationYawHead = this.yaw;
            }
        } if (e instanceof EventKey) {
            int code = ((EventKey) e).code;

            if (code == Keyboard.KEY_F5) {
                toggle();
                Printer.print("This will crash the game in F5");
            }
        }
    }
}
