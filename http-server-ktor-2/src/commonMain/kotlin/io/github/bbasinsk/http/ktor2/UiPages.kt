package io.github.bbasinsk.http.ktor2

import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.stoplight(path: String = "stoplight", specPath: String) =
    get(path) {
        call.respondText(
            """
            <!doctype html>
            <html lang="en">
              <head>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <title>Elements in HTML</title>
                <!-- Embed elements Elements via Web Component -->
                <script src="https://unpkg.com/@stoplight/elements/web-components.min.js"></script>
                <link rel="stylesheet" href="https://unpkg.com/@stoplight/elements/styles.min.css">
              </head>
              <body>
                <elements-api
                  apiDescriptionUrl="$specPath"
                  router="hash"
                  layout="sidebar"
                />
              </body>
            </html>
            """.trimIndent(),
            contentType = ContentType.Text.Html
        )
    }

fun Route.swagger(path: String = "swagger", specPath: String) =
    get(path) {
        call.respondText(
            """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="utf-8" />
              <meta name="viewport" content="width=device-width, initial-scale=1" />
              <meta name="description" content="SwaggerUI" />
              <title>SwaggerUI</title>
              <link rel="stylesheet" href="https://unpkg.com/swagger-ui-dist@5.11.0/swagger-ui.css" />
            </head>
            <body>
            <div id="swagger-ui"></div>
            <script src="https://unpkg.com/swagger-ui-dist@5.11.0/swagger-ui-bundle.js" crossorigin></script>
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
            """.trimIndent(),
            contentType = ContentType.Text.Html
        )
    }

fun Route.redoc(path: String = "/redoc", specPath: String) =
    get(path) {
        call.respondText(
            """
            <!DOCTYPE html>
            <html>
            <head>
                <title>ReDoc</title>
                <!-- needed for adaptive design -->
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
            """.trimIndent(),
            contentType = ContentType.Text.Html
        )
    }
