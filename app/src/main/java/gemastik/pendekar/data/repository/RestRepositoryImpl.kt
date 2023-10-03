package gemastik.pendekar.data.repository

import gemastik.pendekar.data.local.ReportDao
import gemastik.pendekar.data.model.BaseResponse
import gemastik.pendekar.data.model.CCTVModel
import gemastik.pendekar.data.model.HistoryReportModel
import gemastik.pendekar.data.service.RestService
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class RestRepositoryImpl(private val mainDao: ReportDao, private val api: RestService) :
    RestRepository {
    override fun getListCCTV(): Single<BaseResponse<List<CCTVModel>>> {
        return api.getListCCTV()
    }

    override fun getOneCCTV(cctvId: String): Single<BaseResponse<CCTVModel>> {
        return api.getOneCCTV(cctvId)
    }

    override fun getTotalCrime(): Single<BaseResponse<Int>> {
        return api.getTotalCrime()
    }

    override fun addReport(report: HistoryReportModel, file: File): Single<BaseResponse<HistoryReportModel>> {
        val name = (report.senderName ?:"").toRequestBody(report.senderName?.toMediaTypeOrNull())
        val title = report.reportTitle.toRequestBody(report.reportTitle.toMediaTypeOrNull())
        val address = report.reportAddress.toRequestBody(report.reportAddress.toMediaTypeOrNull())
        val date = report.reportDate.toRequestBody(report.reportDate.toMediaTypeOrNull())
        val filePart: MultipartBody.Part = createFormData(
            "image",
            file.name,
            file.asRequestBody("image/*".toMediaTypeOrNull())
        )
        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", report.senderName?:"")
            .addFormDataPart("title", report.reportTitle)
            .addFormDataPart("address", report.reportAddress)
            .addFormDataPart("date", report.reportDate)
            .addPart(filePart)
            .build()
        return api.insertReport(body)
//        return api.insertReport(name, title, address, date, filePart)
    }

    override fun getAllReportHistory(): Single<BaseResponse<List<HistoryReportModel>>> {
        return api.getListReport()
    }
}