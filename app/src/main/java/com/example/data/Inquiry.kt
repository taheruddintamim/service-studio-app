package com.example.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "inquiries")
data class InquiryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val discipline: String,
    val clientName: String,
    val clientContact: String,
    val initialContext: String,
    val status: String = "Transmitted Directly",
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface InquiryDao {
    @Query("SELECT * FROM inquiries ORDER BY timestamp DESC")
    fun getAllInquiries(): Flow<List<InquiryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInquiry(inquiry: InquiryEntity): Long

    @Query("DELETE FROM inquiries WHERE id = :id")
    suspend fun deleteInquiry(id: Long)

    @Query("DELETE FROM inquiries")
    suspend fun clearAll()
}

@Database(entities = [InquiryEntity::class], version = 1, exportSchema = false)
abstract class StudioDatabase : RoomDatabase() {
    abstract fun inquiryDao(): InquiryDao

    companion object {
        @Volatile
        private var INSTANCE: StudioDatabase? = null

        fun getDatabase(context: Context): StudioDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudioDatabase::class.java,
                    "taher_studio_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class StudioRepository(private val dao: InquiryDao) {
    val inquiries: Flow<List<InquiryEntity>> = dao.getAllInquiries()

    suspend fun submitInquiry(
        discipline: String,
        clientName: String,
        clientContact: String,
        initialContext: String
    ): Long {
        val entity = InquiryEntity(
            discipline = discipline,
            clientName = clientName,
            clientContact = clientContact,
            initialContext = initialContext
        )
        return dao.insertInquiry(entity)
    }

    suspend fun removeInquiry(id: Long) = dao.deleteInquiry(id)
}
