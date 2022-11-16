package gemastik.pendekar.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gemastik.pendekar.data.local.entity.ReportEntity
import gemastik.pendekar.utils.ApplicationContext

@Database(entities = [ReportEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reportDao(): ReportDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(
                        ApplicationContext.get(),
                        AppDatabase::class.java,
                        "com.gemastik.pendekar.db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}