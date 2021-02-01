package com.android.mvvm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "owner_table")
@JsonClass(generateAdapter = true)
data class OwnerBean(
    @PrimaryKey
    val id: Int,
    val name: String? = null,
    val avatar_url: String? = null,
    val url: String? = null,
    val html_url: String? = null
)