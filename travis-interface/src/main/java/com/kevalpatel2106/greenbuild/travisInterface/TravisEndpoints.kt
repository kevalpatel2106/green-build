/*
 * Copyright 2018 Keval Patel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kevalpatel2106.greenbuild.travisInterface

import com.kevalpatel2106.ci.greenbuild.base.ciInterface.ServerInterface
import com.kevalpatel2106.grrenbuild.entities.Cron
import com.kevalpatel2106.greenbuild.travisInterface.entities.TravisEnvVars
import com.kevalpatel2106.greenbuild.travisInterface.response.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by Keval on 17/04/18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal interface TravisEndpoints {

    /**
     * Get the information of the current user. It is required to send the auth header with this
     * endpoint.
     *
     * @see <a href="https://developer.travis-ci.org/explore/user">API Explorer</a>
     */
    @GET("user")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun getMyProfile(): Observable<ResponseMyAccount>

    @GET("repo/{repoId}/branches")
    @Headers("Travis-API-Version: 3", "Add-Auth: true", "Cache-Time: 300" /* 5 min */)
    fun getBranches(
            @Path("repoId") repoId: String,
            @Query("limit") limit: Int = ServerInterface.PAGE_SIZE,
            @Query("offset") offset: Int
    ): Observable<BranchesResponse>

    @GET("repos")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun getMyRepos(
            @Query("sort_by") sortBy: String,
            @Query("limit") limit: Int = ServerInterface.PAGE_SIZE,
            @Query("offset") offset: Int,
            @Query("active") onlyActive: Boolean = false,
            @Query("include") include: String = "repository.last_started_build"
    ): Observable<ResponseMyRepo>

    @GET("builds")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun getRecentBuilds(
            @Query("limit") limit: Int = ServerInterface.PAGE_SIZE,
            @Query("offset") offset: Int,
            @Query("sort_by") sortBy: String,
            @Query("state") state: String? = null
    ): Observable<ResponseRecentBuilds>

    @GET("repo/{repoId}/builds")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun getBuildsForRepo(
            @Path("repoId") repoId: String,
            @Query("limit") limit: Int = ServerInterface.PAGE_SIZE,
            @Query("offset") offset: Int,
            @Query("sort_by") sortBy: String,
            @Query("state") state: String? = null
    ): Observable<ResponseBuildsForRepo>

    @GET("repo/{repoId}/env_vars")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun getEnvVariablesForRepo(
            @Path("repoId") repoId: String
    ): Observable<EnvVarsResponse>

    @GET("repo/{repoId}/caches")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun getCachesForRepo(
            @Path("repoId") repoId: String
    ): Observable<CachesListResponse>

    @DELETE("repo/{repoId}/caches")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun deleteCacheByBranch(
            @Path("repoId") repoId: String,
            @Query("commitBranch") branchName: String
    ): Observable<DeleteCacheResponse>

    @DELETE("repo/{repoId}/env_var/{varId}")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun deleteEnvVariable(
            @Path("repoId") repoId: String,
            @Path("varId") envVarId: String
    ): Observable<EmptyResponse>

    @PATCH("repo/{repoId}/env_var/{varId}")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun editEnvVariable(
            @Path("repoId") repoId: String,
            @Path("varId") envVarId: String,
            @Query("env_var.name") envName: String,
            @Query("env_var.value") envValue: String,
            @Query("env_var.public") isPublic: Boolean
    ): Observable<TravisEnvVars>

    @GET("repo/{repoId}/crons")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun getCronList(
            @Path("repoId") repoId: String,
            @Query("limit") limit: Int = ServerInterface.PAGE_SIZE,
            @Query("offset") offset: Int
    ): Observable<CronListResponse>

    @DELETE("cron/{cronId}")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun deleteCron(
            @Path("cronId") cronId: String
    ): Observable<EmptyResponse>

    @POST("/repo/{repoid}/branch/{branch}/cron")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    @FormUrlEncoded
    fun scheduleNewCron(
            @Path("repoid") repoid: String,
            @Path("branch") branchName: String,
            @Field("cron.interval") interval: String,
            @Field("cron.dont_run_if_recent_build_exists") dontRunIfRecentlyBuilt: Boolean
    ): Observable<Cron>

    @POST("build/{buildId}/restart")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun restartBuild(@Path("buildId") buildId: String): Observable<EmptyResponse>

    @POST("build/{buildId}/cancel")
    @Headers("Travis-API-Version: 3", "Add-Auth: true")
    fun abortBuild(@Path("buildId") buildId: String): Observable<EmptyResponse>
}
