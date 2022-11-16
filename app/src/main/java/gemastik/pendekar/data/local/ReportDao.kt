package gemastik.pendekar.data.local

import androidx.room.Dao
import androidx.room.Query
import gemastik.pendekar.base.DevDao
import gemastik.pendekar.data.local.entity.ReportEntity

@Dao
interface ReportDao : DevDao<ReportEntity> {

    @Query("SELECT * FROM report ORDER BY id DESC")
    fun getAllNotification(): List<ReportEntity>
}