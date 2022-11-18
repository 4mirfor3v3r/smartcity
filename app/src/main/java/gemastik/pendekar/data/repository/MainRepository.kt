package gemastik.pendekar.data.repository

import com.google.android.gms.maps.model.LatLng
import gemastik.pendekar.data.model.HistoryReportModel
import gemastik.pendekar.data.model.RouteResponse
import io.reactivex.rxjava3.core.Single

interface MainRepository {
    fun addReport(report: HistoryReportModel)
    fun addAllReport(report: List<HistoryReportModel>)
    fun getAllReportHistory(): List<HistoryReportModel>
    fun getRoute(origin:LatLng,destination:LatLng,avoidAreas:List<LatLng>): Single<RouteResponse>
}