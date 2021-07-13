package net.nend.customevent.mopub;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

import net.nend.android.mopub.customevent.NendMediationSettings;

public class InterstitialVideoActivity extends AppCompatActivity {

    private static final String MOPUB_AD_UNIT_ID = "YOUR_VIDEO_INTERSTITIAL_UNIT_ID";
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

    private void showToast(String message, int duration) {
        Toast.makeText(InterstitialVideoActivity.this, message, duration).show();
    }
}
