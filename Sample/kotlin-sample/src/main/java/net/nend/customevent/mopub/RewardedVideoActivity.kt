package net.nend.customevent.mopub

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mopub.common.MoPubReward
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubRewardedAdListener
import com.mopub.mobileads.MoPubRewardedAds
import net.nend.android.mopub.customevent.NendMediationSettings

class RewardedVideoActivity : AppCompatActivity() {

    private val mopubRewardedListener = object : MoPubRewardedAdListener {
        override fun onRewardedAdLoadSuccess(adUnitId: String) {
            toast("Load Success adUnitId: $adUnitId", Toast.LENGTH_SHORT)
        }

        override fun onRewardedAdLoadFailure(adUnitId: String, errorCode: MoPubErrorCode) {
            toast("Load Failure adUnitId: $adUnitId\n errorCode: $errorCode", Toast.LENGTH_LONG)
        }

        override fun onRewardedAdStarted(adUnitId: String) {
            toast("Reward ad started adUnitId: $adUnitId", Toast.LENGTH_SHORT)
        }

        override fun onRewardedAdShowError(adUnitId: String, errorCode: MoPubErrorCode) {
            toast("Rewarded ad show error adUnitId: $adUnitId\n errorCode: $errorCode", Toast.LENGTH_LONG)
        }

        override fun onRewardedAdClicked(adUnitId: String) {
            toast("Ad Clicked adUnitId: $adUnitId", Toast.LENGTH_SHORT)
        }

        override fun onRewardedAdClosed(adUnitId: String) {
            toast("Ad Closed adUnitId: $adUnitId", Toast.LENGTH_SHORT)
        }

        override fun onRewardedAdCompleted(adUnitIds: Set<String?>, reward: MoPubReward) {
            toast("Rewarded ad completed adUnitId: $adUnitIds \n Reward_label: ${reward.label} \n Reward_amount: ${reward.amount}", Toast.LENGTH_LONG)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded_video)

        MoPubRewardedAds.setRewardedAdListener(mopubRewardedListener)

        findViewById<View>(R.id.bt_load).setOnClickListener {
            val settings = NendMediationSettings.Builder()
                    .setUserId("you user id")
                    .setAge(18)
                    .setBirthday(2000, 1, 1)
                    .setGender(NendMediationSettings.GENDER_MALE)
                    .addCustomFeature("customIntParam", 123)
                    .addCustomFeature("customDoubleParam", 123.45)
                    .addCustomFeature("customStringParam", "test")
                    .addCustomFeature("customBooleanParam", true)
                    .build()
            MoPubRewardedAds.loadRewardedAd(MOPUB_AD_UNIT_ID, settings)
        }

        findViewById<View>(R.id.bt_show).setOnClickListener {
            if (MoPubRewardedAds.hasRewardedAd(MOPUB_AD_UNIT_ID)) {
                MoPubRewardedAds.showRewardedAd(MOPUB_AD_UNIT_ID)
            }
        }
    }

    private fun Context.toast(message: CharSequence, duration: Int) = Toast.makeText(this, message, duration).show()

    companion object {
        const val MOPUB_AD_UNIT_ID = "YOUR_REWARD_UNIT_ID"
    }
}
