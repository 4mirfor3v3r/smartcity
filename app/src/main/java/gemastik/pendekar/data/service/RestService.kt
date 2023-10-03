package gemastik.pendekar.data.service

import gemastik.pendekar.data.model.BaseResponse
import gemastik.pendekar.data.model.CCTVModel
import gemastik.pendekar.data.model.HistoryReportModel
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

@JvmSuppressWildcards
interface RestService {
    @GET("cctv/{cctv_id}")
    fun getOneCCTV(@Path("cctv_id", encoded = true) cctvId: String):Single<BaseResponse<CCTVModel>>

    @GET("list-cctv")
    fun getListCCTV():Single<BaseResponse<List<CCTVModel>>>

    @GET("total-crime")
    fun getTotalCrime():Single<BaseResponse<Int>>

    @POST("insert-report")
    fun insertReport(@Body body: MultipartBody):Single<BaseResponse<HistoryReportModel>>

    @GET("list-report")
    fun getListReport():Single<BaseResponse<List<HistoryReportModel>>>
}