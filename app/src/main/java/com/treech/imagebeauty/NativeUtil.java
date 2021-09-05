package com.treech.imagebeauty;

public class NativeUtil {

    static  {
        // Used to load the 'cpp' library on application startup.
            System.loadLibrary("cpp");
    }

    public static native int[] beauty(int[] buffer,int width,int height);
}
