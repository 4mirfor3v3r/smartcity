package gemastik.pendekar.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gemastik.pendekar.base.DevViewModel
import gemastik.pendekar.data.repository.RestRepository
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.singleScheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.HttpException

class HomeViewModel(d: CompositeDisposable, private val repo: RestRepository) :
    DevViewModel(d) {

    private val disposable = CompositeDisposable()
    private val _totalCrime = MutableLiveData<DevState<Int>>(DevState.default())
    val totalCrime: LiveData<DevState<Int>>
        get() = _totalCrime

    fun getTotalCrime() {
        _totalCrime.value = DevState.loading()
        repo.getTotalCrime()
            .compose(singleScheduler())
            .subscribe({
                _totalCrime.value = DevState.success(it.data)
            },{
                if (it is HttpException) {
                    val errorBody = it.response()?.errorBody()?.string()
                    if (errorBody != null) {
                        _totalCrime.value = DevState.Failure(null, errorBody)
                        messageError.value = errorBody
                    }
                } else {
                    val errorMessage = it.localizedMessage
                    _totalCrime.value = DevState.fail(null, errorMessage)
                    messageError.value = errorMessage
                }
            }).let(disposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}