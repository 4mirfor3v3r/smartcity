package gemastik.pendekar.data.model

import com.google.gson.annotations.SerializedName
import gemastik.pendekar.data.local.entity.ReportEntity
import gemastik.pendekar.utils.ReportStatus

data class HistoryReportModel(
    @SerializedName("_id")
    var reportId: String?,
    @SerializedName("name")
    var senderName: String? = null,
    @SerializedName("title")
    var reportTitle: String,
    @SerializedName("address")
    var reportAddress: String,
    @SerializedName("date")
    var reportDate: String,

    var reportStatus: ReportStatus?,
    var image: String? = null
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