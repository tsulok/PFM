package com.pinup.pfm.wearable

import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.common.converter.ByteConverter
import com.pinup.pfm.common.domain.model.SpeechTransaction
import com.pinup.pfm.domain.event.TransactionUpdatedEvent
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.interactor.transaction.ITransactionInteractor
import com.pinup.pfm.interactor.utils.ICurrencyInteractor
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * Service for listening data from wearable clients
 */
class PFMWearableListenerService : WearableListenerService() {

    @Inject lateinit var transactionInteractor: ITransactionInteractor
    @Inject lateinit var currencyInteractor: ICurrencyInteractor
    @Inject lateinit var categoryInteractor: ICategoryInteractor
    @Inject lateinit var eventBus: EventBus

    override fun onMessageReceived(messageEvent: MessageEvent?) {
        Logger.d("Message received")
        messageEvent?.let {
            (ByteConverter.convertFromBytes(messageEvent.data) as? SpeechTransaction)?.let { data ->
                PFMApplication.applicationComponent.inject(this)

                transactionInteractor.createTransaction(data.name,
                        data.amount,
                        currencyInteractor.getSelectedCurrency()?.currencyCode ?: "",
                        categoryInteractor.getDefaultCategory())

                eventBus.post(TransactionUpdatedEvent())
            }
        }
    }
}