package gemastik.pendekar.data.service

import gemastik.pendekar.data.model.BaseResponse
import gemastik.pendekar.data.model.CCTVModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

@JvmSuppressWildcards
interface RestService {
    @GET("cctv/{cctv_id}")
    fun getOneCCTV(@Path("cctv_id", encoded = true) cctvId: String):Single<BaseResponse<CCTVModel>>

    @GET("list-cctv")
    fun getListCCTV():Single<BaseResponse<List<CCTVModel>>>

    @GET("total-crime")
    fun getTotalCrime():Single<BaseResponse<Int>>
}