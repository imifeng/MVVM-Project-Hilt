package com.android.mvvm.data.dao

import androidx.room.*
import com.android.mvvm.data.RepoBean

@Dao
interface RepoBeanDao {

    @Insert
    suspend fun insert(repo: RepoBean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoBean>)

    @Update
    suspend fun update(repo: RepoBean)

    @Delete
    suspend fun delete(repo: RepoBean)

    @Query("DELETE FROM repo_table")
    suspend fun deleteAllRepos()

    @Query("SELECT * FROM repo_table ORDER BY id DESC")
    suspend fun getAllRepos(): List<RepoBean>

    @Query("SELECT * FROM repo_table WHERE id = :id")
    suspend fun getReposById(id: Int): List<RepoBean>

}