package net.nend.customevent.mopub;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedAdListener;
import com.mopub.mobileads.MoPubRewardedAds;

import net.nend.android.mopub.customevent.NendMediationSettings;

import java.util.Set;

public class RewardedVideoActivity extends AppCompatActivity {

    private static final String MOPUB_AD_UNIT_ID = "YOUR_REWARD_UNIT_ID";

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

    private void showToast(String message, int duration) {
        Toast.makeText(RewardedVideoActivity.this, message, duration).show();
    }
}
