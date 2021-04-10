package com.android.mvvm.web.api

import com.android.mvvm.data.RepoBean
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoApi {

    /**
     * 网络请求方法:@GET、@POST、@PUT、@DELETE、@HEAD(常用)
     * 网络请求标记: @FormUrlEncoded、@Multipart、@Streaming
     * 网络请求参数: @Header &、@Headers、 @Body、@Field 、 @FieldMap、@Part 、 @PartMap、@Query、@QueryMap、@Path、@Url
     */
    @GET("users/{user}/repos")
    suspend fun getRepos(@Path("user") user: String): List<RepoBean>

    // 以下示例文件流的获取
    //    @GET
//    @Streaming
//    suspend fun downloadData(@Url fullUrl: String): ResponseBody

}