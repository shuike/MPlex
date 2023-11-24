package com.skit.mplex.subtitles

import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector

@OptIn(UnstableApi::class)
class SubtitlesSelectManager {
    var selectedId: String = ""
        private set

    fun selectSubtitles(
        trackSelector: DefaultTrackSelector,
        subtitlesData: SubtitlesData,
        playerSubtitles: List<Tracks.Group>
    ) {
        val id = when (subtitlesData) {
            is SubtitlesData.Inner -> subtitlesData.id
            is SubtitlesData.Remote -> subtitlesData.id
        }
        val tracksGroup = playerSubtitles.find { group ->
            group.mediaTrackGroup.getFormat(0).id == id
        }
        if (tracksGroup != null) {
            trackSelector.setParameters(
                trackSelector.parameters.buildUpon()
                    .setPreferredTextLanguage(tracksGroup.getTrackFormat(0).language)
                    .setOverrideForType(
                        TrackSelectionOverride(
                            tracksGroup.mediaTrackGroup, 0
                        )
                    )
                    .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, false)
            )
            selectedId = id
        } else {
            trackSelector.setParameters(
                trackSelector.parameters.buildUpon()
                    .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, true)
            )
            selectedId = ""
        }
    }
}