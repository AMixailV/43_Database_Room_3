package ru.mixail_akulov.a43_database_room_3

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.mixail_akulov.a43_database_room_3.model.accounts.AccountsRepository
import ru.mixail_akulov.a43_database_room_3.model.accounts.room.RoomAccountsRepository
import ru.mixail_akulov.a43_database_room_3.model.boxes.BoxesRepository
import ru.mixail_akulov.a43_database_room_3.model.boxes.room.RoomBoxesRepository
import ru.mixail_akulov.a43_database_room_3.model.room.AppDatabase
import ru.mixail_akulov.a43_database_room_3.model.room.MIGRATION_2_3
import ru.mixail_akulov.a43_database_room_3.model.settings.AppSettings
import ru.mixail_akulov.a43_database_room_3.model.settings.SharedPreferencesAppSettings
import ru.mixail_akulov.a43_database_room_3.utils.security.DefaultSecurityUtilsImpl
import ru.mixail_akulov.a43_database_room_3.utils.security.SecurityUtils

object Repositories {

    private lateinit var applicationContext: Context

    // -- stuffs

    val securityUtils: SecurityUtils by lazy { DefaultSecurityUtilsImpl() }

    private val database: AppDatabase by lazy<AppDatabase> {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .createFromAsset("initial_database.db")
            .addMigrations(MIGRATION_2_3)
            .build()
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(applicationContext)
    }

    // --- repositories

    val accountsRepository: AccountsRepository by lazy {
        RoomAccountsRepository(database.getAccountsDao(), appSettings, securityUtils, ioDispatcher)
    }

    val boxesRepository: BoxesRepository by lazy {
        RoomBoxesRepository(accountsRepository, database.getBoxesDao(), ioDispatcher)
    }

    /**
     * Call this method in all application components that may be created at app startup/restoring
     * (e.g. in onCreate of activities and services)
     */
    fun init(context: Context) {
        applicationContext = context
    }
}