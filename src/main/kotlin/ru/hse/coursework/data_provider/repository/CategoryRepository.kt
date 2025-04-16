package ru.hse.coursework.data_provider.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.coursework.data_provider.model.Category
import java.util.*

@Repository
interface CategoryRepository : CrudRepository<Category, UUID>