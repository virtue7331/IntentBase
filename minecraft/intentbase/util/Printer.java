package intentbase.util;

import intentbase.IntentBase;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class Printer {

    public static void print(String message) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.thePlayer.addChatMessage(new ChatComponentText("[§a" + IntentBase.INSTANCE.chatName + "§r] " + message));
    }

}
