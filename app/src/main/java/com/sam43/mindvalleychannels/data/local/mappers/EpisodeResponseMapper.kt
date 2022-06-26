package com.sam43.mindvalleychannels.data.local.mappers

import com.sam43.mindvalleychannels.data.local.entity.EpisodeEntity
import com.sam43.mindvalleychannels.data.remote.objects.EpisodesResponse
import javax.inject.Inject

class EpisodeResponseMapper @Inject constructor() : Mapper<EpisodesResponse, List<EpisodeEntity>> {
    override suspend fun map(from: EpisodesResponse): List<EpisodeEntity> =
        from.media.map {
            with(it) {
                EpisodeEntity(
                    type,
                    title,
                    url = coverAsset.url,
                    channelTitle = channel.title
                )
            }
        }
}