package ru.hse.coursework.data_provider.model.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import ru.hse.coursework.data_provider.dto.RouteDto
import ru.hse.coursework.data_provider.model.RouteCategory

@Component
class RouteDtoCategoriesToRouteCategoryConverter : Converter<RouteDto.Categories, RouteCategory> {

    override fun convert(source: RouteDto.Categories): RouteCategory {
        return RouteCategory(
            routeId = source.routeId,
            categoryName = source.categoryName
        )
    }
}
