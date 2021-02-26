package net.nend.customevent.mopub;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

import net.nend.android.mopub.customevent.NendMediationSettings;

/*
  This sample uses location data as an option for ad supply.
*/
public class InterstitialVideoActivity extends AppCompatActivity {

    private static final String MOPUB_AD_UNIT_ID = "YOUR_VIDEO_INTERSTITIAL_UNIT_ID";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MoPubInterstitial interstitial;

    private final MoPubInterstitial.InterstitialAdListener adListener = new MoPubInterstitial.InterstitialAdListener() {
        @Override
        public void onInterstitialLoaded(MoPubInterstitial interstitial) {
            showToast("Interstitial Load Success: " + interstitial, Toast.LENGTH_SHORT);
        }

        @Override
        public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
            showToast("Interstitial Load Failure: " + interstitial + "\n errorCode: " + errorCode, Toast.LENGTH_LONG);
        }

        @Override
        public void onInterstitialShown(MoPubInterstitial interstitial) {
            showToast("Interstitial shown: " + interstitial, Toast.LENGTH_SHORT);
        }

        @Override
        public void onInterstitialClicked(MoPubInterstitial interstitial) {
            showToast("Interstitial clicked: " + interstitial, Toast.LENGTH_SHORT);
        }

        @Override
        public void onInterstitialDismissed(MoPubInterstitial interstitial) {
            showToast("Interstitial dismissed: " + interstitial, Toast.LENGTH_SHORT);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_video);

        // If you use location in your app, but would like to disable location passing.
//        MoPub.setLocationAwareness(MoPub.LocationAwareness.DISABLED);

        findViewById(R.id.bt_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitial == null) {
                    interstitial = new MoPubInterstitial(InterstitialVideoActivity.this, MOPUB_AD_UNIT_ID);
                    interstitial.setInterstitialAdListener(adListener);

                    NendMediationSettings settings = new NendMediationSettings.Builder()
                            .setUserId("userId")
                            .setAge(18)
                            .setBirthday(2000,1,1)
                            .setGender(NendMediationSettings.GENDER_FEMALE)
                            .addCustomFeature("customIntParam1", 123)
                            .addCustomFeature("customDoubleParam1", 123.45)
                            .addCustomFeature("customStringParam1", "testString")
                            .addCustomFeature("customBooleanParam1", true)
                            .build();
                }
                interstitial.load();
            }
        });

        findViewById(R.id.bt_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitial != null && interstitial.isReady()) {
                    interstitial.show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (interstitial != null) {
            interstitial.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!verifyPermissions()) {
            requestPermissions();
        }
    }

    private void showToast(String message, int duration) {
        Toast.makeText(InterstitialVideoActivity.this, message, duration).show();
    }

    private boolean verifyPermissions() {
        int state = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        return state == PackageManager.PERMISSION_GRANTED;
    }

    private void showRequestPermissionDialog() {
        ActivityCompat.requestPermissions(InterstitialVideoActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (shouldRequest) {
            Snackbar.make(findViewById(R.id.base_layout), "Location permission is needed for get the last Location. It's a demo that uses location data.", Snackbar.LENGTH_LONG).setAction(android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRequestPermissionDialog();
                }
            }).show();
        } else {
            showRequestPermissionDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Snackbar.make(findViewById(R.id.base_layout), "User interaction was cancelled.", Snackbar.LENGTH_LONG).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(findViewById(R.id.base_layout), "Permission granted.", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(findViewById(R.id.base_layout), "Permission denied.", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
