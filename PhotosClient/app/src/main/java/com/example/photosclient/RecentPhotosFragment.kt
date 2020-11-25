package com.example.photosclient

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hellotech7.imagesearchapp.FlickrGetter

class RecentPhotosFragment : Fragment() {

    lateinit var downloaderThread:DownloaderThread
    lateinit var photosRecyclerView: RecyclerView
    var items:List<PhotoItem> = mutableListOf()
    lateinit var adapter: PhotosAdapter
    var handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.recent_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_refresh) {

            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        downloaderThread = DownloaderThread(handler, "RecentPhotosFragment")
        val layoutView = inflater.inflate(R.layout.fragment_recent_photos, container, false)
        photosRecyclerView = layoutView.findViewById(R.id.photosRecyclerView)
        photosRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)

        }
        return layoutView
    }

    override fun onResume() {
        super.onResume()
        downloaderThread.start()
        downloaderThread.getLooper()

        adapter = PhotosAdapter(items, context!!, downloaderThread)
        photosRecyclerView.adapter = adapter

        FlickrAsyncTask().execute()

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecentPhotosFragment().apply {
            }
    }

    inner class FlickrAsyncTask() : AsyncTask<Void, Void, List<PhotoItem>>() {


        override fun onPostExecute(result: List<PhotoItem>?) {
            super.onPostExecute(result)
            if(result != null) {
                items = result
                adapter = PhotosAdapter(items, context!!, downloaderThread)
                photosRecyclerView.adapter = adapter
            }
        }

        override fun doInBackground(vararg params: Void?): List<PhotoItem>? {
            return FlickrGetter.getFlickrItems()
        }

    }
}