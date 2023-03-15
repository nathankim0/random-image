package com.jinyeob.randomimage.money.util

import android.app.Activity
import android.os.Handler
import android.os.Looper
import com.android.billingclient.api.*

object GooglePayUtil : PurchasesUpdatedListener {

    private const val TAG = "==GooglePayUtil=="

    private lateinit var billingClient: BillingClient
    private var productDetailsList: List<ProductDetails> = mutableListOf()
    private lateinit var consumeListener: ConsumeResponseListener

    interface GooglePayUtilCallback {
        fun onSuccess()
        fun onFail()
    }

    var googlePayUtilCallback: GooglePayUtilCallback? = null

    fun initBillingClient(activity: Activity) {

        billingClient = BillingClient.newBuilder(activity)
            .setListener(this)
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                LogUtil.d(TAG, "연결 실패")
                // RETRY CONNECTION
                if (billingClient.connectionState != 2) {
                    Handler(Looper.myLooper()!!).postDelayed({ initBillingClient(activity) }, 2000)
                }
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    LogUtil.d(TAG, "연결 성공")
                    LogUtil.d(TAG, "billingCilent.connectionState : ${billingClient.connectionState}")

                    // TODO: BLOCK UI!!!
                    getPurchaseList()
                }
            }
        })

        consumeListener = ConsumeResponseListener { billingResult, purchaseToken ->
            LogUtil.d(TAG, "billingResult.responseCode : ${billingResult.responseCode}")
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                LogUtil.d(TAG, "소모 성공")
            } else {
                LogUtil.d(TAG, "소모 실패")
            }
        }
    }

    /**
     * 구매 가능 목록
     */
    fun getPurchaseList() {
        LogUtil.d(TAG, "querySkuDetails")

        val tempParam = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("payment_id")
                        .setProductType(BillingClient.ProductType.SUBS) // 구독
                        .build()
                )
            ).build()

        billingClient.queryProductDetailsAsync(tempParam) { _billingResult, _productDetailsList ->
            if (_billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                LogUtil.d(TAG, "queryProductDetailsAsync 성공")
                productDetailsList = _productDetailsList
            } else {
                LogUtil.d(TAG, "queryProductDetailsAsync 실패")
            }
        }
    }

    fun getPay(activity: Activity) {
        val productDetailsParams: MutableList<BillingFlowParams.ProductDetailsParams> = mutableListOf()

        for (i in productDetailsList.indices) {
            if (productDetailsList[i].productId == "payment_id") {
                productDetailsParams.add(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetailsList[i])
                        .build()
                )
            }
        }

        if(productDetailsParams.isEmpty()) {
            LogUtil.d(TAG, "결제 불가: productDetailsParams is empty")
            return
        }

        // Google Play 결제
        when (
            billingClient.launchBillingFlow(
                activity,
                BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productDetailsParams)
                    .build()
            ).responseCode
        ) {
            BillingClient.BillingResponseCode.OK -> {
                LogUtil.d(TAG, "결제 성공")
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                LogUtil.d(TAG, "결제 취소 USER_CANCELED")
            }
            BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE -> {
                LogUtil.d(TAG, "결제 서비스 불가")
            }
            BillingClient.BillingResponseCode.BILLING_UNAVAILABLE -> {
                LogUtil.d(TAG, "결제 불가 BILLING_UNAVAILABLE")
            }
            BillingClient.BillingResponseCode.ITEM_UNAVAILABLE -> {
                LogUtil.d(TAG, "결제 불가 ITEM_UNAVAILABLE")
            }
            BillingClient.BillingResponseCode.DEVELOPER_ERROR -> {
                LogUtil.d(TAG, "결제 불가 DEVELOPER_ERROR")
            }
            BillingClient.BillingResponseCode.ERROR -> {
                LogUtil.d(TAG, "결제 불가 ERROR")
            }
            BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> {
                LogUtil.d(TAG, "결제 불가 ITEM_ALREADY_OWNED")
            }
            BillingClient.BillingResponseCode.ITEM_NOT_OWNED -> {
                LogUtil.d(TAG, "결제 불가 ITEM_NOT_OWNED")
            }
        }
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        LogUtil.d(TAG, "onPurchasesUpdated ${billingResult.responseCode}")
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                LogUtil.d(TAG, "구매 성공")

                googlePayUtilCallback?.onSuccess()

//                billingClient.consumeAsync(ConsumeParams.newBuilder()
//                    .setPurchaseToken(purchase.purchaseToken)
//                    .build(), consumeListener)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            LogUtil.d(TAG, "구매 취소")
            googlePayUtilCallback?.onFail()
        } else {
            LogUtil.d(TAG, "구매 실패")
            googlePayUtilCallback?.onFail()
        }
    }
}
