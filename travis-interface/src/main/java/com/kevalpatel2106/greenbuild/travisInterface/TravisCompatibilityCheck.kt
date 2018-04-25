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

import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck
import timber.log.Timber

/**
 * Created by Keval on 23/04/18.
 * This class provides the compatibility information for the features listed in [CompatibilityCheck]
 * for Travis CI services.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 * @see CompatibilityCheck
 */
class TravisCompatibilityCheck : CompatibilityCheck {

    companion object {

        fun get(baseUrl: String): TravisCompatibilityCheck? {
            return when {
                baseUrl == Constants.TRAVIS_CI_ORG -> TravisCompatibilityCheck()
                baseUrl == Constants.TRAVIS_CI_COM -> TravisCompatibilityCheck()
                baseUrl.startsWith("https://travis.") && baseUrl.endsWith("/api/") -> {
                    TravisCompatibilityCheck()
                }
                else -> {
                    Timber.i("Not a travis ci server: $baseUrl")
                    null
                }
            }
        }
    }

    /**
     * Returns true if the CI server provides way to list all the recent builds (for all the repositories)
     * else false.
     */
    override fun isRecentBuildsListSupported() = true

    /**
     * Returns true if the CI server provides way to list all the builds for the repository. Application
     * can retrieve the  builds list by calling [TravisServerInterface.getBuildList].
     *
     * @see TravisServerInterface.getBuildList
     */
    override fun isBuildsListByRepoSupported() = true

    /**
     * Method to check if the CI provider allows to list the environment variables.  Application
     * can retrieve the  builds list by calling [TravisServerInterface.getEnvironmentVariablesList].
     *
     * @return true if the feature is possible.
     * @see TravisServerInterface.getEnvironmentVariablesList
     */
    override fun isEnvironmentVariableListSupported() = true

    /**
     * Method to check if the CI provider allows to add the environment variables. This method is
     * not supported.
     */
    override fun isAddEnvironmentVariableSupported() = false

    /**
     * Method to check if the CI provider allows to delete the environment variables. Application
     * can retrieve the  builds list by calling [TravisServerInterface.deleteEnvironmentVariable].
     *
     * @return true if the feature is possible.
     * @see TravisServerInterface.deleteEnvironmentVariable
     */
    override fun isEnvironmentVariableDeleteSupported() = true

    /**
     * Method to check if the CI provider allows to edit the public environment variables. Application
     * can retrieve the  builds list by calling [TravisServerInterface.editEnvVariable].
     *
     * @return true if the feature is possible.
     * @see TravisServerInterface.editEnvVariable
     */
    override fun isPublicEnvironmentVariableEditSupported() = true

    /**
     * Method to check if the CI provider allows to edit the private/secrete environment variables.
     * Application can retrieve the  builds list by calling [TravisServerInterface.editEnvVariable].
     *
     * @return true if the feature is possible.
     * @see TravisServerInterface.editEnvVariable
     */
    override fun isPrivateEnvironmentVariableEditSupported() = true

    /**
     * Method to check if the CI provider allows to list all the cron jobs.  Application
     * can retrieve the  builds list by calling [TravisServerInterface.getCronsList].
     *
     * @return true if the feature is possible.
     * @see TravisServerInterface.getCronsList
     */
    override fun isCronJobsListSupported() = true

    /**
     * TODO
     * Method to check if the CI provider allows to schedule new cron jobs. Application
     * can retrieve the  builds list by calling [ServerInterface.getCronsList].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.getCronsList
     */
    override fun isAddCronJobsSupported() = true

    /**
     * Method to check if the CI provider allows to list all the caches for the repository. Application
     * can retrieve the  builds list by calling [TravisServerInterface.getCachesList].
     *
     * @return true if the feature is possible.
     * @see TravisServerInterface.getCachesList
     */
    override fun isCacheListListSupported() = true

    /**
     * Method to check if the CI provider allows to delete the cache. Application can retrieve the
     * builds list by calling [TravisServerInterface.deleteCache].
     *
     * @return true if the feature is possible.
     * @see TravisServerInterface.deleteCache
     */
    override fun isCacheDeleteSupported() = true

    /**
     * Method to check if the CI provider allows to start the cron job manually. This is not supported
     * on Travis CI platform
     *
     * @return true if the feature is possible.
     */
    override fun isInitiateCronJobsSupported(): Boolean {
        return false
    }

    /**
     * Method to check if the CI provider allows to delete the cron job. Application
     * can retrieve the  builds list by calling [TravisServerInterface.deleteCron].
     *
     * @return true if the feature is possible.
     * @see TravisServerInterface.deleteCron
     */
    override fun isDeleteCronJobsSupported(): Boolean = true

    /**
     * Check if the CI platform supports an API for restarting the build.
     *
     * @return True if CI supports restarting the build which is completed.
     */
    override fun isRestartBuildSupported() = true

    /**
     * Check if the CI platform supports an API for aborting the running build.
     *
     * @return True if CI supports aborting the build which is running.
     */
    override fun isAbortBuildSupported() = true

    /**
     * Method to check if the CI platform provides option to don't run cron if the recent build exist.
     *
     * @return true if the feature is possible.
     */
    override fun isDontRunIfRecentBuildExistSupported() = true
}
