package cat.copernic.abp_project_3.application.data.enums

/**
 * Enum that have the folders name which is in the firebase storage
 */
enum class StorageReferences(val path: String) {
    ACCOUNTS_IMAGE("accounts_image"),
    CATEGORIES_IMAGE("categories_image"),
    OFFERS_IMAGE("offers_image")
}