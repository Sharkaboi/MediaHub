package com.sharkaboi.mediahub.data.cache.models.mappers

import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.data.cache.models.anime.*

object AnimeMapper {
    fun toCacheModel(animeByIDResponse: AnimeByIDResponse): AnimeWithStudios {
        val relatedManga = animeByIDResponse.relatedManga.map {
            RelatedManga(
                animeId = animeByIDResponse.id,
                relatedMangaId = it.node.id,
                title = it.node.title,
                relationType = it.relationType,
                relationTypeFormatted = it.relationTypeFormatted,
                pictureL = it.node.mainPicture?.large,
                pictureM = it.node.mainPicture?.medium
            )
        }
        val studios = animeByIDResponse.studios.map { AnimeStudio(it.id, it.name) }
        val relatedAnime = animeByIDResponse.relatedAnime.map {
            RelatedAnime(
                animeId = animeByIDResponse.id,
                relatedAnimeId = it.node.id,
                title = it.node.title,
                relationType = it.relationType,
                relationTypeFormatted = it.relationTypeFormatted,
                pictureL = it.node.mainPicture?.large,
                pictureM = it.node.mainPicture?.medium,
            )
        }
        val recommendations = animeByIDResponse.recommendations.map {
            AnimeRecommendations(
                animeId = animeByIDResponse.id,
                numRecommendations = it.numRecommendations,
                recommendationAnimeId = it.node.id,
                title = it.node.title,
                pictureL = it.node.mainPicture?.large,
                pictureM = it.node.mainPicture?.medium,
            )
        }
        val pictures = animeByIDResponse.pictures.map {
            AnimePicture(
                animeId = animeByIDResponse.id,
                large = it.large,
                medium = it.medium
            )
        }
        val genres = animeByIDResponse.genres?.map {
            AnimeGenre(it.id, it.name)
        }
        val anime = AnimeDetails(
            animeId = animeByIDResponse.id,
            title = animeByIDResponse.title,
            alternativeTitleEn = animeByIDResponse.alternativeTitles?.en,
            alternativeTitleJa = animeByIDResponse.alternativeTitles?.ja,
            alternativeTitleSynonyms = animeByIDResponse.alternativeTitles?.synonyms?.joinToString(),
            episodeDuration = animeByIDResponse.averageEpisodeDuration,
            animeDetailsBackground = animeByIDResponse.background,
            broadcastDayOfTheWeek = animeByIDResponse.broadcast?.dayOfTheWeek,
            broadcastStartTime = animeByIDResponse.broadcast?.startTime,
            createdAt = animeByIDResponse.createdAt,
            endDate = animeByIDResponse.endDate,
            pictureL = animeByIDResponse.mainPicture?.large,
            pictureM = animeByIDResponse.mainPicture?.medium,
            mean = animeByIDResponse.mean,
            mediaType = animeByIDResponse.mediaType,
            isReWatching = animeByIDResponse.myListStatus?.isReWatching,
            numEpisodesWatched = animeByIDResponse.myListStatus?.numEpisodesWatched,
            personalScore = animeByIDResponse.myListStatus?.score,
            watchingStatus = animeByIDResponse.myListStatus?.status,
            personalUpdatedAt = animeByIDResponse.myListStatus?.updatedAt,
            nsfw = animeByIDResponse.nsfw,
            numEpisodes = animeByIDResponse.numEpisodes,
            numScoringUsers = animeByIDResponse.numScoringUsers,
            popularity = animeByIDResponse.popularity,
            rank = animeByIDResponse.rank,
            rating = animeByIDResponse.rating,
            source = animeByIDResponse.source,
            startDate = animeByIDResponse.startDate,
            startSeasonString = animeByIDResponse.startSeason?.season,
            startSeasonYear = animeByIDResponse.startSeason?.year,
            numListUsersTotal = animeByIDResponse.numListUsers,
            numListUsersCompleted = animeByIDResponse.statistics?.status?.completed?.toInt(),
            numListUsersDropped = animeByIDResponse.statistics?.status?.dropped?.toInt(),
            numListUsersOnHold = animeByIDResponse.statistics?.status?.onHold?.toInt(),
            numListUsersPlanToWatch = animeByIDResponse.statistics?.status?.planToWatch?.toInt(),
            numListUsersWatching = animeByIDResponse.statistics?.status?.watching?.toInt(),
            airingStatus = animeByIDResponse.status,
            synopsis = animeByIDResponse.synopsis,
            updatedAt = animeByIDResponse.updatedAt
        )
        return AnimeWithStudios(
            anime = AnimeWithGenreAndPicturesAndRecommendationsAndRelatedAnimeAndRelatedManga(
                animeWithGenresAndPicturesAndRecommendationsAndRelatedAnime = AnimeWithGenreAndPicturesAndRecommendationsAndRelatedAnime(
                    animeWithGenresAndPicturesAndRecommendations = AnimeWithGenreAndPicturesAndRecommendations(
                        animeWithGenresAndPictures = AnimeWithGenreAndPictures(
                            animeWithGenres = AnimeWithGenres(
                                anime = anime,
                                genres = genres
                            ),
                            pictures = pictures
                        ),
                        recommendations = recommendations
                    ),
                    relatedAnime = relatedAnime
                ),
                relatedManga = relatedManga
            ),
            studios = studios
        )
    }
}