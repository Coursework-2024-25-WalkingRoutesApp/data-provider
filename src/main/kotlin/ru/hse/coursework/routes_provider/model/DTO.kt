package ru.hse.coursework.routes_provider.model

class RoutePage (
    val route: Route,
    val coordinates: List<RouteCoordinate>,
    val categories: List<RouteCategory>,
    val reviews: List<Review>
)

class NewRoute (
    val route: Route,
    val coordinates: List<RouteCoordinate>,
    val categories: List<RouteCategory>
)

class UserPage (
    val user: User,
    val favorite: List<Favorite>,
    val routeSession: List<RouteSession>
)