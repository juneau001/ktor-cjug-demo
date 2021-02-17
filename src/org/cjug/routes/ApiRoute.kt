package org.cjug.routes

import io.ktor.routing.Routing
import io.ktor.routing.route

fun Routing.apiRoute() {
    route("/bookapi/v1") {
        books()
    }
}