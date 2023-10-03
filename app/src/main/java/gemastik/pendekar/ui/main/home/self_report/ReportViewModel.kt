package gemastik.pendekar.ui.main.home.self_report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gemastik.pendekar.base.DevViewModel
import gemastik.pendekar.data.model.HistoryReportModel
import gemastik.pendekar.data.repository.MainRepository
import gemastik.pendekar.data.repository.RestRepository
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.logError
import gemastik.pendekar.utils.singleScheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.File

class ReportViewModel(disposable: CompositeDisposable, private val repo: MainRepository, private val restRepo: RestRepository) :
    DevViewModel(disposable) {
    private val disposable = CompositeDisposable()

    private val _pickedImage =
        MutableLiveData<DevState<File>>(DevState.default())
    val pickedImage: LiveData<DevState<File>>
        get() = _pickedImage

    private val _historyReportResult =
        MutableLiveData<DevState<List<HistoryReportModel>>>(DevState.default())
    val historyReportResult: LiveData<DevState<List<HistoryReportModel>>>
        get() = _historyReportResult

    private val _createReportResult =
        MutableLiveData<DevState<HistoryReportModel>>(DevState.default())
    val createReportResult: LiveData<DevState<HistoryReportModel>>
        get() = _createReportResult

    private val _createAllReportResult =
        MutableLiveData<DevState<List<HistoryReportModel>>>(DevState.default())
    val createAllReportResult: LiveData<DevState<List<HistoryReportModel>>>
        get() = _createAllReportResult


    private val _listReport = MutableLiveData<DevState<List<HistoryReportModel>>>(DevState.default())
    val listReport: LiveData<DevState<List<HistoryReportModel>>>
        get() = _listReport

    fun getAllReport() {
        _historyReportResult.value = DevState.loading()
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { repo.getAllReportHistory() }
            logError(result.toString())
            if (result.isNotEmpty()) {
                _historyReportResult.value = DevState.success(result)
            } else {
                _historyReportResult.value = DevState.empty()
            }
        }
    }

    fun addReport(data: HistoryReportModel, imagePicked: File) {
        _createReportResult.value = DevState.loading()
        restRepo.addReport(data, imagePicked)
            .compose(singleScheduler())
            .subscribe({
                _createReportResult.value = DevState.success(it.data)
            },{
                if (it is HttpException) {
                    val errorBody = it.response()?.errorBody()?.string()
                    if (errorBody != null) {
                        _createReportResult.value = DevState.Failure(null, errorBody)
                        messageError.value = errorBody
                    }
                } else {
                    val errorMessage = it.localizedMessage
                    logError(errorMessage)

                    _createReportResult.value = DevState.fail(null, errorMessage)
                    messageError.value = errorMessage
                }
            }).let(disposable::add)
    }

    fun getListReport() {
        _listReport.value = DevState.loading()
        restRepo.getAllReportHistory()
            .compose(singleScheduler())
            .subscribe({
                _listReport.value = DevState.success(it.data)
            },{
                if (it is HttpException) {
                    val errorBody = it.response()?.errorBody()?.string()
                    if (errorBody != null) {
                        _listReport.value = DevState.Failure(null, errorBody)
                        messageError.value = errorBody
                    }
                } else {
                    val errorMessage = it.localizedMessage
                    _listReport.value = DevState.fail(null, errorMessage)
                    messageError.value = errorMessage
                }
            }).let(disposable::add)
    }

    fun addAllReport(data: List<HistoryReportModel>) {
        _createAllReportResult.value = DevState.loading()
        viewModelScope.launch {
            withContext(Dispatchers.IO) { repo.addAllReport(data) }
            _createAllReportResult.value = DevState.success(data)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}