package gemastik.pendekar.data.repository

import com.google.android.gms.maps.model.LatLng
import gemastik.pendekar.data.local.ReportDao
import gemastik.pendekar.data.model.HistoryReportModel
import gemastik.pendekar.data.model.RouteResponse
import gemastik.pendekar.data.service.MainService
import io.reactivex.rxjava3.core.Single

class MainRepositoryImpl(private val mainDao: ReportDao, private val mainApi: MainService) :
    MainRepository {
    override fun addReport(report: HistoryReportModel) {
        mainDao.insert(report.toReportEntity())
    }

    override fun addAllReport(report: List<HistoryReportModel>) {
        report.forEach {
            mainDao.insert(it.toReportEntity())
        }
    }

    override fun getAllReportHistory(): List<HistoryReportModel> {
        return mainDao.getAllNotification().map { entity -> entity.toHistoryReport() }
    }

    override fun getRoute(
        origin: LatLng,
        destination: LatLng,
        avoidAreas: List<LatLng>
    ): Single<RouteResponse> {
        var avoidArea:String? = null
        if(avoidAreas.isNotEmpty()) {
            avoidAreas.forEach {
                avoidArea =
                    "${avoidArea}bbox:${it.longitude - 0.0004},${it.latitude + 0.0004},${it.longitude + 0.0004},${it.latitude - 0.0004}|"
            }
        }
        return mainApi.getRoute(
            origin = "${origin.latitude},${origin.longitude}",
            destination = "${destination.latitude},${destination.longitude}",
            avoidArea = avoidArea
            )
    }
}