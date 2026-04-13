package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.Http
import io.github.bbasinsk.http.renderPath

/**
 * Bundles an OpenAPI spec endpoint with its info/servers and the UI pages that point at it.
 *
 * The spec endpoint is defined as any other [Http] — its output must be a [ByteArray] with
 * `application/json` content type, so the handler can return the pre-serialized spec bytes
 * produced by [specJson]:
 *
 * ```
 * val openApiFile = Http.get { Root / "openapi.json" }
 *     .output { status(Ok) { bytes(ContentType.Json) } }
 *
 * val swaggerUi = Http.get { Root / "swagger" }.output { status(Ok) { html() } }
 *
 * val docs = OpenapiDocs(
 *     spec = openApiFile,
 *     info = Info(title = "My API", version = "1.0.0"),
 *     servers = listOf(Server(url = "http://localhost:8080")),
 * )
 *
 * endpoints {
 *     // ... user endpoints ...
 *     handle(openApiFile) { _ -> Response.success(docs.specJson(apis())) }
 *     handle(swaggerUi)   { _ -> Response.success(docs.swaggerHtml()) }
 * }
 * ```
 *
 * The UI pages (Swagger, ReDoc, Stoplight) always point at [specPath], which is derived
 * from [spec] — so you cannot accidentally point a UI at a stale or wrong path.
 */
class OpenapiDocs(
    val spec: Http<*, *, *, ByteArray, *>,
    val info: Info,
    val servers: List<Server> = emptyList(),
) {
    /** Rendered URL path of the spec endpoint, e.g. `/openapi.json`. */
    val specPath: String = spec.params.renderPath()

    /**
     * Serializes the OpenAPI spec for [apis] (combined with this docs' [info] and [servers])
     * to a UTF-8 JSON byte array, ready to return from the spec endpoint's handler.
     */
    fun specJson(apis: List<Http<*, *, *, *, *>>): ByteArray =
        OpenApiJson.encodeToString(
            OpenAPI.serializer(),
            apis.toOpenApiSpec(info, servers)
        ).encodeToByteArray()

    /** Swagger UI HTML page pointing at [specPath]. */
    fun swaggerHtml(): String = swaggerTemplate(specPath)

    /** ReDoc UI HTML page pointing at [specPath]. */
    fun redocHtml(): String = redocTemplate(specPath)

    /** Stoplight Elements UI HTML page pointing at [specPath]. */
    fun stoplightHtml(): String = stoplightTemplate(specPath)
}

private fun swaggerTemplate(specPath: String): String =
    """
    <!DOCTYPE html>
    <html lang="en">
    <head>
      <meta charset="utf-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1" />
      <meta name="description" content="SwaggerUI" />
      <title>SwaggerUI</title>
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swagger-ui-dist/swagger-ui.css" />
    </head>
    <body>
    <div id="swagger-ui"></div>
    <script src="https://cdn.jsdelivr.net/npm/swagger-ui-dist/swagger-ui-bundle.js" crossorigin></script>
    <script>
      window.onload = () => {
        window.ui = SwaggerUIBundle({
          url: '$specPath',
          dom_id: '#swagger-ui',
        });
      };
    </script>
    </body>
    </html>
    """.trimIndent()

private fun redocTemplate(specPath: String): String =
    """
    <!DOCTYPE html>
    <html>
    <head>
        <title>ReDoc</title>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style>
            body {
                margin: 0;
                padding: 0;
            }
        </style>
    </head>
    <body>
        <redoc spec-url='$specPath'></redoc>
        <script src="https://cdn.jsdelivr.net/npm/redoc/bundles/redoc.standalone.js"> </script>
    </body>
    </html>
    """.trimIndent()

private fun stoplightTemplate(specPath: String): String =
    """
    <!doctype html>
    <html lang="en">
      <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Elements in HTML</title>
        <script src="https://cdn.jsdelivr.net/npm/@stoplight/elements/web-components.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@stoplight/elements/styles.min.css">
      </head>
      <body>
        <elements-api
          apiDescriptionUrl="$specPath"
          router="hash"
          layout="sidebar"
        />
      </body>
    </html>
    """.trimIndent()
