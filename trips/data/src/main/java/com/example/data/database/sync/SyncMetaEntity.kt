package com.example.data.database.sync

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_meta")
data class SyncMetaEntity(
    @ColumnInfo(name = "meta_key")
    @PrimaryKey val key: String,
    val longValue: Long?,
    val stringValue: String?,
)
