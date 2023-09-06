package gemastik.pendekar.ui.main.home.safe_area

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import gemastik.pendekar.base.DevViewModel
import gemastik.pendekar.data.model.CCTVModel
import gemastik.pendekar.data.model.RouteResponse
import gemastik.pendekar.data.repository.MainRepository
import gemastik.pendekar.data.repository.RestRepository
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.singleScheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException

class SafeRouteViewModel(d: CompositeDisposable, private val repo: MainRepository, private val rest: RestRepository) :
    DevViewModel(d) {
    private val disposable = CompositeDisposable()
    private val _safeRoute = MutableLiveData<DevState<RouteResponse>>(DevState.default())
    val safeRoute: LiveData<DevState<RouteResponse>>
        get() = _safeRoute

    private val _listCCTV = MutableLiveData<DevState<List<CCTVModel>>>(DevState.default())
    val listCCTV: LiveData<DevState<List<CCTVModel>>>
        get() = _listCCTV

    fun getListCCTV() {
        _listCCTV.value = DevState.loading()
        rest.getListCCTV()
            .compose(singleScheduler())
            .subscribe({
                _listCCTV.value = DevState.success(it.data)
            },{
                if (it is HttpException) {
                    val errorBody = it.response()?.errorBody()?.string()
                    if (errorBody != null) {
                        _listCCTV.value = DevState.Failure(null, errorBody)
                        messageError.value = errorBody
                    }
                } else {
                    val errorMessage = it.localizedMessage
                    _listCCTV.value = DevState.fail(null, errorMessage)
                    messageError.value = errorMessage
                }
            }).let(disposable::add)
    }

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

    fun clearDisposable(){
        disposable.dispose()
    }
}