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

import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedAdListener;
import com.mopub.mobileads.MoPubRewardedAds;

import net.nend.android.mopub.customevent.NendMediationSettings;

import java.util.Set;

/*
  This sample uses location data as an option for ad supply.
*/
public class RewardedVideoActivity extends AppCompatActivity {

    private static final String MOPUB_AD_UNIT_ID = "YOUR_REWARD_UNIT_ID";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private final MoPubRewardedAdListener moPubRewardedAdListener = new MoPubRewardedAdListener() {
        @Override
        public void onRewardedAdLoadSuccess(@NonNull String adUnitId) {
            showToast("Load Success adUnitId: " + adUnitId, Toast.LENGTH_SHORT);
        }

        @Override
        public void onRewardedAdLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode moPubErrorCode) {
            showToast("Load Failure adUnitId: " + adUnitId + "\n errorCode: " + moPubErrorCode, Toast.LENGTH_LONG);
        }

        @Override
        public void onRewardedAdStarted(@NonNull String adUnitId) {
            showToast("Reward ad started adUnitId: " + adUnitId, Toast.LENGTH_SHORT);
        }

        @Override
        public void onRewardedAdShowError(@NonNull String adUnitId, @NonNull MoPubErrorCode moPubErrorCode) {
            showToast("Rewarded ad show error adUnitId: " + adUnitId + "\n errorCode: " + moPubErrorCode, Toast.LENGTH_LONG);
        }

        @Override
        public void onRewardedAdClicked(@NonNull String adUnitId) {
            showToast("Ad Clicked adUnitId: " + adUnitId, Toast.LENGTH_SHORT);
        }

        @Override
        public void onRewardedAdClosed(@NonNull String adUnitId) {
            showToast("Ad Closed adUnitId: " + adUnitId, Toast.LENGTH_SHORT);
        }

        @Override
        public void onRewardedAdCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {
            showToast("Rewarded ad completed adUnitId: " + adUnitIds.toString() + "\n Reward_label: " + reward.getLabel() + "\n Reward_amount: " + reward.getAmount(), Toast.LENGTH_LONG);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_video);

        // If you use location in your app, but would like to disable location passing.
//        MoPub.setLocationAwareness(MoPub.LocationAwareness.DISABLED);

        MoPubRewardedAds.setRewardedAdListener(moPubRewardedAdListener);

        findViewById(R.id.bt_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NendMediationSettings settings = new NendMediationSettings.Builder()
                        .setUserId("you user id")
                        .setAge(18)
                        .setBirthday(2000,1,1)
                        .setGender(NendMediationSettings.GENDER_MALE)
                        .addCustomFeature("customIntParam", 123)
                        .addCustomFeature("customDoubleParam", 123.45)
                        .addCustomFeature("customStringParam", "test")
                        .addCustomFeature("customBooleanParam", true)
                        .build();
                MoPubRewardedAds.loadRewardedAd(MOPUB_AD_UNIT_ID, settings);
            }
        });

        findViewById(R.id.bt_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoPubRewardedAds.hasRewardedAd(MOPUB_AD_UNIT_ID)) {
                    MoPubRewardedAds.showRewardedAd(MOPUB_AD_UNIT_ID);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!verifyPermissions()) {
            requestPermissions();
        }
    }

    private void showToast(String message, int duration) {
        Toast.makeText(RewardedVideoActivity.this, message, duration).show();
    }

    private boolean verifyPermissions() {
        int state = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        return state == PackageManager.PERMISSION_GRANTED;
    }

    private void showRequestPermissionDialog() {
        ActivityCompat.requestPermissions(RewardedVideoActivity.this,
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
