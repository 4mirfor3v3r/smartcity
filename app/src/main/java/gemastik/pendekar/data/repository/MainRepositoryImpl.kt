package gemastik.pendekar.data.repository

import gemastik.pendekar.data.local.ReportDao
import gemastik.pendekar.data.model.HistoryReportModel

class MainRepositoryImpl(private val mainDao:ReportDao):MainRepository {
    override fun addReport(report: HistoryReportModel) {
        mainDao.insert(report.toReportEntity())
    }

    override fun addAllReport(report: List<HistoryReportModel>) {
        report.forEach {
            mainDao.insert(it.toReportEntity())
        }
    }

    override fun getAllReportHistory(): List<HistoryReportModel> {
        return mainDao.getAllNotification().map { entity -> entity.toHistoryReport() }
    }
}