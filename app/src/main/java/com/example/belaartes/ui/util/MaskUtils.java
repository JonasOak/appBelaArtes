package com.example.belaartes.ui.util;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MaskUtils {
    public static TextWatcher insertPhoneMask(EditText editText) {
        return new TextWatcher() {
            private boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll("[^\\d]", "");

                String formatted = "";
                if (str.length() > 0) {
                    formatted += "(" + str.substring(0, Math.min(2, str.length()));
                    if (str.length() >= 2) {
                        formatted += ") ";
                        formatted += str.substring(2, Math.min(7, str.length()));
                    }
                    if (str.length() >= 7) {
                        formatted += "-" + str.substring(7, Math.min(11, str.length()));
                    }
                }

                isUpdating = true;
                editText.setText(formatted);
                editText.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

    public static TextWatcher insertCpfMask(EditText editText) {
        return new TextWatcher() {
            private boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll("[^\\d]", "");

                String formatted = "";
                if (str.length() > 0) {
                    formatted += str.substring(0, Math.min(3, str.length()));
                    if (str.length() >= 3) {
                        formatted += "." + str.substring(3, Math.min(6, str.length()));
                    }
                    if (str.length() >= 6) {
                        formatted += "." + str.substring(6, Math.min(9, str.length()));
                    }
                    if (str.length() >= 9) {
                        formatted += "-" + str.substring(9, Math.min(11, str.length()));
                    }
                }

                isUpdating = true;
                editText.setText(formatted);
                editText.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }
}
