package gemastik.pendekar.data.repository

import gemastik.pendekar.data.model.BaseResponse
import gemastik.pendekar.data.model.CCTVModel
import gemastik.pendekar.data.model.HistoryReportModel
import io.reactivex.rxjava3.core.Single
import java.io.File

interface RestRepository {
    fun getListCCTV():Single<BaseResponse<List<CCTVModel>>>
    fun getOneCCTV(cctvId: String):Single<BaseResponse<CCTVModel>>

    fun getTotalCrime():Single<BaseResponse<Int>>
    fun addReport(report: HistoryReportModel, file: File) : Single<BaseResponse<HistoryReportModel>>
    fun getAllReportHistory(): Single<BaseResponse<List<HistoryReportModel>>>
}