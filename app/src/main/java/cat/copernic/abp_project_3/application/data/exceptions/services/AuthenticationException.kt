package cat.copernic.abp_project_3.application.data.exceptions.services

/**
 * Exception thrown when authentication fails.
 *
 * @param message The detail message.
 */
class AuthenticationException(message: String) : Exception(message)