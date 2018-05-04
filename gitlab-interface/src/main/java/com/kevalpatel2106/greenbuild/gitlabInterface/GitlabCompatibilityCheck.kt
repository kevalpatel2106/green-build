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

package com.kevalpatel2106.greenbuild.gitlabInterface

import com.kevalpatel2106.ci.greenbuild.base.ciInterface.CompatibilityCheck

/**
 * Created by Kevalpatel2106 on 04-May-18.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class GitlabCompatibilityCheck : CompatibilityCheck {
    /**
     * Returns true if the CI server provides way to list all the recent builds (for all the repositories)
     * else false.
     */
    override fun isRecentBuildsListSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Returns true if the CI server provides way to list all the repository else false.
     */
    override fun isRepoListingSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Returns true if the CI server provides way to list all the builds for the repository. Application
     * can retrieve the  builds list by calling [ServerInterface.getBuildList].
     *
     * @see ServerInterface.getBuildList
     */
    override fun isBuildsListByRepoSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Check if the CI platform supports an API for restarting the build.
     *
     * @return True if CI supports restarting the build which is completed.
     */
    override fun isRestartBuildSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Check if the CI platform supports an API for aborting the running build.
     *
     * @return True if CI supports aborting the build which is running.
     */
    override fun isAbortBuildSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to check if the CI provider allows to list the environment variables.  Application
     * can retrieve the  builds list by calling [ServerInterface.getEnvironmentVariablesList].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.getEnvironmentVariablesList
     */
    override fun isEnvironmentVariableListSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to check if the CI provider allows to add the environment variables.
     *
     * @return true if the feature is possible.
     */
    override fun isAddEnvironmentVariableSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to check if the CI provider allows to delete the environment variables. Application
     * can retrieve the  builds list by calling [ServerInterface.deleteEnvironmentVariable].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.deleteEnvironmentVariable
     */
    override fun isEnvironmentVariableDeleteSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to check if the CI provider allows to edit the public environment variables. Application
     * can retrieve the  builds list by calling [ServerInterface.editEnvVariable].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.editEnvVariable
     */
    override fun isPublicEnvironmentVariableEditSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to check if the CI provider allows to edit the private/secrete environment variables.
     * Application can retrieve the  builds list by calling [ServerInterface.editEnvVariable].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.editEnvVariable
     */
    override fun isPrivateEnvironmentVariableEditSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to check if the CI provider allows to list all the cron jobs.  Application
     * can retrieve the  builds list by calling [ServerInterface.getCronsList].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.getCronsList
     */
    override fun isCronJobsListSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to check if the CI provider allows to schedule new cron jobs.
     *
     * @return true if the feature is possible.
     */
    override fun isAddCronJobsSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to check if the CI provider allows to start the cron job manually. Application
     * can retrieve the  builds list by calling [ServerInterface.startCronManually].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.startCronManually
     */
    override fun isInitiateCronJobsSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to check if the CI provider allows to delete the cron job. Application
     * can retrieve the  builds list by calling [ServerInterface.deleteCron].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.deleteCron
     */
    override fun isDeleteCronJobsSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to check if the CI platform provides option to don't run cron if the recent build exist.
     *
     * @return true if the feature is possible.
     */
    override fun isDontRunIfRecentBuildExistSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to check if the CI provider allows to list all the caches for the repository. Application
     * can retrieve the  builds list by calling [ServerInterface.getCachesList].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.getCachesList
     */
    override fun isCacheListListSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Method to check if the CI provider allows to delete the cache. Application can retrieve the
     * builds list by calling [ServerInterface.deleteCache].
     *
     * @return true if the feature is possible.
     * @see ServerInterface.deleteCache
     */
    override fun isCacheDeleteSupported(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
