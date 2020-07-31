package net.nend.customevent.mopub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubView

class BannerActivity : AppCompatActivity() {

    private lateinit var mopubView: MoPubView

    private val adListener = object : MoPubView.BannerAdListener {
        override fun onBannerLoaded(banner: MoPubView) {
            Log.d(TAG, "onBannerLoaded:$banner")
        }

        override fun onBannerFailed(banner: MoPubView, errorCode: MoPubErrorCode) {
            Log.d(TAG, "onBannerFailed:$banner/ errorCode:$errorCode")
        }

        override fun onBannerClicked(banner: MoPubView) {
            Log.d(TAG, "onBannerClicked:$banner")
        }

        override fun onBannerExpanded(banner: MoPubView) {
            Log.d(TAG, "onBannerExpanded:$banner")
        }

        override fun onBannerCollapsed(banner: MoPubView) {
            Log.d(TAG, "onBannerCollapsed:$banner")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)

        mopubView = findViewById<MoPubView>(R.id.adview).apply {
            setAdUnitId(MOPUB_AD_UNIT_ID)
            bannerAdListener = adListener
            loadAd()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mopubView.bannerAdListener = null
        mopubView.destroy()
        val parent = mopubView.parent as ViewGroup
        parent.removeAllViews()
    }

    companion object {
        const val MOPUB_AD_UNIT_ID = "YOUR_BANNER_UNIT_ID"
        const val TAG = "BannerActivity"
    }
}
