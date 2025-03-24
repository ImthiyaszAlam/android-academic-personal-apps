package com.imthiyas.contactme.util;

import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

public class ValidationUtil {

    // Validate gender selection (should be one of the valid options)
    public static boolean validateGender(int genderSelection) {
        return genderSelection == 1 || genderSelection == 2 || genderSelection == 3;
    }

    // Validate age (only allow numbers and age > 0)
    public static boolean validateAge(String ageStr, Context context) {
        try {
            int age = Integer.parseInt(ageStr);
            if (age > 0) {
                return true;
            } else {
                Toast.makeText(context, "Age must be greater than 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Invalid age input", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // Validate selfie (check if file exists and is an image)
    public static boolean validateSelfie(String selfiePath) {
        return selfiePath != null && !selfiePath.isEmpty();
    }

    // General input validation for empty fields
    public static boolean isNotEmpty(EditText editText, String errorMessage, Context context) {
        String input = editText.getText().toString().trim();
        if (input.isEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
