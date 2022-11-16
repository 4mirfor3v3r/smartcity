package gemastik.pendekar.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gemastik.pendekar.data.local.AppDatabase
import gemastik.pendekar.data.repository.MainRepositoryImpl
import gemastik.pendekar.ui.main.home.self_report.ReportViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ViewModelFactory : ViewModelProvider.Factory {
    private val disposable = CompositeDisposable()
    private val db = AppDatabase.getAppDatabase()
    private val repository = MainRepositoryImpl(db.reportDao())

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DevViewModel::class.java) -> DevViewModel(disposable) as T
            modelClass.isAssignableFrom(ReportViewModel::class.java) -> ReportViewModel(disposable, repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        @JvmStatic
        val viewModelFactory = ViewModelFactory()
    }
}