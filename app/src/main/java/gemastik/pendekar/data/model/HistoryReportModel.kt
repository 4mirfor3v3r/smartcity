package gemastik.pendekar.data.model

import gemastik.pendekar.data.local.entity.ReportEntity
import gemastik.pendekar.utils.ReportStatus

data class HistoryReportModel(
    var reportId: String?,
    var reportTitle: String,
    var reportAddress: String,
    var reportDate: String,
    var reportStatus: ReportStatus?
) {
    fun toReportEntity(): ReportEntity {
        return ReportEntity(
            id = reportId?.toInt(),
            reportTitle = reportTitle,
            reportAddress = reportAddress,
            reportDate = reportDate,
            reportStatus = when (reportStatus) {
                ReportStatus.PROCESS ->0
                ReportStatus.SUCCESS -> 1
                ReportStatus.FAILURE ->-1
                else -> 0
            }
        )
    }
}