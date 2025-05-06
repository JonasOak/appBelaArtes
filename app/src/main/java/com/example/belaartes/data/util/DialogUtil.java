package com.example.belaartes.data.util;

import android.app.AlertDialog;

public interface DialogUtil {
    void showConfirmationDialog(String title, String message, Runnable onConfirm);
}
