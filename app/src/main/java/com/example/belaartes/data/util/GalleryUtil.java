package com.example.belaartes.data.util;

public interface GalleryUtil {
    String convertImageToBase64(byte[] imageBytes);
    void openGallery();

    void checkPermissionGallery();
}
