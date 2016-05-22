package uuid.fhnw.ch.thisis.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * @author Samuel Schläpfer <samuel.schlaepfer@fhnw.ch>
 */
public class ImageUtils {
    public static Bitmap decodeDownsampledBitmap(Resources res, int imageId, int viewWidth, int viewHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, imageId, options);

        return BitmapFactory.decodeResource(res, imageId, calculateOptions(options, viewWidth, viewHeight));
    }

    public static Bitmap decodeDownsampledBitmap(File file, int viewWidth, int viewHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);

        return BitmapFactory.decodeFile(file.getPath(), calculateOptions(options, viewWidth, viewHeight));
    }

    public static BitmapFactory.Options calculateOptions(BitmapFactory.Options options, int width, int height){
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        BitmapFactory.Options result = new BitmapFactory.Options();
        result.inJustDecodeBounds = false;

        double widthRatio = width == 0 ? 1 : imageWidth / (double) width;
        double heightRatio = height == 0 ? 1 : imageHeight / (double) height;

        if (widthRatio > heightRatio){
            result.inSampleSize = (int) widthRatio;
        }
        else{
            result.inSampleSize = (int) heightRatio;
        }

        return result;
    }
}
