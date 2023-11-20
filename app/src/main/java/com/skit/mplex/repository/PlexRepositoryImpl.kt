package com.skit.mplex.repository

import com.skit.mplex.bean.HubRecentlyAddedResponse
import com.skit.mplex.bean.LibrarySectionsResponseBean
import com.skit.mplex.bean.RecentlyAddedResponse
import com.skit.mplex.net.HttpFactory
import com.skit.mplex.server.PlexLocalApi

class PlexRepositoryImpl : PlexRepository {
    override suspend fun getLibraryBean(): LibrarySectionsResponseBean = plexServer.getLibraryBean()

    override suspend fun getRecentlyAdded(id: String): HubRecentlyAddedResponse =
        plexServer.getRecentlyAdded(id)

    override suspend fun getRecentlyAdded(): RecentlyAddedResponse = plexServer.getRecentlyAdded()

    private val plexServer: PlexLocalApi by lazy {
        HttpFactory.localRetrofit.create(PlexLocalApi::class.java)
    }
}