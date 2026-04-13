package io.github.bbasinsk.http.openapi

import io.github.bbasinsk.http.Http

/**
 * Serializes the generated OpenAPI spec to a UTF-8 JSON byte array, ready to return from
 * an endpoint handler.
 *
 * Define the spec endpoint inline as any other [Http]:
 * ```
 * val openApiFile = Http.get { Root / "openapi.json" }
 *     .output { status(Ok) { bytes(ContentType.Json) } }
 *
 * handle(openApiFile) { _ -> Response.success(renderOpenapiJson(apis(), info, servers)) }
 * ```
 *
 * `bytes(ContentType.Json)` lets the handler return the pre-serialized JSON without
 * round-tripping through the schema layer.
 */
fun renderOpenapiJson(
    apis: List<Http<*, *, *, *, *>>,
    info: Info,
    servers: List<Server> = emptyList()
): ByteArray =
    OpenApiJson.encodeToString(
        OpenAPI.serializer(),
        apis.toOpenApiSpec(info, servers)
    ).encodeToByteArray()

/** Renders the Swagger UI HTML page pointing at [specPath]. */
fun renderSwaggerHtml(specPath: String = "/openapi.json"): String =
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

/** Renders the ReDoc UI HTML page pointing at [specPath]. */
fun renderRedocHtml(specPath: String = "/openapi.json"): String =
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

/** Renders the Stoplight Elements UI HTML page pointing at [specPath]. */
fun renderStoplightHtml(specPath: String = "/openapi.json"): String =
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
