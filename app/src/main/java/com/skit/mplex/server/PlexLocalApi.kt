package com.skit.mplex.server

import com.skit.mplex.bean.HubRecentlyAddedResponse
import com.skit.mplex.bean.LibrarySectionsResponseBean
import com.skit.mplex.bean.MovieMetaDataResponse
import com.skit.mplex.bean.NewestResponse
import com.skit.mplex.bean.RecentlyAddedResponse
import com.skit.mplex.bean.TVShowChildMetaDataResponse
import com.skit.mplex.bean.TvShowChildrenResponse
import com.skit.mplex.bean.TvShowMetaDataResponse
import com.skit.mplex.bean.TvShowSeasonChildrenResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlexLocalApi {
    @GET("library/sections")
    suspend fun getLibraryBean(): LibrarySectionsResponseBean

    @GET("library/sections/{directory_id}/all")
    suspend fun getNewest(@Path("directory_id") id: String): NewestResponse

    @GET("library/metadata/{ratingKey}?includeConcerts=1&includeExtras=1&includeOnDeck=1&includePopularLeaves=1&includePreferences=1&includeReviews=1&includeChapters=1&includeStations=1&includeExternalMedia=1&asyncAugmentMetadata=1&asyncCheckFiles=1&asyncRefreshAnalysis=1&asyncRefreshLocalMediaAgent=1")
    suspend fun getTvShowMetaData(@Path("ratingKey") id: String): TvShowMetaDataResponse

    @GET("library/metadata/{ratingKey}?includeConcerts=1&includeExtras=1&includeOnDeck=1&includePopularLeaves=1&includePreferences=1&includeReviews=1&includeChapters=1&includeStations=1&includeExternalMedia=1&asyncAugmentMetadata=1&asyncCheckFiles=1&asyncRefreshAnalysis=1&asyncRefreshLocalMediaAgent=1")
    suspend fun getTvShowChildMetaData(@Path("ratingKey") id: String): TVShowChildMetaDataResponse

    @GET("library/metadata/{ratingKey}/children?excludeAllLeaves=1&includeUserState=1")
    suspend fun getTvShowChildren(@Path("ratingKey") id: String): TvShowChildrenResponse

    @GET("library/metadata/{seasonRingKey}/children?excludeAllLeaves=1&includeUserState=1")
    suspend fun getTvShowSeasonChildren(@Path("seasonRingKey") id: String): TvShowSeasonChildrenResponse

    @GET("library/metadata/{ratingKey}?includeConcerts=1&includeExtras=1&includeOnDeck=1&includePopularLeaves=1&includePreferences=1&includeReviews=1&includeChapters=1&includeStations=1&includeExternalMedia=1&asyncAugmentMetadata=1&asyncCheckFiles=1&asyncRefreshAnalysis=1&asyncRefreshLocalMediaAgent=1")
    suspend fun getMovieMetaData(@Path("ratingKey") id: String): MovieMetaDataResponse

    @GET("hubs/promoted?includeMeta=1&excludeFields=summary&excludeContinueWatching=1")
    suspend fun getRecentlyAdded(@Query("contentDirectoryID") id: String): HubRecentlyAddedResponse

    @GET("library/recentlyAdded")
    suspend fun getRecentlyAdded(): RecentlyAddedResponse

    @GET("hubs/continueWatching?includeMeta=1&excludeFields=summary")
    suspend fun getContinueWatching(@Query("contentDirectoryID") id: String): HubRecentlyAddedResponse

}