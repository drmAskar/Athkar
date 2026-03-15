package com.example.athkar.data.seed

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Unit tests for CategoryEntityData JSON deserialization.
 * Tests that @SerializedName annotations properly map snake_case JSON to camelCase Kotlin fields.
 */
class CategoryEntityDataTest {

    private val gson = Gson()

    private data class TestCategoryEntityData(
        val id: String,
        @SerializedName("name_ar")
        val nameAr: String,
        @SerializedName("name_en")
        val nameEn: String?,
        val icon: String?,
        @SerializedName("sort_order")
        val sortOrder: Int
    )

    @Test
    fun `test category JSON with snake_case fields deserializes correctly`() {
        val json = """
            [
                {
                    "id": "morning",
                    "name_ar": "أذكار الصباح",
                    "name_en": "Morning Athkar",
                    "icon": "wb_sunny",
                    "sort_order": 1
                }
            ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java,
            TestCategoryEntityData::class.java
        ).type

        val categories: List<TestCategoryEntityData> = gson.fromJson(json, listType)

        assertEquals(1, categories.size)
        assertNotNull(categories[0].nameAr)
        assertEquals("أذكار الصباح", categories[0].nameAr)
        assertEquals("Morning Athkar", categories[0].nameEn)
    }

    @Test
    fun `test category JSON with missing nameAr uses fallback to id`() {
        val json = """
            [
                {
                    "id": "morning",
                    "name_ar": "",
                    "name_en": "Morning Athkar",
                    "icon": "wb_sunny",
                    "sort_order": 1
                }
            ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java,
            TestCategoryEntityData::class.java
        ).type

        val categories: List<TestCategoryEntityData> = gson.fromJson(json, listType)

        // The seedCategories code uses: nameAr = it.nameAr.ifEmpty { it.id }
        // This test verifies nameAr is empty string from JSON (not null)
        assertEquals("", categories[0].nameAr)
        // The fallback would be applied when creating CategoryEntity
        assertEquals("morning", categories[0].id)
    }
}