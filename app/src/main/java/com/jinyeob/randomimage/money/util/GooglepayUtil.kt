package com.jinyeob.randomimage.money.util

import android.app.Activity
import com.android.billingclient.api.*

object GooglepayUtil : PurchasesUpdatedListener {

    private const val TAG = "GooglepayUtil"

    private lateinit var billingClient: BillingClient
    private var productDetailsList: List<ProductDetails> = mutableListOf()
    private lateinit var consumeListener : ConsumeResponseListener

    fun initBillingClient(activity: Activity) {

        billingClient = BillingClient.newBuilder(activity)
            .setListener(this)
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                // TODO: RETRY CONNECTION
                DlogUtil.d(TAG, "연결 실패")
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    DlogUtil.d(TAG, "연결 성공")
                    DlogUtil.d(TAG, "billingCilent.connectionState : ${billingClient.connectionState}")

                    // TODO: BLOCK UI!!!
                    querySkuDetails()
                }
            }
        })

        consumeListener = ConsumeResponseListener { billingResult, purchaseToken ->
            DlogUtil.d(TAG, "billingResult.responseCode : ${billingResult.responseCode}")
            if(billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                DlogUtil.d(TAG, "소모 성공")
            } else {
                DlogUtil.d(TAG, "소모 실패")
            }
        }
    }

    /**
     * 구매 가능 목록
     *
     */
    fun querySkuDetails() {
        DlogUtil.d(TAG, "querySkuDetails")

        val tempParam = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("payment_id")
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()
                )
            ).build()

        billingClient.queryProductDetailsAsync(tempParam) { _billingResult, _productDetailsList ->
            if(_billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                DlogUtil.d(TAG, "querySkuDetails 성공")
                productDetailsList = _productDetailsList
            } else {
                DlogUtil.d(TAG, "querySkuDetails 실패")
            }
        }
    }

    fun getPay(activity: Activity) {
        val list : MutableList<BillingFlowParams.ProductDetailsParams> = mutableListOf()

        for(i in productDetailsList.indices) {
            if(productDetailsList[i].productId == "payment_id") {
                list.add(BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetailsList[i])
                    .build())
            }
        }

        // Google Play 결제
        when(billingClient.launchBillingFlow(activity, BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(list)
            .build()).responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                DlogUtil.d(TAG, "결제 성공")
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                DlogUtil.d(TAG, "결제 취소 USER_CANCELED")
            }
            BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE -> {
                DlogUtil.d(TAG, "결제 서비스 불가")
            }
            BillingClient.BillingResponseCode.BILLING_UNAVAILABLE -> {
                DlogUtil.d(TAG, "결제 불가 BILLING_UNAVAILABLE")
            }
            BillingClient.BillingResponseCode.ITEM_UNAVAILABLE -> {
                DlogUtil.d(TAG, "결제 불가 ITEM_UNAVAILABLE")
            }
            BillingClient.BillingResponseCode.DEVELOPER_ERROR -> {
                DlogUtil.d(TAG, "결제 불가 DEVELOPER_ERROR")
            }
            BillingClient.BillingResponseCode.ERROR -> {
                DlogUtil.d(TAG, "결제 불가 ERROR")
            }
            BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> {
                DlogUtil.d(TAG, "결제 불가 ITEM_ALREADY_OWNED")
            }
            BillingClient.BillingResponseCode.ITEM_NOT_OWNED -> {
                DlogUtil.d(TAG, "결제 불가 ITEM_NOT_OWNED")
            }
        }
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        DlogUtil.d(TAG, "onPurchasesUpdated ${billingResult.responseCode}")
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                DlogUtil.d(TAG,"구매 성공")

//                billingClient.consumeAsync(ConsumeParams.newBuilder()
//                    .setPurchaseToken(purchase.purchaseToken)
//                    .build(), consumeListener)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            DlogUtil.d(TAG,"구매 취소")
        } else {
            DlogUtil.d(TAG,"구매 실패")
        }
    }
}
