package cat.copernic.abp_project_3.application.data.enums

/**
 * Enum that have all offer types of status
 */
enum class OfferState(
    val title: String
) {
    AVAILABLE("Available"),
    IN_TRADE("In Trade"),
    DISABLED("Disabled")
}