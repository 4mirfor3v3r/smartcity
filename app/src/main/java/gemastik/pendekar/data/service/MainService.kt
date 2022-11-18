package gemastik.pendekar.data.service

import gemastik.pendekar.data.model.RouteResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface MainService {
    @GET("routes")
    fun getRoute(
        @Query("origin", encoded = true) origin: String,
        @Query("destination", encoded = true) destination: String,
        @Query("transportMode", encoded = true) transportMode: String = "car",
        @Query("avoid[areas]", encoded = true) avoidArea: String,
        @Query("return", encoded = true) polyline: String = "polyline"
    ):Single<RouteResponse>
}