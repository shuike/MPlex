package com.skit.mplex.repository

import com.skit.mplex.bean.HubRecentlyAddedResponse
import com.skit.mplex.bean.LibrarySectionsResponseBean
import com.skit.mplex.bean.RecentlyAddedResponse

interface PlexRepository {
    suspend fun getLibraryBean(): LibrarySectionsResponseBean
    suspend fun getRecentlyAdded(id: String): HubRecentlyAddedResponse
    suspend fun getRecentlyAdded(): RecentlyAddedResponse
}