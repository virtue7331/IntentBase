package intentbase.modules.movement;

import intentbase.events.Event;
import intentbase.events.listeners.EventUpdate;
import intentbase.modules.Module;
import org.lwjgl.input.Keyboard;
import net.minecraft.world.WorldSettings;

import java.security.Key;

public class Sprint extends Module {

    WorldSettings world;

    public Sprint() {
        super("Sprint", Keyboard.KEY_H, Category.MOVEMENT);
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (event.isPre()) {

                if (mc.thePlayer.moveForward > 0 && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally ) {
                    if (mc.thePlayer.getFoodStats().getFoodLevel() >= 7 || !mc.thePlayer.getFoodStats().needFood());
                    {
                        mc.thePlayer.setSprinting(true);
                    }
                }
            }
        }
    }

    public void onEnable() {

    }

    public void onDisable() {
        mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.isKeyDown());
    }
}
