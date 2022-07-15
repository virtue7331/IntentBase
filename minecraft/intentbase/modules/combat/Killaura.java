package intentbase.modules.combat;

import intentbase.events.Event;
import intentbase.events.listeners.EventMotion;
import intentbase.modules.*;
import intentbase.settings.ModeSetting;
import intentbase.settings.NumberSetting;
import intentbase.util.RenderUtils;
import intentbase.util.Timer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import org.lwjgl.input.Keyboard;
import net.minecraft.entity.*;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.entity.player.EntityPlayer;

public class Killaura extends Module {

    public Timer timer = new Timer();
    public NumberSetting cps = new NumberSetting("CPS", 10, 5, 20, 1);
    public NumberSetting range = new NumberSetting("Reach", 4, 1, 6, 0.1);
    public ModeSetting modeSetting = new ModeSetting("Modes", "Single", "Single", "NoSwing", "Switch");

    public Killaura() {
        super("Killaura", Keyboard.KEY_R, Category.COMBAT);
        this.addSettings(range, modeSetting, cps);
    }

    public void onEnable() {

    }

    public void onDisable() {
        RenderUtils.resetPlayerYaw();
        RenderUtils.resetPlayerPitch();
    }

    public void onEvent(Event event) {
        if (event instanceof EventMotion) {
            if (event.isPre()) {

                EventMotion eventMotion = (EventMotion) event;

                List<Entity> targets = mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());

                targets = targets.stream().filter(e -> e.getDistanceToEntity(mc.thePlayer) < range.getValue() && e != mc.thePlayer && !e.isDead).collect(Collectors.toList());

                targets.sort(Comparator.comparingDouble(e -> ((Entity)e).getDistanceToEntity(mc.thePlayer)));

          //      targets = targets.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());

                if (!targets.isEmpty()) {
                    Entity target = targets.get(0);

                    eventMotion.setYaw(getRotations(target)[0]);
                    eventMotion.setPitch(getRotations(target)[1]);
                    RenderUtils.setCustomYaw(eventMotion.yaw);
                    RenderUtils.setCustomPitch(eventMotion.pitch);

                    if (timer.hasTimeElapsed((long) (1000 / cps.getValue()), true)) {
                        if (modeSetting.is("noSwing")) {
                            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                        } else {
                            mc.thePlayer.swingItem();
                        }
                        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                    }
                } else {
                    RenderUtils.resetPlayerYaw();
                    RenderUtils.resetPlayerPitch();
                }
            }
        }
    }

    public float[] getRotations(Entity e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
                deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }

        return new float[] {yaw, pitch};
    }

}
