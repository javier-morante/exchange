package cat.copernic.abp_project_3.application.data.exceptions.services

/**
 * Exception thrown when an error occurs during category management operations.
 *
 * @param message The detail message.
 */
class CategoryManagementException(message: String) : Exception(message)