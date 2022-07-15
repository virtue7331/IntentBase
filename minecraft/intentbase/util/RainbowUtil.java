package intentbase.util;

import java.awt.*;

public class RainbowUtil {

    public static int getRainbow(float seconds, float saturation, float brightness) {
        float hue = (System.currentTimeMillis() % (int) (seconds * 4000) / (float) (seconds * 4000f));
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

    public static int getRainbow(float seconds, float saturation, float brightness, long index) {
        float hue = ((System.currentTimeMillis() + index) % (int) (seconds * 4000) / (float) (seconds * 4000f));
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

}
