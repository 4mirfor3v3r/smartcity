package gemastik.pendekar.data.repository

import gemastik.pendekar.data.model.HistoryReportModel
import io.reactivex.rxjava3.core.Single

interface MainRepository {
    fun addReport(report: HistoryReportModel)
    fun addAllReport(report: List<HistoryReportModel>)
    fun getAllReportHistory(): List<HistoryReportModel>
}