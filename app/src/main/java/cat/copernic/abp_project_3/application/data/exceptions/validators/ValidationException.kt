package cat.copernic.abp_project_3.application.data.exceptions.validators

/**
 * Exception thrown when a validation error occurs.
 *
 * @param message The detail message.
 */
class ValidationException(message: String) : Exception(message)