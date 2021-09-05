#include <jni.h>
#include <string>

extern "C" JNIEXPORT jintArray JNICALL Java_com_treech_imagebeauty_NativeUtil_beauty
        (JNIEnv *env, jclass jcls, jintArray buffer, jint width, jint height) {
    jint *source = (env)->GetIntArrayElements(buffer, NULL);
    float brightnessRatio = 0.2f;
    float contrastRatio = 0.2f;
    int brightness = (int) (255 * brightnessRatio);
    int contrast = (int) (1.0f + contrastRatio);
    int newSize = width * height;
    int a, r, g, b;
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            int color = source[j * width + i];
            a = color >> 24;
            r = (color >> 16) & 0xFF;
            g = (color >> 8) & 0xFF;
            b = color & 0xFF;

            //亮度
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
            source[j * width + i] = (a << 24) | (r << 16) | (g << 8) | b;
        }
    }
    jintArray result = (env)->NewIntArray(newSize);
    (env)->SetIntArrayRegion(result, 0, newSize, source);
    (env)->ReleaseIntArrayElements(buffer, source, 0);
    return result;
}