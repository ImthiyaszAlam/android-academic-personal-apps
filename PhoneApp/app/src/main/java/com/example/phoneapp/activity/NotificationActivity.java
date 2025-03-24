package com.example.phoneapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phoneapp.R;
import com.google.firebase.database.core.Tag;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotificationActivity extends AppCompatActivity {

    private EditText titleET, messageET;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // Views initialization
        titleET = findViewById(R.id.titleET);
        messageET = findViewById(R.id.messageET);
        sendBtn = findViewById(R.id.sendNotificationBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleET.getText().toString().trim();
                String message = messageET.getText().toString().trim();

                if (title.isEmpty() || message.isEmpty()) {
                    Toast.makeText(NotificationActivity.this, "Fill both fields", Toast.LENGTH_SHORT).show();
                } else {
                    sendNotification(title, message);
                }
            }

        });
    }
    private void sendNotification(String title, String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String FCM_API = "https://fcm.googleapis.com/fcm/send";
                    String serverKey = "BJ66W80DDT1NqI7Z2sr9wkMlHuNtchxfIdLe9Zhcvhh0E5HkD1hC5OUmNyMP-OkP-s6BVPqYiErGfDZTS4ItoQU";  // Replace with your actual server key
                    String contentType = "application/json";

                    JSONObject notification = new JSONObject();
                    JSONObject notificationBody = new JSONObject();

                    notificationBody.put("title", title);
                    notificationBody.put("body", message);

                    notification.put("to", "/topics/all");  // Change if you're targeting specific devices
                    notification.put("notification", notificationBody);

                    URL url = new URL(FCM_API);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setUseCaches(false);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Authorization", "key=" + serverKey);
                    connection.setRequestProperty("Content-Type", contentType);

                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(notification.toString().getBytes("UTF-8"));
                    outputStream.close();

                    int responseCode = connection.getResponseCode();
                    String responseMessage = connection.getResponseMessage();
                    Log.d("FCM_RESPONSE", "Response Code: " + responseCode + ", Message: " + responseMessage);

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        Log.d("FCM_RESPONSE", "Response: " + response.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NotificationActivity.this, "Notification sent successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        StringBuilder errorResponse = new StringBuilder();
                        String errorLine;

                        while ((errorLine = errorReader.readLine()) != null) {
                            errorResponse.append(errorLine);
                        }

                        Log.e("FCM_ERROR", "Error Response: " + errorResponse.toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NotificationActivity.this, "Failed to send notification", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NotificationActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

}
