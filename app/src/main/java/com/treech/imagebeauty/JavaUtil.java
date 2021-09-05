package com.treech.imagebeauty;

import android.graphics.Bitmap;
import android.graphics.Color;

public class JavaUtil {

    //亮度
    public static float brightnessRatio = 0.2f;

    //对比度
    public static float contrastRatio = 0.2f;

    public static Bitmap beauty(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int brightness = (int) (255 * brightnessRatio);
        int contrast = (int) (1.0f + contrastRatio);
        int a, r, g, b;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = bitmap.getPixel(i, j);
                a = Color.alpha(color);
                r = Color.red(color);
                g = Color.green(color);
                b = Color.blue(color);

                //美白
                int ri = r + brightness;
                int gi = g + brightness;
                int bi = b + brightness;

                //边缘检测
                r = ri > 255 ? 255 : (ri < 0 ? 0 : ri);
                g = gi > 255 ? 255 : (gi < 0 ? 0 : gi);
                b = bi > 255 ? 255 : (bi < 0 ? 0 : bi);

                //对比度
                ri = r + 128;
                gi = g + 128;
                bi = b + 128;

                ri = (int) (ri * contrast);
                gi = (int) (gi * contrast);
                bi = (int) (bi * contrast);

                ri = ri - 128;
                gi = gi - 128;
                bi = bi - 128;

                //边缘检测
                r = ri > 255 ? 255 : (ri < 0 ? 0 : ri);
                g = gi > 255 ? 255 : (gi < 0 ? 0 : gi);
                b = bi > 255 ? 255 : (bi < 0 ? 0 : bi);
                result.setPixel(i, j, Color.argb(a, r, g, b));
            }
        }
        return result;
    }
}
