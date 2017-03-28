package com.pinup.pfm.domain.provider

import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.PieEntry
import com.pinup.pfm.domain.repository.manager.transaction.ITransactionRepository
import com.pinup.pfm.model.database.Transaction
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Data provider for charts
 */
interface IChartDataProvider {
    fun providePieChartData()
    fun provideLineChartData(): List<Entry>
    fun provideBarChartData(): List<BarEntry>
}

class ChartDataProvider  @Inject constructor(val transactionDaoManager: ITransactionRepository)
    : IChartDataProvider {

    object Constants {
        const val MAX_HISTORY_COUNT_DAYS = 7
    }

    override fun providePieChartData() {

    }

    override fun provideBarChartData(): List<BarEntry> {
        val rawEntries = loadRawData()
        return rawEntries.map { BarEntry(it.x, it.y) }
    }

    override fun provideLineChartData(): List<Entry> {
        return loadRawData()
    }

    private fun loadRawData(): List<Entry> {

        val entries: MutableList<Entry> = ArrayList()
        val initialDate = calculateInitialDate()
        val recentTransactions = transactionDaoManager.loadTransactionsAfter(initialDate.time)
        val groupedTransactions = groupTransactionsByDay(recentTransactions)

        for (i in 0..Constants.MAX_HISTORY_COUNT_DAYS ) {
            var sumForDay: Float = 0.0f
            groupedTransactions.get(initialDate.get(Calendar.DAY_OF_YEAR))?.let { transactions ->
                sumForDay = transactions
                        .map { it.amount.toFloat() }
                        .reduce { acc, value -> acc + value }
            }

            entries.add(Entry(initialDate.get(Calendar.DAY_OF_YEAR).toFloat(), sumForDay))

            initialDate.add(Calendar.DAY_OF_MONTH, 1)
        }

        return entries
    }

    private fun calculateInitialDate(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1 * Constants.MAX_HISTORY_COUNT_DAYS) // Let's go back x days
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        return calendar
    }

    private fun groupTransactionsByDay(transactions: List<Transaction>): Map<Int, List<Transaction>> {
        val calendar = Calendar.getInstance()
        return transactions.groupBy {
            calendar.time = it.date
            calendar.get(Calendar.DAY_OF_YEAR)
        }
    }
}