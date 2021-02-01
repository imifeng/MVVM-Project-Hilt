package com.android.mvvm.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "repo_table")
@JsonClass(generateAdapter = true)
data class RepoBean(
    @PrimaryKey
    val id: Int,
    val name: String? = null,
    @Json(name = "private")
    val privateX: Boolean? = null,
    @Embedded(prefix = "owner_")
    val owner: OwnerBean? = null,
    val html_url: String? = null,
    val description: String? = null

//    val wateringInterval: Int = 7, // how often the plant should be watered, in days
) {
    override fun toString() = name ?: "null"
}