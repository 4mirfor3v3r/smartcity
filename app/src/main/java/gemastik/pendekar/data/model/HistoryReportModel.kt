package gemastik.pendekar.data.model

import gemastik.pendekar.utils.ReportStatus

data class HistoryReportModel(
    var reportId: String,
    var reportTitle: String,
    var reportAddress: String,
    var reportDate: String,
    var reportStatus: ReportStatus
)