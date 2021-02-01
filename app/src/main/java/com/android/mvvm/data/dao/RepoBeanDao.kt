package com.android.mvvm.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.mvvm.data.RepoBean

@Dao
interface RepoBeanDao {

    @Insert
    fun insert(repo: RepoBean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoBean>)

    @Update
    fun update(repo: RepoBean)

    @Delete
    fun delete(repo: RepoBean)

    @Query("DELETE FROM repo_table")
    fun deleteAllRepos()

    @Query("SELECT * FROM repo_table ORDER BY id DESC")
    fun getAllRepos(): LiveData<List<RepoBean>>

    @Query("SELECT * FROM repo_table WHERE id = :id")
    fun getReposById(id: Int): LiveData<List<RepoBean>>

}