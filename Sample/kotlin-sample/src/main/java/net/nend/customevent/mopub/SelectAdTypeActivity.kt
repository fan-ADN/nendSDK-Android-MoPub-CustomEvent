package net.nend.customevent.mopub

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.mopub.common.MoPub
import com.mopub.common.SdkConfiguration
import com.mopub.common.SdkInitializationListener
import net.nend.android.mopub.customevent.NendAdapterConfiguration

class SelectAdTypeActivity : ListActivity() {

    private enum class AdType(val id: Int) {
        BANNER(0),
        INTERSTITIAL(1),
        REWARDED_VIDEO(2),
        INTERSTITIAL_VIDEO(3);

        companion object {
            fun getAdType(id: Int) = AdType.values().first { it.id == id }
        }

        fun startActivity(activity: Activity) {
            when (this) {
                BANNER -> activity.startActivity(Intent(activity,
                    BannerActivity::class.java))
                INTERSTITIAL -> activity.startActivity(Intent(activity,
                        InterstitialActivity::class.java))
                REWARDED_VIDEO -> activity.startActivity(Intent(activity,
                        RewardedVideoActivity::class.java))
                INTERSTITIAL_VIDEO -> activity.startActivity(Intent(activity,
                        InterstitialVideoActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_ad_type)

        val sdkConfiguration = SdkConfiguration.Builder(MOPUB_AD_UNIT_ID)
                .withAdditionalNetwork(NendAdapterConfiguration::class.java.name)
                .build()
        MoPub.initializeSdk(this, sdkConfiguration, initMoPubSdkListener())
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        AdType.getAdType(position).startActivity(this)
    }

    private fun initMoPubSdkListener() = SdkInitializationListener {
        Toast.makeText(this, "MoPub SDK initialized.", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val MOPUB_AD_UNIT_ID = "YOUR_UNIT_ID"
    }
}
