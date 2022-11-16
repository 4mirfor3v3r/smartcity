package gemastik.pendekar.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import gemastik.pendekar.base.DbModel
import gemastik.pendekar.data.model.HistoryReportModel
import gemastik.pendekar.utils.ReportStatus

@Entity(tableName = "report")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "title")
    var reportTitle: String,

    @ColumnInfo(name = "address")
    var reportAddress: String,

    @ColumnInfo(name = "date")
    var reportDate: String,

    @ColumnInfo(name = "status")
    var reportStatus: Int
):DbModel() {
    fun toHistoryReport(): HistoryReportModel {
        return HistoryReportModel(
            reportId = id.toString(),
            reportTitle = reportTitle,
            reportAddress = reportAddress,
            reportDate = reportDate,
            reportStatus = when (reportStatus) {
                0 -> ReportStatus.PROCESS
                1 -> ReportStatus.SUCCESS
                -1 -> ReportStatus.FAILURE
                else -> ReportStatus.PROCESS
            }
        )
    }
}
