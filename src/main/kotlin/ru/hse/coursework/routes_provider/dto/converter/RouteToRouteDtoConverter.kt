package ru.hse.coursework.routes_provider.dto.converter

import ru.hse.coursework.routes_provider.dto.RouteDto
import ru.hse.coursework.routes_provider.model.Route

class RouteToRouteDtoConverter : Converter<Route, , RouteDto> {
    override fun convert(source: Route): RouteDto {
        return RouteDto(
            id = source.id,
            name = source.name,
            description = source.description,
            length = source.length,
            duration = source.duration,
            preview = source.preview,
            categories = source.categories.map { it.name }
        )
    }
}