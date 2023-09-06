package gemastik.pendekar.ui.main.home.cctv_camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gemastik.pendekar.base.DevViewModel
import gemastik.pendekar.data.model.CCTVModel
import gemastik.pendekar.data.repository.RestRepository
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.singleScheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException

class CCTVCameraViewModel(d: CompositeDisposable, private val repo: RestRepository) :
    DevViewModel(d) {

    private val disposable = CompositeDisposable()
    private val _cctvDetail = MutableLiveData<DevState<CCTVModel>>(DevState.default())
    val cctvDetail: LiveData<DevState<CCTVModel>>
        get() = _cctvDetail

    fun getOneCCTV(cctvId:String) {
        _cctvDetail.value = DevState.loading()
        repo.getOneCCTV(cctvId)
            .compose(singleScheduler())
            .subscribe({
                _cctvDetail.value = DevState.success(it.data)
            },{
                if (it is HttpException) {
                    val errorBody = it.response()?.errorBody()?.string()
                    if (errorBody != null) {
                        _cctvDetail.value = DevState.Failure(null, errorBody)
                        messageError.value = errorBody
                    }
                } else {
                    val errorMessage = it.localizedMessage
                    _cctvDetail.value = DevState.fail(null, errorMessage)
                    messageError.value = errorMessage
                }
            }).let(disposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}