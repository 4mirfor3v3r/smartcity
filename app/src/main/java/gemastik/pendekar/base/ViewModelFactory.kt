package gemastik.pendekar.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gemastik.pendekar.data.api.NetworkConfig
import gemastik.pendekar.data.local.AppDatabase
import gemastik.pendekar.data.repository.MainRepositoryImpl
import gemastik.pendekar.data.repository.RestRepositoryImpl
import gemastik.pendekar.ui.main.home.HomeViewModel
import gemastik.pendekar.ui.main.home.cctv.CCTVViewModel
import gemastik.pendekar.ui.main.home.cctv_camera.CCTVCameraViewModel
import gemastik.pendekar.ui.main.home.list_cctv.ListCCTVViewModel
import gemastik.pendekar.ui.main.home.safe_area.SafeRouteViewModel
import gemastik.pendekar.ui.main.home.self_report.ReportViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ViewModelFactory : ViewModelProvider.Factory {
    private val disposable = CompositeDisposable()
    private val db = AppDatabase.getAppDatabase()
    private val api = NetworkConfig.apiService
    private val restApi = NetworkConfig.restApiService
    private val repository = MainRepositoryImpl(db.reportDao(),api)
    private val restRepository = RestRepositoryImpl(db.reportDao(),restApi)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DevViewModel::class.java) -> DevViewModel(disposable) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(disposable, restRepository) as T
            modelClass.isAssignableFrom(CCTVViewModel::class.java) -> CCTVViewModel(disposable, restRepository) as T
            modelClass.isAssignableFrom(ReportViewModel::class.java) -> ReportViewModel(disposable, repository) as T
            modelClass.isAssignableFrom(SafeRouteViewModel::class.java) -> SafeRouteViewModel(disposable, repository, restRepository) as T
            modelClass.isAssignableFrom(ListCCTVViewModel::class.java) -> ListCCTVViewModel(disposable, restRepository) as T
            modelClass.isAssignableFrom(CCTVCameraViewModel::class.java) -> CCTVCameraViewModel(disposable, restRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        @JvmStatic
        val viewModelFactory = ViewModelFactory()
    }
}