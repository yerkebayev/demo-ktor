import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Page<T>(
    val data: List<T>,
    @SerialName("current_page") val currentPage: Int,
    @SerialName("total_items") val totalItems: Int,
    @SerialName("total_pages") val totalPages: Int
) {
    companion object {
        inline fun <reified T> fromJson(json: String): Page<T> {
            return Json.decodeFromString(json)
        }

        inline fun <reified T> toJson(page: Page<T>): String {
            return Json.encodeToString(page)
        }
    }

}
