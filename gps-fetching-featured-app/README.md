# Contact Form Submission App

This is an Android app designed to collect user details through a contact form with specific input validation, audio recording, geolocation, and timestamp features. The app collects responses to three questions, saves the answers in JSON format, and displays the submitted results on a separate page.

### Assignment Overview:
- **Q1:** Select your gender (Male, Female, Other)
- **Q2:** Enter your age (Numeric input only)
- **Q3:** Upload your selfie (Camera capture only)
  
### Features Implemented:
1. **Input Validation:**
   - Q1: Dropdown (Male, Female, Other)
   - Q2: Numeric input with number-only keypad
   - Q3: Camera capture only (no gallery uploads)
   - Q2 is mandatory; Q1 and Q3 are optional.
   
2. **Audio Recording, GPS, and Timestamp:**
   - Audio recording starts when answering Q1 and stops when hitting the submit button.
   - Audio is saved in `.wav` format.
   - Geolocation (latitude, longitude) is captured upon submission.
   - Submission timestamp is captured in the format `Y-m-d H:i:s`.

3. **Data Saving & Display:**
   - Answers are saved as a JSON file: `{"Q1":2, "Q2":25, "Q3": "xyz.png", "recording": "abc.wav", "gps": "26.12323,86.23235", "submit_time":"2023-07-04 18:30:45"}`.
   - Selfie image (XYZ.png) and recorded audio (ABC.wav) are stored in local storage.
   - Results are displayed in a table on a separate page, showing all submitted answers.

4. **Multi-Page Flow:**
   - One question per page: Q1 on Page-1, Q2 on Page-2, Q3 on Page-3, Submit on Page-4.
   - NEXT button to validate answers and move to the next page.
   - Option to go back and change previous answers using navigation arrows.

---

## Installation


---

### **Option 1: Clone the Repository**

To clone this repository to your local machine, follow these steps:

1. Open your terminal (Command Prompt or Git Bash).
2. Run the following command:

   ```bash
   git clone https://github.com/ImthiyaszAlam/Contact-Form-Submission-App
   ```

---


3. Navigate to the project directory:


4. Open the project in Android Studio and run the app on your device or emulator.

---

### Option 2: Download the Zip Folder

1. Go to the this repo(git clone https://github.com/ImthiyaszAlam/Contact-Form-Submission-App).
2. Click the "Code" button and select "Download ZIP."
3. Extract the ZIP folder to a location on your computer.
4. Open the extracted folder in Android Studio and run the app on your device or emulator.

---

## How to Use

1. **Q1:** Select your gender (Male, Female, or Other).
2. **Q2:** Enter your age (Numeric input only).
3. **Q3:** Take a selfie using the front camera (gallery uploads are not allowed).
4. Press the **Submit** button to save and display your answers.
   - The audio recording will automatically start when answering Q1 and stop when you hit Submit.
   - Your location (latitude, longitude) and submission time will be captured when you submit the form.

The results will be shown on a separate page with all the submitted details.

---

## Files Saved

- JSON file with the responses: `answers.json`
- Selfie image: `xyz.png`
- Recorded audio: `abc.wav`

These files are stored locally on your device.

# Technologies Used
---
- Android SDK – Core framework for building Android apps.
- Java – Programming language used for app development.
- Camera API – Used for capturing selfies via the device's front camera.
- GPS API – Captures the device's latitude and longitude for geolocation.
- MediaRecorder – Records audio in .wav format.
- Room Database – Stores data (JSON, images, audio) locally on the device.
- Date & Time API – Captures the submission time in the Y-m-d H:i:s format.
- XML Layouts – Used for designing the app’s user interface.
