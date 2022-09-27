package com.example.flixsterpt1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MovieFragment: Fragment(), OnListFragmentInteractionListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_rv, container, false)
        val progressBar = view.findViewById<View>(R.id.progressBar) as ProgressBar
        val recyclerView = view.findViewById<View>(R.id.movieRV) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ProgressBar, recyclerView: RecyclerView) {
        val client = AsyncHttpClient()
        val params = RequestParams()
//        params["api-key"] = API_KEY
        client["https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", params, object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                // called when response HTTP status is "200 OK"
                progressBar.visibility = View.GONE
                val resultsJSON : String = json.jsonObject.get("results").toString()
                val gson = Gson()
                val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
                val models : List<Movie> = gson.fromJson(resultsJSON, arrayMovieType) // Fix me!
                recyclerView.adapter = MovieRVAdapter(models, this@MovieFragment)
                Log.v("apiCall", "Success!")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                progressBar.visibility = View.GONE
                Log.v("apiCall", "Error with API call! $errorResponse")
            }
        }]
    }
    override fun onItemClick(item: Movie) {
        Toast.makeText(context,"Test: title is ${item.title} and a n overview: ${item.overview}", Toast.LENGTH_LONG).show()
    }
}