package com.example.flixsterpt1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

    /**
     * [RecyclerView.Adapter] that can display a [Movie] and makes a call to the
     * specified [OnListFragmentInteractionListener].
     */
    class MovieRVAdapter(
        private val movies: List<Movie>,
        private val mListener: OnListFragmentInteractionListener?
    )
        : RecyclerView.Adapter<MovieRVAdapter.MovieViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie, parent, false)
            return MovieViewHolder(view)
        }

        /**
         * This inner class lets us refer to all the different View elements
         * (Yes, the same ones as in the XML layout files!)
         */
        inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            var mMovie: Movie? = null
            val mMovieTitle: TextView = mView.findViewById<View>(R.id.title) as TextView
            val mMovieOverview: TextView = mView.findViewById<View>(R.id.overview) as TextView
            val mPosterPath: ImageView = mView.findViewById(R.id.poster) as ImageView

            override fun toString(): String {
                return mMovieTitle.toString() + " '" + mMovieOverview.text + "'"
            }
        }

        /**
         * This lets us "bind" each Views in the ViewHolder to its' actual data!
         */
        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            val movie = movies[position]

            holder.mMovie = movie
            holder.mMovieTitle.text = movie.title
            holder.mMovieOverview.text = movie.overview
            Glide.with(holder.mView)
                .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
                .centerInside()
                .into(holder.mPosterPath)

            holder.mView.setOnClickListener {
                holder.mMovie?.let { movie ->
                    mListener?.onItemClick(movie)
                }
            }
        }

        /**
         * Remember: RecyclerView adapters require a getItemCount() method.
         */
        override fun getItemCount(): Int {
            return movies.size
        }
    }