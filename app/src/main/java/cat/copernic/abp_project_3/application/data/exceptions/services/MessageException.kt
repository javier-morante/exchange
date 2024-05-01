package cat.copernic.abp_project_3.application.data.exceptions.services

/**
 * Exception thrown when an error related to messages occurs.
 *
 * @param message The detail message.
 */
class MessageException(message: String) : Exception(message)