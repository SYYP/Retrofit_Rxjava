package www.yipengyu.com.myproject.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;

/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/27 0027
 * Time: 15:38
 */
public class BitmapUtils {

    /**
     * 字节数组转bitmap
     */
    public static Bitmap bytesBitmap(byte[] bytes) {
        Bitmap bitmap = null;
        if (bytes != null && bytes.length > 0) {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return bitmap;
    }
    /**
     * bitmap 转 字节数组（不压缩）
     *
     * @param bitmap bitmap
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        return bttmapBytes(bitmap, 100);
    }
    /**
     * bitmap 转 字节数组（可压缩）
     *
     * @param bitmap
     * @param quality(0-100)
     * @return
     */
    public static byte[] bttmapBytes(Bitmap bitmap, int quality) {
        if (bitmap == null) return new byte[1];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();

    }

    /**
     * 把两个位图覆盖合成为一个位图，以底层位图的长宽为基准
     *
     * @param backBitmap  在底部的位图
     * @param frontBitmap 盖在上面的位图
     * @return 合并后Bitmap
     */
    public static Bitmap mergeBitmap(Bitmap backBitmap, Bitmap frontBitmap) {

        if (backBitmap == null || backBitmap.isRecycled()){
            return frontBitmap;
        }else if (frontBitmap == null || frontBitmap.isRecycled()){
            return backBitmap;
        }

        Bitmap bitmap = backBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Rect baseRect = new Rect(0, 0, backBitmap.getWidth(), backBitmap.getHeight());
        Rect frontRect = new Rect(0, 0, frontBitmap.getWidth(), frontBitmap.getHeight());
        canvas.drawBitmap(frontBitmap, frontRect, baseRect, null);
        return bitmap;
    }

    /**
     * 将2个bitmap的字节数组合并
     * @param backBytes 在底部的位图
     * @param frontBytes 盖在上面的位图
     * @return 字节数组
     */
    public static byte[] mergeBytes(byte[] backBytes,byte[] frontBytes){
        if (backBytes == null || backBytes.length<1){
            return frontBytes;
        }else if (frontBytes == null || frontBytes.length<1){
            return backBytes;
        }else {
            return bitmap2Bytes(bytesBitmap(backBytes),bytesBitmap(frontBytes));
        }
    }

    /**
     * 2个bitmap 转 字节数组（不压缩）
     *
     * @param backBitmap  在底部的位图
     * @param frontBitmap 盖在上面的位图
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(Bitmap backBitmap, Bitmap frontBitmap) {
        return bttmapBytes(mergeBitmap(backBitmap,frontBitmap), 100);
    }
}


