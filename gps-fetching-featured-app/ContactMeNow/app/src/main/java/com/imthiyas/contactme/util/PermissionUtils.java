package com.imthiyas.contactme.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static final int MY_PERMISSIONS_REQUEST_CODE = 100; // You can use any value

    public static boolean checkPermission(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, String[] permission) {
        ActivityCompat.requestPermissions(activity,
                permission,
                MY_PERMISSIONS_REQUEST_CODE);
    }
    public static void requestPermission(Activity activity, String permission) {
        ActivityCompat.requestPermissions(activity,
                new String[]{permission},
                MY_PERMISSIONS_REQUEST_CODE);
    }
    public static boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean checkAndRequestPermission(Activity activity, String permission) {
        if (!checkPermission(activity, permission)) {
            requestPermission(activity, permission);
            return false;
        }
        return true;
    }

    // Handle the result of the permission request
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                  @NonNull int[] grantResults, OnPermissionResultListener listener) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                if (listener != null) {
                    listener.onPermissionGranted();
                }
            } else {
                // Permission denied
                if (listener != null) {
                    listener.onPermissionDenied();
                }
            }
        }
    }

    public interface OnPermissionResultListener {
        void onPermissionGranted();
        void onPermissionDenied();
    }
}
