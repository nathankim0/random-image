package com.jinyeob.randomimage.money

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.ConsumeResponseListener
import com.android.billingclient.api.ProductDetails
import com.jinyeob.randomimage.MainApplication.Companion.TAG
import com.jinyeob.randomimage.databinding.ActivityMoneyBinding
import com.jinyeob.randomimage.money.util.DlogUtil
import com.jinyeob.randomimage.money.util.GooglepayJavaUtil
import com.jinyeob.randomimage.money.util.GooglepayUtil

class MoneyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoneyBinding

    private lateinit var billingCilent: BillingClient
    private var productDetailsList: List<ProductDetails> = mutableListOf()
    private lateinit var consumeListenser : ConsumeResponseListener
    private lateinit var googlepayJavaUtil: GooglepayJavaUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoneyBinding.inflate(layoutInflater)

        binding.payTextview.setOnClickListener {
            DlogUtil.d(TAG, "click")
            GooglepayUtil.getPay(this)
        }

        GooglepayUtil.initBillingClient(this)

        val googlepayJavaUtilDelegate = object : GooglepayJavaUtil.GooglepayJavaUtilDelegate {
            override fun onSuccess() {
                //code here
            }
        }

        setContentView(binding.root)
    }
}
