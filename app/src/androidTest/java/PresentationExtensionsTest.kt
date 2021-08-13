import androidx.core.text.toSpanned
import androidx.test.platform.app.InstrumentationRegistry
import com.sharkaboi.mediahub.common.extensions.*
import com.sharkaboi.mediahub.data.api.models.anime.AnimeByIDResponse
import com.sharkaboi.mediahub.data.api.models.manga.MangaByIDResponse
import org.junit.Assert
import org.junit.Test
import kotlin.time.ExperimentalTime

class PresentationExtensionsTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun getProgressStringWithNullProgressAndNullTotalReturnsValidString() {
        val progress = null
        val total = null
        val expectedString = "0/??"
        val resultString = context.getProgressStringWith(progress, total)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getProgressStringWithValidProgressAndNullTotalReturnsValidString() {
        val progress = 5
        val total = null
        val expectedString = "5/??"
        val resultString = context.getProgressStringWith(progress, total)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getProgressStringWithNullProgressAndValidTotalReturnsValidString() {
        val progress = null
        val total = 10
        val expectedString = "0/10"
        val resultString = context.getProgressStringWith(progress, total)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getProgressStringWith0ProgressAnd0TotalReturnsValidString() {
        val progress = 0
        val total = 0
        val expectedString = "0/??"
        val resultString = context.getProgressStringWith(progress, total)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getProgressStringWithNullProgressAnd0TotalReturnsValidString() {
        val progress = null
        val total = 0
        val expectedString = "0/??"
        val resultString = context.getProgressStringWith(progress, total)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getProgressStringWithValidProgressAndValidTotalReturnsValidString() {
        val progress = 10
        val total = 10
        val expectedString = "10/10"
        val resultString = context.getProgressStringWith(progress, total)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getEpisodesOfAnimeStringWithValidEpisodeCountReturnsValidString() {
        val episodeCount = 10
        val expectedString = "10 eps"
        val resultString = context.getEpisodesOfAnimeString(episodeCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getEpisodesOfAnimeStringWith1EpisodeCountReturnsValidString() {
        val episodeCount = 1
        val expectedString = "1 ep"
        val resultString = context.getEpisodesOfAnimeString(episodeCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getEpisodesOfAnimeStringWith0EpisodeCountReturnsValidString() {
        val episodeCount = 0
        val expectedString = "N/A eps"
        val resultString = context.getEpisodesOfAnimeString(episodeCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getEpisodesOfAnimeFullStringWithValidEpisodeCountReturnsValidString() {
        val episodeCount = 10.0
        val expectedString = "10 episodes"
        val resultString = context.getEpisodesOfAnimeFullString(episodeCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getEpisodesOfAnimeFullStringWith1EpisodeCountReturnsValidString() {
        val episodeCount = 1.0
        val expectedString = "1 episode"
        val resultString = context.getEpisodesOfAnimeFullString(episodeCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getEpisodesOfAnimeFullStringWith0EpisodeCountReturnsValidString() {
        val episodeCount = 0.0
        val expectedString = "N/A episodes"
        val resultString = context.getEpisodesOfAnimeFullString(episodeCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getDaysCountStringWithValidDaysCountReturnsValidString() {
        val daysCount = 10L
        val expectedString = "10 days"
        val resultString = context.getDaysCountString(daysCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getDaysCountStringWith1DayCountReturnsValidString() {
        val daysCount = 1L
        val expectedString = "1 day"
        val resultString = context.getDaysCountString(daysCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getDaysCountStringWith0DaysCountReturnsValidString() {
        val daysCount = 0L
        val expectedString = "N/A days"
        val resultString = context.getDaysCountString(daysCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getVolumesOfMangaStringWithValidVolumeCountReturnsValidString() {
        val volCount = 10
        val expectedString = "10 vols"
        val resultString = context.getVolumesOfMangaString(volCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getVolumesOfMangaStringWith1VolumeCountReturnsValidString() {
        val volCount = 1
        val expectedString = "1 vol"
        val resultString = context.getVolumesOfMangaString(volCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getVolumesOfMangaStringWith0VolumeCountReturnsValidString() {
        val volCount = 0
        val expectedString = "N/A vols"
        val resultString = context.getVolumesOfMangaString(volCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getChaptersOfMangaStringWithValidChapterCountReturnsValidString() {
        val chapCount = 10
        val expectedString = "10 chaps"
        val resultString = context.getChaptersOfMangaString(chapCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getChaptersOfMangaStringWith1ChapterCountReturnsValidString() {
        val volCount = 1
        val expectedString = "1 chap"
        val resultString = context.getChaptersOfMangaString(volCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getChaptersOfMangaStringWith0ChapterCountReturnsValidString() {
        val volCount = 0
        val expectedString = "N/A chaps"
        val resultString = context.getChaptersOfMangaString(volCount)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getAnimeSeasonStringWithNullSeasonAndNullYearReturnsValidString() {
        val season = null
        val year = null
        val expectedString = "Season : N/A"
        val resultString = context.getAnimeSeasonString(season, year)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getAnimeSeasonStringWithNullSeasonAnd0YearReturnsValidString() {
        val season = null
        val year = 0
        val expectedString = "Season : N/A"
        val resultString = context.getAnimeSeasonString(season, year)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getAnimeSeasonStringWithValidSeasonAndNullYearReturnsValidString() {
        val season = "Summer"
        val year = null
        val expectedString = "Season : N/A"
        val resultString = context.getAnimeSeasonString(season, year)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getAnimeSeasonStringWithValidSeasonAndValidYearReturnsValidString() {
        val season = "Summer"
        val year = 2020
        val expectedString = "Summer 2020"
        val resultString = context.getAnimeSeasonString(season, year)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getAnimeOriginalSourceStringWithNullSourceReturnsValidString() {
        val source = null
        val expectedString = "From N/A"
        val resultString = context.getAnimeOriginalSourceString(source)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getAnimeOriginalSourceStringWithLowerCaseSourceReturnsValidString() {
        val source = "manga"
        val expectedString = "From Manga"
        val resultString = context.getAnimeOriginalSourceString(source)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getAnimeOriginalSourceStringWithUnderscoreSourceReturnsValidString() {
        val source = "light_novel"
        val expectedString = "From Light novel"
        val resultString = context.getAnimeOriginalSourceString(source)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getMediaTypeStringWithWithValidMediaTypeReturnsValidString() {
        val mediaType = "Movie"
        val expectedString = "Type : MOVIE"
        val resultString = context.getMediaTypeStringWith(mediaType)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getRatingStringWithRatingWithWithNullRatingReturnsValidString() {
        val rating = null
        val expectedString = "★ 0.0"
        val resultString = context.getRatingStringWithRating(rating)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getRatingStringWithRatingWithWith0RatingReturnsValidString() {
        val rating = 0
        val expectedString = "★ 0.0"
        val resultString = context.getRatingStringWithRating(rating)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getRatingStringWithRatingWithWithHalfRatingReturnsValidString() {
        val rating = 0.5
        val expectedString = "★ 0.5"
        val resultString = context.getRatingStringWithRating(rating)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getRatingStringWithRatingWithWith1RatingReturnsValidString() {
        val rating = 1
        val expectedString = "★ 1"
        val resultString = context.getRatingStringWithRating(rating)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getRatingStringWithRatingWithWithMore9RatingReturnsValidString() {
        val rating = 9.999
        val expectedString = "★ 10.0"
        val resultString = context.getRatingStringWithRating(rating)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getRatingStringWithRatingWithWithLess9RatingReturnsValidString() {
        val rating = 9.125
        val expectedString = "★ 9.1"
        val resultString = context.getRatingStringWithRating(rating)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getAnimeBroadcastTimeWithNullTimeReturnsValidString() {
        val time = null
        val expectedString = "N/A"
        val resultString = context.getAnimeBroadcastTime(time)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getAnimeBroadcastTimeWithNullTimeAndValidDayReturnsValidString() {
        val time = AnimeByIDResponse.Broadcast(
            startTime = null,
            dayOfTheWeek = "sunday"
        )
        val expectedString = "On sunday"
        val resultString = context.getAnimeBroadcastTime(time)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getFormattedAnimeTitlesStringWithNullReturnsValidString() {
        val titles = null
        val expectedString = "N/A".toSpanned()
        val resultString = context.getFormattedAnimeTitlesString(titles)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getFormattedAnimeTitlesStringWithNullTitlesReturnsValidString() {
        val titles = AnimeByIDResponse.AlternativeTitles(
            en = null,
            ja = null,
            synonyms = null
        )
        val resultString = context.getFormattedAnimeTitlesString(titles)
        Assert.assertTrue(resultString.split("N/A").size == 4)
    }

    @Test
    fun getFormattedAnimeTitlesStringWithNullTitlesAndNoSynonymsReturnsValidString() {
        val titles = AnimeByIDResponse.AlternativeTitles(
            en = null,
            ja = null,
            synonyms = emptyList()
        )
        val resultString = context.getFormattedAnimeTitlesString(titles)
        Assert.assertTrue(resultString.split("N/A").size == 4)
    }

    @Test
    fun getFormattedAnimeTitlesStringWithBlankTitlesReturnsValidString() {
        val titles = AnimeByIDResponse.AlternativeTitles(
            en = "     ",
            ja = "    ",
            synonyms = emptyList()
        )
        val resultString = context.getFormattedAnimeTitlesString(titles)
        Assert.assertTrue(resultString.split("N/A").size == 4)
    }

    @Test
    fun getFormattedAnimeTitlesStringWithValidTitlesReturnsValidString() {
        val titles = AnimeByIDResponse.AlternativeTitles(
            en = "english",
            ja = "japanese",
            synonyms = listOf("synonyms")
        )
        val resultString = context.getFormattedAnimeTitlesString(titles)
        Assert.assertFalse(resultString.contains("N/A"))
    }

    @Test
    fun getFormattedMangaTitlesStringWithNullReturnsValidString() {
        val titles = null
        val expectedString = "N/A".toSpanned()
        val resultString = context.getFormattedMangaTitlesString(titles)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getFormattedMangaTitlesStringWithNullTitlesReturnsValidString() {
        val titles = MangaByIDResponse.AlternativeTitles(
            en = null,
            ja = null,
            synonyms = null
        )
        val resultString = context.getFormattedMangaTitlesString(titles)
        Assert.assertTrue(resultString.split("N/A").size == 4)
    }

    @Test
    fun getFormattedMangaTitlesStringWithNullTitlesAndNoSynonymsReturnsValidString() {
        val titles = MangaByIDResponse.AlternativeTitles(
            en = null,
            ja = null,
            synonyms = emptyList()
        )
        val resultString = context.getFormattedMangaTitlesString(titles)
        Assert.assertTrue(resultString.split("N/A").size == 4)
    }

    @Test
    fun getFormattedMangaTitlesStringWithBlankTitlesReturnsValidString() {
        val titles = MangaByIDResponse.AlternativeTitles(
            en = "     ",
            ja = "    ",
            synonyms = emptyList()
        )
        val resultString = context.getFormattedMangaTitlesString(titles)
        Assert.assertTrue(resultString.split("N/A").size == 4)
    }

    @Test
    fun getFormattedMangaTitlesStringWithValidTitlesReturnsValidString() {
        val titles = MangaByIDResponse.AlternativeTitles(
            en = "english",
            ja = "japanese",
            synonyms = listOf("synonyms")
        )
        val resultString = context.getFormattedMangaTitlesString(titles)
        Assert.assertFalse(resultString.contains("N/A"))
    }

    @Test
    fun getAnimeStatsWithNullStatsReturnsValidString() {
        val stats = null
        val expectedString = "N/A".toSpanned()
        val resultString = context.getAnimeStats(stats)
        Assert.assertEquals(expectedString, resultString)
    }

    @Test
    fun getAnimeStatsWithValidStatsReturnsValidString() {
        val stats = AnimeByIDResponse.Statistics(
            numListUsers = 0,
            status = AnimeByIDResponse.Statistics.Status(
                watching = "0",
                completed = "0",
                onHold = "0",
                dropped = "0",
                planToWatch = "0",
            )
        )
        val resultString = context.getAnimeStats(stats)
        Assert.assertTrue(resultString.isNotBlank())
    }

    @Test
    fun getMangaStatsWithNullStatsReturnsValidString() {
        val numListUsers = null
        val numScoredUsers = null
        val resultString = context.getMangaStats(numListUsers, numScoredUsers)
        Assert.assertTrue(resultString.split("N/A").size == 3)
    }

    @Test
    fun getMangaStatsWithValidStatsReturnsValidString() {
        val numListUsers = 0
        val numScoredUsers = 0
        val resultString = context.getMangaStats(numListUsers, numScoredUsers)
        Assert.assertFalse(resultString.contains("N/A"))
    }

    @ExperimentalTime
    @Test
    fun getEpisodeLengthFromSecondsWithNullSecondsReturnsValidString() {
        val seconds = null
        val expectedString = "N/A per ep"
        val resultString = context.getEpisodeLengthFromSeconds(seconds)
        Assert.assertEquals(expectedString, resultString)
    }

    @ExperimentalTime
    @Test
    fun getEpisodeLengthFromSecondsWith0SecondsReturnsValidString() {
        val seconds = 0
        val expectedString = "N/A per ep"
        val resultString = context.getEpisodeLengthFromSeconds(seconds)
        Assert.assertEquals(expectedString, resultString)
    }

    @ExperimentalTime
    @Test
    fun getEpisodeLengthFromSecondsWithNegativeSecondsReturnsValidString() {
        val seconds = -1
        val expectedString = "N/A per ep"
        val resultString = context.getEpisodeLengthFromSeconds(seconds)
        Assert.assertEquals(expectedString, resultString)
    }

    @ExperimentalTime
    @Test
    fun getEpisodeLengthFromSecondsWithValidHourSecondsReturnsValidString() {
        val seconds = 90 * 60
        val expectedString = "1h 30m per ep"
        val resultString = context.getEpisodeLengthFromSeconds(seconds)
        Assert.assertEquals(expectedString, resultString)
    }

    @ExperimentalTime
    @Test
    fun getEpisodeLengthFromSecondsWithValidMinutesSecondsReturnsValidString() {
        val seconds = 30 * 60
        val expectedString = "30m per ep"
        val resultString = context.getEpisodeLengthFromSeconds(seconds)
        Assert.assertEquals(expectedString, resultString)
    }

    @ExperimentalTime
    @Test
    fun getAiringTimeFormattedWith0TimeReturnsValidString() {
        val time = GetNextAiringAnimeEpisodeQuery.NextAiringEpisode(
            timeUntilAiring = 0,
            episode = 7
        )
        val expectedString = "Episode 7 airs in 0h 0m 0s"
        val resultString = context.getAiringTimeFormatted(time)
        Assert.assertEquals(expectedString, resultString)
    }

    @ExperimentalTime
    @Test
    fun getAiringTimeFormattedWithMinutesTimeReturnsValidString() {
        val time = GetNextAiringAnimeEpisodeQuery.NextAiringEpisode(
            timeUntilAiring = 30 * 60,
            episode = 7
        )
        val expectedString = "Episode 7 airs in 30m"
        val resultString = context.getAiringTimeFormatted(time)
        Assert.assertEquals(expectedString, resultString)
    }

    @ExperimentalTime
    @Test
    fun getAiringTimeFormattedWithHoursTimeReturnsValidString() {
        val time = GetNextAiringAnimeEpisodeQuery.NextAiringEpisode(
            timeUntilAiring = 90 * 60,
            episode = 7
        )
        val expectedString = "Episode 7 airs in 1h 30m"
        val resultString = context.getAiringTimeFormatted(time)
        Assert.assertEquals(expectedString, resultString)
    }

    @ExperimentalTime
    @Test
    fun getAiringTimeFormattedWithDaysTimeReturnsValidString() {
        val time = GetNextAiringAnimeEpisodeQuery.NextAiringEpisode(
            timeUntilAiring = 90 * 24 * 60 * 60,
            episode = 7
        )
        val expectedString = "Episode 7 airs in 90d 0h 0m"
        val resultString = context.getAiringTimeFormatted(time)
        Assert.assertEquals(expectedString, resultString)
    }
}
