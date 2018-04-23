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

package com.kevalpatel2106.ci.greenbuild.base.view

import android.content.Context
import android.support.annotation.CallSuper
import android.support.annotation.MainThread
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevalpatel2106.ci.greenbuild.base.R
import com.kevalpatel2106.ci.greenbuild.base.view.PageRecyclerViewAdapter.RecyclerViewListener

/**
 * Created by Keval on 31-May-17.
 * Base class for loading [RecyclerView.Adapter] that handles page
 * complete callbacks. Use this class instead of [RecyclerView.Adapter] through out the application.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 * @see RecyclerViewListener
 */

abstract class PageRecyclerViewAdapter<VH : PageRecyclerViewAdapter.PageViewHolder, T>(
        protected val context: Context,
        protected val collection: ArrayList<T>,
        protected val listener: RecyclerViewListener<T>?
) : RecyclerView.Adapter<PageRecyclerViewAdapter.PageViewHolder>() {

    @Suppress("PrivatePropertyName")
    private val TYPE_LOADER = 5364

    @Suppress("PrivatePropertyName")
    private val INITIAL_PAGE_NUMBER = 0 /* Considering first page is 0 */

    /**
     * Clear the items list/collection and reset the adapter to it's initial state.
     */
    fun reset() {
        collection.clear()
        notifyDataSetChanged()

        //Release pagination lock
        lockPagination = false

        //Consider next page
        hasNextPage = true
    }

    /**
     * Boolean to indicate if there are any more pages?
     */
    var hasNextPage = true

    /**
     * Boolean to set true to lock the pagination. Once the loading of next page completes, we will
     * release the lock and enable page complete callbacks again.
     *
     * @see onPageLoadComplete
     */
    private var lockPagination = false

    /**
     * Get the item for the given position in the adapter.
     *
     * @param pos Position of the item to get.
     * @return Item from the list at given position or null if the position is greater than or
     * equal to the adapter data list size.
     */
    protected fun getItem(pos: Int): T? = if (pos < collection.size) {
        collection[pos]
    } else {
        null
    }

    @CallSuper
    override fun getItemCount(): Int = if (collection.size >= getPageSize()
            && hasNextPage
            && listener != null)
        collection.size + 1 /* Number items + Loader */
    else
        collection.size /* Number items */

    @CallSuper
    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        //Notify on page complete
        if (listener != null        /* Listener is not provided indicates subscriber doesn't want pagination. */
                && hasNextPage      /* If there is next page. */
                && !lockPagination  /* If previous page complete call completed. */
                && position == collection.lastIndex /* Last item in the list */
                && collection.size >= getPageSize()  /* If number of items cannot fill the first page. */) {
            listener.onPageComplete(collection.size)
            lockPagination = true
        }

        if (holder is LoaderViewHolder) {
            // Progressbar will start loading automatically.
            // Do nothing.
        } else {

            val item = getItem(position)!!

            //Call abstract method
            @Suppress("UNCHECKED_CAST")
            bindView(holder as VH, item)
        }
    }

    @CallSuper
    override fun getItemViewType(position: Int): Int {
        return if (position == collection.size) {   /* This is page loader view */
            TYPE_LOADER
        } else {
            prepareViewType(position)    /* Ask for the view type to concrete class */
        }
    }

    @CallSuper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        return if (viewType == TYPE_LOADER) {

            //Prepare view holder for loader row.
            LoaderViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.layout_loader, parent, false))
        } else {

            //Prepare the view holder by view type.
            prepareViewHolder(parent, viewType)
        }
    }

    /**
     * Call this method to release pagination lock once you complete the loading of next page.
     */
    fun onPageLoadComplete() {
        //Release the pagination lock
        lockPagination = false
    }

    /**
     * Here you should bind the view holder with your view and data.

     * @param holder   [RecyclerView.ViewHolder]
     * @param item Item to bind with the view holder.
     * @see RecyclerView.Adapter.bindViewHolder
     */
    @MainThread
    abstract fun bindView(holder: VH, item: T)

    /**
     * Prepare the view hoder for the given view type.
     *
     * @param parent Parent view.
     * @param viewType View holder type.
     * @see RecyclerView.Adapter.createViewHolder
     */
    @MainThread
    abstract fun prepareViewHolder(parent: ViewGroup, viewType: Int): VH

    /**
     * Get the view holder type.
     *
     * @see RecyclerView.Adapter.getItemViewType
     */
    @MainThread
    abstract fun prepareViewType(position: Int): Int

    abstract fun getPageSize(): Int

    /**
     * Loading view holder
     */
    internal class LoaderViewHolder(itemView: View) : PageViewHolder(itemView)

    /**
     * Base view holder for pagination
     */
    open class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * Listener to get notify when the list ends.
     */
    interface RecyclerViewListener<in T> {

        /**
         * Callback to call when whole list is displayed.
         *
         * @param pos Position of the last item
         */
        @MainThread
        fun onPageComplete(pos: Int)
    }
}
