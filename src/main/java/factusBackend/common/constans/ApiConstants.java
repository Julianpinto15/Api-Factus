package factusBackend.common.constans;

public class ApiConstants {
    // Endpoints
    public static final String API_BASE_PATH = "/api/v1";
    public static final String AUTH_PATH = "/auth";
    public static final String CLIENTS_PATH = "/clients";
    public static final String INVOICES_PATH = "/invoices";

    // Factus API endpoints
    public static final String FACTUS_AUTH_ENDPOINT = "/oauth/token";
    public static final String FACTUS_CREATE_INVOICE_ENDPOINT = "/v1/bills";
    public static final String FACTUS_VALIDATE_INVOICE_ENDPOINT = "/api/v2/invoices/{id}/validate";
    public static final String FACTUS_COUNTRIES_ENDPOINT = "/v1/countries?name=";
    public static final String FACTUS_MUNICIPALITIES_ENDPOINT = "/api/v2/municipalities";
    public static final String FACTUS_TAXATION_ENDPOINT = "/api/v2/taxation";
    public static final String FACTUS_UNITS_ENDPOINT = "/api/v2/units";
    public static final String FACTUS_NUMBER_RANGES_ENDPOINT = "/api/v2/number-ranges";

    // Response messages
    public static final String SUCCESS_MESSAGE = "Operación exitosa";
    public static final String ERROR_MESSAGE = "Error al procesar la solicitud";
    public static final String NOT_FOUND_MESSAGE = "Recurso no encontrado";
    public static final String UNAUTHORIZED_MESSAGE = "No autorizado";
}