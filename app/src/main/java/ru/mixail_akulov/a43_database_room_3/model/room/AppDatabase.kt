package ru.mixail_akulov.a43_database_room_3.model.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mixail_akulov.a43_database_room_3.model.accounts.room.AccountsDao
import ru.mixail_akulov.a43_database_room_3.model.accounts.room.entities.AccountDbEntity
import ru.mixail_akulov.a43_database_room_3.model.boxes.room.BoxesDao
import ru.mixail_akulov.a43_database_room_3.model.boxes.room.entities.AccountBoxSettingDbEntity
import ru.mixail_akulov.a43_database_room_3.model.boxes.room.entities.BoxDbEntity
import ru.mixail_akulov.a43_database_room_3.model.boxes.room.views.SettingDbView

@Database(
    version = 3,
    entities = [
        AccountDbEntity::class,
        BoxDbEntity::class,
        AccountBoxSettingDbEntity::class
    ],
    views = [
        SettingDbView::class
    ],
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2,
            spec = AutoMigrationSpec1To2::class
        )
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getBoxesDao(): BoxesDao

}