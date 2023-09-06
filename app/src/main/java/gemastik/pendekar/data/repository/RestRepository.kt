package gemastik.pendekar.data.repository

import gemastik.pendekar.data.model.BaseResponse
import gemastik.pendekar.data.model.CCTVModel
import io.reactivex.rxjava3.core.Single

interface RestRepository {
    fun getListCCTV():Single<BaseResponse<List<CCTVModel>>>
    fun getOneCCTV(cctvId: String):Single<BaseResponse<CCTVModel>>

    fun getTotalCrime():Single<BaseResponse<Int>>
}