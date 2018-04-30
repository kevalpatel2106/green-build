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

package com.kevalpatel2106.ci.greenbuild.base.ciInterface

/**
 * Created by Keval on 23/04/18.
 * This interface defines the methods that will provide the feature compatibility information to the
 * application for each CI provider. If the feature is compatible/available, UI layer will enable
 * the view for that feature or if the feature is not available for particular CI provider, related
 * feature will disable.
 *
 * UI will check for the compatibility of the feature before preparing the UI and if the
 * feature is available for given CI provider than it will call [ServerInterface] method associated
 * with the feature to retrieve the data.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
interface CompatibilityCheck {

    ////////////////// Builds management ///////////////////

    /**
     * Returns true if the CI server provides way to list all the recent builds (for all the repositories)
     * else false.
     */
    fun isRecentBuildsListSupported(): Boolean

    /**
     * Returns true if the CI server provides way to list all the repository else false.
     */
    fun isRepoListingSupported(): Boolean

    /**
     * Returns true if the CI server provides way to list all the builds for the repository. Application
     * can retrieve the  builds list by calling [ServerInterface.getBuildList].
     *
     * @see ServerInterface.getBuildList
     */
    fun isBuildsListByRepoSupported(): Boolean

    /**
     * Check if the CI platform supports an API for restarting the build.
     *
     * @return True if CI supports restarting the build which is completed.
     */
    fun isRestartBuildSupported(): Boolean

    /**
     * Check if the CI platform supports an API for aborting the running build.
     *
     * @return True if CI supports aborting the build which is running.
     */
    fun isAbortBuildSupported(): Boolean

    ////////////////// Environment variable management ///////////////////

    /**
     * Method to check if the CI provider allows to list the environment variables.  Application
     * can retrieve the  builds list by calling [ServerInterface.getEnvironmentVariablesList].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.getEnvironmentVariablesList
     */
    fun isEnvironmentVariableListSupported(): Boolean

    /**
     * Method to check if the CI provider allows to add the environment variables.
     *
     * @return true if the feature is possible.
     */
    fun isAddEnvironmentVariableSupported(): Boolean

    /**
     * Method to check if the CI provider allows to delete the environment variables. Application
     * can retrieve the  builds list by calling [ServerInterface.deleteEnvironmentVariable].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.deleteEnvironmentVariable
     */
    fun isEnvironmentVariableDeleteSupported(): Boolean

    /**
     * Method to check if the CI provider allows to edit the public environment variables. Application
     * can retrieve the  builds list by calling [ServerInterface.editEnvVariable].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.editEnvVariable
     */
    fun isPublicEnvironmentVariableEditSupported(): Boolean

    /**
     * Method to check if the CI provider allows to edit the private/secrete environment variables.
     * Application can retrieve the  builds list by calling [ServerInterface.editEnvVariable].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.editEnvVariable
     */
    fun isPrivateEnvironmentVariableEditSupported(): Boolean

    ////////////////// Cron management ///////////////////

    /**
     * Method to check if the CI provider allows to list all the cron jobs.  Application
     * can retrieve the  builds list by calling [ServerInterface.getCronsList].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.getCronsList
     */
    fun isCronJobsListSupported(): Boolean

    /**
     * Method to check if the CI provider allows to schedule new cron jobs.
     *
     * @return true if the feature is possible.
     */
    fun isAddCronJobsSupported(): Boolean

    /**
     * Method to check if the CI provider allows to start the cron job manually. Application
     * can retrieve the  builds list by calling [ServerInterface.startCronManually].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.startCronManually
     */
    fun isInitiateCronJobsSupported(): Boolean

    /**
     * Method to check if the CI provider allows to delete the cron job. Application
     * can retrieve the  builds list by calling [ServerInterface.deleteCron].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.deleteCron
     */
    fun isDeleteCronJobsSupported(): Boolean

    /**
     * Method to check if the CI platform provides option to don't run cron if the recent build exist.
     *
     * @return true if the feature is possible.
     */
    fun isDontRunIfRecentBuildExistSupported(): Boolean


    ////////////////// Cache management ///////////////////

    /**
     * Method to check if the CI provider allows to list all the caches for the repository. Application
     * can retrieve the  builds list by calling [ServerInterface.getCachesList].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.getCachesList
     */
    fun isCacheListListSupported(): Boolean

    /**
     * Method to check if the CI provider allows to delete the cache. Application can retrieve the
     * builds list by calling [ServerInterface.deleteCache].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.deleteCache
     */
    fun isCacheDeleteSupported(): Boolean


}
