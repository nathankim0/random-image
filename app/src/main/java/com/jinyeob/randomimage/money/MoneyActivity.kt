package com.jinyeob.randomimage.money

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jinyeob.randomimage.databinding.ActivityMoneyBinding
import com.jinyeob.randomimage.money.util.GooglePayUtil
import com.jinyeob.randomimage.money.util.ToastUtil

class MoneyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoneyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoneyBinding.inflate(layoutInflater)

        GooglePayUtil.initBillingClient(this)
        GooglePayUtil.googlePayUtilCallback = object : GooglePayUtil.GooglePayUtilCallback {
            override fun onSuccess() {
            }

            override fun onFail() {
            }
        }

        binding.payTextview.setOnClickListener {
            ToastUtil.showCustomShortToastNormal(this, "구매 시작")
            GooglePayUtil.getPay(this)
        }

        setContentView(binding.root)
    }
}
