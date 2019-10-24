package net.berkayak.trendmovies.model


import com.google.gson.annotations.SerializedName

data class TrendList(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val trendListBeans: List<TrendListBean>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)