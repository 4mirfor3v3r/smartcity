package gemastik.pendekar.ui.main.home.safe_area

import android.webkit.WebStorage.Origin
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import gemastik.pendekar.base.DevViewModel
import gemastik.pendekar.data.model.RouteResponse
import gemastik.pendekar.data.repository.MainRepository
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.singleScheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException

class SafeRouteViewModel(private val disposable: CompositeDisposable, private val repo: MainRepository) :
    DevViewModel(disposable) {

    private val _safeRoute = MutableLiveData<DevState<RouteResponse>>(DevState.default())
    val safeRoute: LiveData<DevState<RouteResponse>>
        get() = _safeRoute

    fun getRoute(origin: LatLng, destination: LatLng, avoidArea: List<LatLng>) {
        _safeRoute.value = DevState.loading()
        repo.getRoute(origin,destination,avoidArea)
            .compose(singleScheduler())
            .subscribe({
                _safeRoute.value = DevState.success(it)
            },{
                if (it is HttpException) {
                    val errorBody = it.response()?.errorBody()?.string()
                    if (errorBody != null) {
                        _safeRoute.value = DevState.Failure(null, errorBody)
                        messageError.value = errorBody
                    }
                } else {
                    val errorMessage = it.localizedMessage
                    _safeRoute.value = DevState.fail(null, errorMessage)
                    messageError.value = errorMessage
                }
            }).let(disposable::add)
    }
}