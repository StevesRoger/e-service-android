package org.jarvis.code.util;

/**
 * Created by ki.kao on 12/16/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;

//import com.bumptech.glide.load.resource.gif.GifDrawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

    // images mime type
    public static final String GIF_MIME_TYPE = "image/gif";
    public static final String JPEG_MIME_TYPE = "image/jpeg";
    public static final String PNG_MIME_TYPE = "image/png";


    public static Bitmap createBitmap(@NonNull Bitmap source, int x, int y, int width, int height, float scale) {
        Matrix m = new Matrix();
        if (scale > 0.f) {
            m.setScale(scale, scale);
        }
        return Bitmap.createBitmap(source, x, y, width, height, m, true);
    }

    public static String bitmapToBase64String(@NonNull Bitmap bitmap) {
        String result;

        ByteArrayOutputStream bitmapBytesStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bitmapBytesStream);

        try {
            result = Base64.encodeToString(bitmapBytesStream.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            result = null;
        }

        return result;
    }

    public static Bitmap createScaledBitmap(@NonNull Bitmap unscaledBitmap, int dstWidth) {
        final int srcWidth = unscaledBitmap.getWidth();
        final int srcHeight = unscaledBitmap.getHeight();

        final float srcAspect = (float) srcWidth / (float) srcHeight;
        final int dstHeight = (int) (dstWidth / srcAspect);

        Rect srcRect = new Rect(0, 0, srcWidth, srcHeight);
        Rect dstRect = new Rect(0, 0, dstWidth, dstHeight);

        Bitmap scaledBitmap = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    public static String getTempImagesPath(@NonNull Context context) {
        return context.getExternalCacheDir().getAbsolutePath();
    }


    // can change bitmap compress percent as you want
    public static void writeBitmapToFile(@NonNull String filePath, @NonNull Bitmap bitmap, @NonNull Bitmap.CompressFormat compressFormat) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(compressFormat, 70, out);
            out.flush();
            out.close();
        } catch (Exception ignore) {
            Log.w("Cannot store bitmap: %s", filePath);
        }
    }

   /* public static void writeGifToFile(@NonNull String path, @NonNull GifDrawable drawable) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(drawable.getData());
            out.flush();
            out.close();
        } catch (Exception ignore) {
            Log.w("Cannot store gif: %s", path);
        }
    }*/

    public static boolean isGifType(@NonNull String mimeType) {
        return TextUtils.equals(mimeType, GIF_MIME_TYPE);
    }

    public static Bitmap.CompressFormat getCompressFormat(@NonNull String mimeType) {
        switch (mimeType) {
            case JPEG_MIME_TYPE:
                return Bitmap.CompressFormat.JPEG;
            case PNG_MIME_TYPE:
                return Bitmap.CompressFormat.PNG;
        }
        // default should be png
        return Bitmap.CompressFormat.PNG;
    }

    public static String getFileSuffixForImage(@NonNull String mimeType) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
    }

    public static String getMimeForFilePath(@NonNull String path) {
        if (!TextUtils.isEmpty(path)) {
            String extension = MimeTypeMap.getFileExtensionFromUrl(path);
            if (extension != null) {
                return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
        }
        return PNG_MIME_TYPE;
    }

    @Nullable
    public static String getTempPath(@NonNull Context context, @NonNull String mimeType) {
        String fileSuffix = "." + getFileSuffixForImage(mimeType);
        File file;
        try {
            file = File.createTempFile("file", fileSuffix, context.getExternalCacheDir());
            return file.getAbsolutePath();
        } catch (IOException e) {
            Log.e(e.getMessage(), "Failed to create a file.");
        }
        return null;
    }
}