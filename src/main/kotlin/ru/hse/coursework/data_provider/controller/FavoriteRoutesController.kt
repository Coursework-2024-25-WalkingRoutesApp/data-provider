package ru.hse.coursework.data_provider.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.hse.coursework.data_provider.api.FavoriteRoutesControllerApi
import ru.hse.coursework.data_provider.dto.RouteCartDto
import ru.hse.coursework.data_provider.dto.UserCoordinateDto
import ru.hse.coursework.data_provider.service.FavoriteRoutesService
import java.util.*

@RestController
@RequestMapping(FAVORITE_BASE_PATH_URL)
class FavoriteRoutesController(
    private val favoriteRoutesService: FavoriteRoutesService
) : FavoriteRoutesControllerApi {

    @GetMapping(GET_FAVOURITES_URL)
    override fun getFavourites(@RequestHeader(USER_ID_HEADER) userId: UUID, @RequestParam latitude: Double, @RequestParam longitude: Double): List<RouteCartDto> =
        favoriteRoutesService.getUserFavouriteRoutes(userId, UserCoordinateDto(latitude, longitude))


    @PostMapping(ADD_FAVOURITE_URL)
    override fun addFavourite(@RequestHeader(USER_ID_HEADER) userId: UUID, @RequestParam routeId: UUID): ResponseEntity<String> =
        favoriteRoutesService.addFavourite(userId, routeId)

    @DeleteMapping(DELETE_FAVOURITE_URL)
    override fun deleteFavourite(@RequestHeader(USER_ID_HEADER) userId: UUID, @RequestParam routeId: UUID): ResponseEntity<String> =
        favoriteRoutesService.deleteFavourite(userId, routeId)
}
