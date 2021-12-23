import androidx.test.platform.app.InstrumentationRegistry
import com.sharkaboi.mediahub.data.api.enums.*
import com.sharkaboi.mediahub.data.api.enums.AnimeRating.getAnimeRating
import org.junit.Assert
import org.junit.Test

class EnumTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun formattedStringOfAnimeMustContainSameElementsAsEnumValues() {
        Assert.assertEquals(
            UserAnimeSortType.values().size,
            UserAnimeSortType.getFormattedArray(context).size
        )
    }

    @Test
    fun formattedStringOfMangaMustContainSameElementsAsEnumValues() {
        Assert.assertEquals(
            UserMangaSortType.values().size,
            UserMangaSortType.getFormattedArray(context).size
        )
    }

    @Test
    fun getAnimeAiringStatusWithValidStatusReturnsValidString() {
        val inputToExpectedMap = mapOf(
            "finished_airing" to "Finished airing",
            "currently_airing" to "Currently airing",
            "not_yet_aired" to "Yet to be aired",
            "invalid_status" to "Airing status : N/A"
        )
        inputToExpectedMap.forEach { (inputString, expectedString) ->
            Assert.assertEquals(context.getAnimeAiringStatus(inputString), expectedString)
        }
    }

    @Test
    fun getAnimeNsfwRatingWithRatingReturnsValidString() {
        val inputToExpectedMap = mapOf(
            "white" to "SFW",
            "gray" to "NSFW?",
            "black" to "NSFW",
            null to "N/A",
            "invalid_rating" to "SFW : N/A"
        )
        inputToExpectedMap.forEach { (inputString, expectedString) ->
            Assert.assertEquals(context.getAnimeNsfwRating(inputString), expectedString)
        }
    }

    @Test
    fun getMangaNsfwRatingWithRatingReturnsValidString() {
        val inputToExpectedMap = mapOf(
            "white" to "Safe for work (SFW)",
            "gray" to "Maybe not safe for work (NSFW)",
            "black" to "Not safe for work (NSFW)",
            null to "N/A",
            "invalid_rating" to "N/A"
        )
        inputToExpectedMap.forEach { (inputString, expectedString) ->
            Assert.assertEquals(context.getMangaNsfwRating(inputString), expectedString)
        }
    }

    @Test
    fun getMangaPublishStatusWithStatusReturnsValidString() {
        val inputToExpectedMap = mapOf(
            "finished" to "Finished publishing",
            "currently_publishing" to "Currently publishing",
            "not_yet_published" to "Yet to be published",
            "invalid_rating" to "Publishing status : N/A"
        )
        inputToExpectedMap.forEach { (inputString, expectedString) ->
            Assert.assertEquals(context.getMangaPublishStatus(inputString), expectedString)
        }
    }

    @Test
    fun getAnimeRankingWithRankingReturnsValidString() {
        val inputToExpectedMap = mapOf(
            AnimeRankingType.all to "All",
            AnimeRankingType.airing to "Airing",
            AnimeRankingType.upcoming to "Upcoming",
            AnimeRankingType.tv to "TV",
            AnimeRankingType.ova to "OVA",
            AnimeRankingType.movie to "Movie",
            AnimeRankingType.special to "Specials",
            AnimeRankingType.bypopularity to "By popularity",
            AnimeRankingType.favorite to "In your list",
        )
        inputToExpectedMap.forEach { (input, expectedString) ->
            Assert.assertEquals(input.getFormattedString(context), expectedString)
        }
    }

    @Test
    fun getFormattedStringOfAnimeStatusWithValidStatusReturnsValidString() {
        val inputToExpectedMap = mapOf(
            AnimeStatus.all to "All",
            AnimeStatus.watching to "Watching",
            AnimeStatus.plan_to_watch to "Planned",
            AnimeStatus.completed to "Completed",
            AnimeStatus.on_hold to "On hold",
            AnimeStatus.dropped to "Dropped"
        )
        inputToExpectedMap.forEach { (input, expectedString) ->
            Assert.assertEquals(input.getFormattedString(context), expectedString)
        }
    }

    @Test
    fun getFormattedStringOfMangaStatusWithValidStatusReturnsValidString() {
        val inputToExpectedMap = mapOf(
            MangaStatus.all to "All",
            MangaStatus.reading to "Reading",
            MangaStatus.plan_to_read to "Planned",
            MangaStatus.completed to "Completed",
            MangaStatus.on_hold to "On hold",
            MangaStatus.dropped to "Dropped"
        )
        inputToExpectedMap.forEach { (input, expectedString) ->
            Assert.assertEquals(input.getFormattedString(context), expectedString)
        }
    }

    @Test
    fun getFormattedStringOfUserAnimeSortTypeWithValidTypeReturnsValidString() {
        val inputToExpectedMap = mapOf(
            MangaStatus.all to "All",
            MangaStatus.reading to "Reading",
            MangaStatus.plan_to_read to "Planned",
            MangaStatus.completed to "Completed",
            MangaStatus.on_hold to "On hold",
            MangaStatus.dropped to "Dropped"
        )
        inputToExpectedMap.forEach { (input, expectedString) ->
            Assert.assertEquals(input.getFormattedString(context), expectedString)
        }
    }

    @Test
    fun getFormattedStringOfMangaRankingWithValidRankingReturnsValidString() {
        val inputToExpectedMap = mapOf(
            MangaRankingType.all to "All",
            MangaRankingType.manga to "Manga",
            MangaRankingType.oneshots to "One-shots",
            MangaRankingType.doujin to "Doujins",
            MangaRankingType.lightnovels to "Light novels",
            MangaRankingType.novels to "Novels",
            MangaRankingType.manhwa to "Manhwa",
            MangaRankingType.manhua to "Manhua",
            MangaRankingType.bypopularity to "By popularity",
            MangaRankingType.favorite to "In your list",
        )
        inputToExpectedMap.forEach { (input, expectedString) ->
            Assert.assertEquals(input.getFormattedString(context), expectedString)
        }
    }

    @Test
    fun getAnimeRatingWithRatingReturnsValidString() {
        val inputToExpectedMap = mapOf(
            null to "N/A",
            "g" to "G - All ages",
            "pg" to "PG",
            "pg_13" to "PG 13",
            "r" to "R - 17+",
            "r+" to "R+",
            "rx" to "Rx - Hentai",
            "invalid_rating" to "N/A",
        )
        inputToExpectedMap.forEach { (inputString, expectedString) ->
            Assert.assertEquals(context.getAnimeRating(inputString), expectedString)
        }
    }

    @Test
    fun getFormattedArrayOfUserAnimeSortTypeHasSameSizeAsEnumValues() {
        Assert.assertEquals(
            UserAnimeSortType.getFormattedArray(context).size,
            UserAnimeSortType.values().size
        )
    }

    @Test
    fun getFormattedArrayOfUserMangaSortTypeHasSameSizeAsEnumValues() {
        Assert.assertEquals(
            UserMangaSortType.getFormattedArray(context).size,
            UserMangaSortType.values().size
        )
    }
}
