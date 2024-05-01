package cat.copernic.abp_project_3.application.data.exceptions.services

/**
 * Exception thrown when an error related to storage occurs.
 *
 * @param message The detail message.
 */
class StorageException(message: String) : Exception(message)