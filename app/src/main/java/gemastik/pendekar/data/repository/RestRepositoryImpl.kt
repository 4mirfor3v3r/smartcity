package gemastik.pendekar.data.repository

import gemastik.pendekar.data.local.ReportDao
import gemastik.pendekar.data.model.BaseResponse
import gemastik.pendekar.data.model.CCTVModel
import gemastik.pendekar.data.service.RestService
import io.reactivex.rxjava3.core.Single

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
}