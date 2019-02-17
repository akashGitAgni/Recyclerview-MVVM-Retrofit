package com.akash.postsandcomments

import com.akash.postsandcomments.comments.CommentsViewModel
import com.akash.postsandcomments.data.Api
import com.akash.postsandcomments.data.Repository
import com.akash.postsandcomments.data.RepositoryImpl
import com.akash.postsandcomments.posts.PostsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataSourceModule = module {

    single { createRetrofitInstance() }

    factory { get<Retrofit>().create(Api::class.java) }

    single { RepositoryImpl(get()) as Repository }

}

val viewmodelModule = module {
    viewModel { PostsViewModel(get()) }

    viewModel { CommentsViewModel(get()) }
}

fun createRetrofitInstance(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}