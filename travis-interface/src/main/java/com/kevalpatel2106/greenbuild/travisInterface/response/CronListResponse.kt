package com.kevalpatel2106.greenbuild.travisInterface.response

import com.google.gson.annotations.SerializedName
import com.kevalpatel2106.greenbuild.travisInterface.entities.TravisCron
import javax.annotation.Generated

@Generated("com.robohorse.robopojogenerator")
internal data class CronListResponse(

        @field:SerializedName("crons")
        val crons: List<TravisCron>,

        @field:SerializedName("@pagination")
        val pagination: Pagination
){

        internal data class Pagination(

                @field:SerializedName("is_last")
                val isLast: Boolean,

                @field:SerializedName("offset")
                val offset: Int,

                @field:SerializedName("limit")
                val limit: Int,

                @field:SerializedName("count")
                val count: Int? = null,

                @field:SerializedName("is_first")
                val isFirst: Boolean
        )
}
