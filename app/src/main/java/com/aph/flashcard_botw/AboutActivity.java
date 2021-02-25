package com.aph.flashcard_botw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Intent srcIntent = getIntent();

        TextView appNametextView = findViewById(R.id.appNameTextView);
        TextView versionTextView = findViewById(R.id.versionTextView);
        try {
            versionTextView.setText(getAppVersion());
            appNametextView.setText(getAppName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //get the app version dynamically
    public String getAppVersion() throws PackageManager.NameNotFoundException {
        PackageManager manager = getApplicationContext().getPackageManager();
        PackageInfo info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
        return info.versionName;
    }

    public String getAppName() throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo = this.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : this.getString(stringId);
    }
}