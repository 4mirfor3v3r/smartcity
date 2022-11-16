package gemastik.pendekar.ui.main.home.self_report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gemastik.pendekar.base.DevViewModel
import gemastik.pendekar.data.model.HistoryReportModel
import gemastik.pendekar.data.repository.MainRepository
import gemastik.pendekar.utils.DevState
import gemastik.pendekar.utils.logDebug
import gemastik.pendekar.utils.logError
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportViewModel(disposable: CompositeDisposable, private val repo: MainRepository) :
    DevViewModel(disposable) {

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

    fun addReport(data: HistoryReportModel) {
        _createReportResult.value = DevState.loading()
        viewModelScope.launch {
            withContext(Dispatchers.IO) { repo.addReport(data) }
            _createReportResult.value = DevState.success(data)
        }
    }

    fun addAllReport(data: List<HistoryReportModel>) {
        _createAllReportResult.value = DevState.loading()
        viewModelScope.launch {
            withContext(Dispatchers.IO) { repo.addAllReport(data) }
            _createAllReportResult.value = DevState.success(data)
        }
    }
}