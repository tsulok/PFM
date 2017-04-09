package com.pinup.pfm.domain.provider

import android.content.Context
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.pinup.pfm.domain.repository.manager.category.ICategoryRepository
import com.pinup.pfm.domain.repository.manager.transaction.ITransactionRepository
import com.pinup.pfm.model.database.Transaction
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Data provider for charts
 */
interface IChartDataProvider {
    fun providePieChartData(dayHistoryCount: Int): List<PieEntry>
    fun provideBarChartData(dayHistoryCount: Int): List<BarEntry>
}

class ChartDataProvider @Inject constructor(private val transactionDaoManager: ITransactionRepository,
                                            private val categoryDaoManager: ICategoryRepository,
                                            @ApplicationContext val context: Context)
    : IChartDataProvider {

    override fun providePieChartData(dayHistoryCount: Int): List<PieEntry> {
        val entries: MutableList<PieEntry> = ArrayList()

        val initialDate = calculateInitialDate(dayHistoryCount)
        val recentTransactions = transactionDaoManager.loadTransactionsAfter(initialDate.time)
        val groupedTransactions = groupTransactionsByCategory(recentTransactions)

        for ((key, value) in groupedTransactions) {
            val sumForCategory = value
                    .map { it.amount.toFloat() }
                    .reduce { acc, value -> acc + value }

            categoryDaoManager.loadByServerId(key)?.let {
                entries.add(PieEntry(sumForCategory, it.name))
            }
        }

        return entries
    }

    override fun provideBarChartData(dayHistoryCount: Int): List<BarEntry> {
        val entries: MutableList<BarEntry> = ArrayList()
        val initialDate = calculateInitialDate(dayHistoryCount)
        val recentTransactions = transactionDaoManager.loadTransactionsAfter(initialDate.time)
        val groupedTransactions = groupTransactionsByDay(recentTransactions)

        for (i in 0..dayHistoryCount) {
            var sumForDay: Float = 0.0f
            groupedTransactions[initialDate.get(Calendar.DAY_OF_YEAR)]?.let { transactions ->
                sumForDay = transactions
                        .map { it.amount.toFloat() }
                        .reduce { acc, value -> acc + value }
            }

            entries.add(BarEntry(initialDate.get(Calendar.DAY_OF_YEAR).toFloat(), sumForDay))

            initialDate.add(Calendar.DAY_OF_MONTH, 1)
        }

        return entries
    }

    private fun calculateInitialDate(dayHistoryCount: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1 * dayHistoryCount) // Let's go back x days
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        return calendar
    }

    /**
     * Group transactions by the day of the year
     */
    private fun groupTransactionsByDay(transactions: List<Transaction>): Map<Int, List<Transaction>> {
        val calendar = Calendar.getInstance()
        return transactions.groupBy {
            calendar.time = it.date
            calendar.get(Calendar.DAY_OF_YEAR)
        }
    }

    /**
     * Group transactions by categories
     */
    private fun groupTransactionsByCategory(transactions: List<Transaction>): Map<String, List<Transaction>> {
        return transactions.groupBy { it.category.id }
    }
}