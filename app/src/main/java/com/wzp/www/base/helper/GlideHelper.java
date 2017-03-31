package com.wzp.www.base.helper;

import android.graphics.Color;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.wzp.www.base.R;
import com.wzp.www.base.bean.constant.Global;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by wengzhipeng on 17/3/7.
 */

public class GlideHelper {
    public static final LruBitmapPool LRU_BITMAP_POOL = new LruBitmapPool((int)
            (Runtime.getRuntime().maxMemory()) / 8);
    /**
     * 圆形转换器
     */
    public static final Transformation CROP_CIRCLE_TRANSFORMATION = new
            CropCircleTransformation(LRU_BITMAP_POOL);
    /**
     * 方形转换器
     */
    public static final Transformation CROP_SQUARE_TRANSFORMATION = new
            CropSquareTransformation(LRU_BITMAP_POOL);
    /**
     * 圆角转换器
     */
    public static final Transformation ROUND_CORNERS_TRANSFORMATION = new
            RoundedCornersTransformation(LRU_BITMAP_POOL, 6, 6);
    /**
     * 颜色覆盖转换器
     */
    public static final Transformation COLOR_FILTER_TRANSFORMATION = new
            ColorFilterTransformation(LRU_BITMAP_POOL, Color.GRAY);
    /**
     * 置灰转换器
     */
    public static final Transformation GRAY_SCALE_TRANSFORMATION = new
            GrayscaleTransformation(LRU_BITMAP_POOL);
    /**
     * 毛玻璃转换器
     */
    public static final Transformation BLUR_TRANSFORMATION = new BlurTransformation
            (Global.APP_CONTEXT, LRU_BITMAP_POOL);

    public static DrawableRequestBuilder configDefault(DrawableTypeRequest request) {
        return request.placeholder(R.drawable.ic_generic_default)// 默认占位图片
                .error(R.drawable.ic_generic_error)// 默认失败图片
                .thumbnail(0.2f)// 先加载一定比例缩略图
                .animate(android.R.anim.fade_in)// 图片加载完成后动画效果
                .centerCrop()// 图片填充模式
//                .bitmapTransform(CROP_CIRCLE_TRANSFORMATION)// 转换器
                ;
    }

}
