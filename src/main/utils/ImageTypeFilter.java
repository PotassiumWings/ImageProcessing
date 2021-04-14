package main.utils;

import main.exceptions.TypeNotSupportedException;

import java.awt.image.BufferedImage;

public class ImageTypeFilter {
    public static void checkImageType(int type) throws TypeNotSupportedException {
        if (type == BufferedImage.TYPE_3BYTE_BGR ||
            type == BufferedImage.TYPE_4BYTE_ABGR ||
            type == BufferedImage.TYPE_4BYTE_ABGR_PRE ||
            type == BufferedImage.TYPE_BYTE_GRAY ||
            type == BufferedImage.TYPE_BYTE_BINARY ||
            type == BufferedImage.TYPE_BYTE_INDEXED) {
            return;
        }
        throw new TypeNotSupportedException(type);
    }
}
