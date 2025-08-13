package com.ruoyi.tools.utils.file;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 图片工具类
 * @Author wocurr.com
 */
public class ImageUtil {

    public static final List<String> IMAGE_EXT_LIST = Arrays.asList("jpg", "jpeg", "png", "tif", "gif", "bmp");

    /**
     * 判断是否是图片
     * @param extName
     * @return
     */
    public static boolean isImage(String extName) {
        if (StringUtils.isBlank(extName)) {
            return false;
        }
        return IMAGE_EXT_LIST.contains(extName.toLowerCase());
    }
}
