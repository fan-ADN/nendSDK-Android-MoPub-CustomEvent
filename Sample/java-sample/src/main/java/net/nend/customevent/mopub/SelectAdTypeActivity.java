package net.nend.customevent.mopub;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;

import net.nend.android.mopub.customevent.NendAdapterConfiguration;

import java.util.ArrayList;
import java.util.List;

public class SelectAdTypeActivity extends ListActivity {

    private static final String MOPUB_AD_UNIT_ID = "YOUR_UNIT_ID";

    private static final List<Class> SAMPLE_ACTIVITIES = new ArrayList<Class>() {
        {
            add(BannerActivity.class);
            add(InterstitialActivity.class);
            add(RewardedVideoActivity.class);
            add(InterstitialVideoActivity.class);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ad_type);

        SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder(MOPUB_AD_UNIT_ID)
                .withAdditionalNetwork(NendAdapterConfiguration.class.getName())
                .build();
        MoPub.initializeSdk(this, sdkConfiguration, initMoPubSdkListener());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(this, SAMPLE_ACTIVITIES.get(position)));
    }

    private SdkInitializationListener initMoPubSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
                Toast.makeText(SelectAdTypeActivity.this, "MoPub SDK initialized.", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
