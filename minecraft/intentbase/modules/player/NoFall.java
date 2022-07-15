package intentbase.modules.player;

import intentbase.events.Event;
import intentbase.events.listeners.EventUpdate;
import intentbase.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Keyboard.KEY_J, Category.PLAYER);
    }

    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            if (mc.thePlayer.fallDistance > 2) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
        }
    }
}
