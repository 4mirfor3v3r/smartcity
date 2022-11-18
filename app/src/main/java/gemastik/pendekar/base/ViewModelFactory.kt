package gemastik.pendekar.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gemastik.pendekar.data.api.NetworkConfig
import gemastik.pendekar.data.local.AppDatabase
import gemastik.pendekar.data.repository.MainRepositoryImpl
import gemastik.pendekar.ui.main.home.safe_area.SafeRouteViewModel
import gemastik.pendekar.ui.main.home.self_report.ReportViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ViewModelFactory : ViewModelProvider.Factory {
    private val disposable = CompositeDisposable()
    private val db = AppDatabase.getAppDatabase()
    private val api = NetworkConfig.apiService
    private val repository = MainRepositoryImpl(db.reportDao(),api)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DevViewModel::class.java) -> DevViewModel(disposable) as T
            modelClass.isAssignableFrom(ReportViewModel::class.java) -> ReportViewModel(disposable, repository) as T
            modelClass.isAssignableFrom(SafeRouteViewModel::class.java) -> SafeRouteViewModel(disposable, repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        @JvmStatic
        val viewModelFactory = ViewModelFactory()
    }
}