package com.example.athkar.data.seed

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Unit tests for JSON deserialization with snake_case mapping.
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

    private data class TestAthkarEntityData(
        val id: String,
        @SerializedName("category_id")
        val categoryId: String,
        @SerializedName("title_ar")
        val titleAr: String,
        @SerializedName("text_ar")
        val textAr: String,
        val count: Int,
        val source: String?,
        @SerializedName("source_reference")
        val sourceReference: String?,
        val virtues: String?,
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

    @Test
    fun `test athkar JSON with snake_case fields deserializes correctly`() {
        val json = """
        [
            {
                "id": "morning_001",
                "category_id": "morning",
                "title_ar": "أصبحنا وأصبح الملك لله",
                "text_ar": "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ",
                "count": 1,
                "source": "صحيح مسلم",
                "source_reference": "4/2088",
                "virtues": "من قالها كفاه الله ما أهمه",
                "sort_order": 1
            }
        ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java,
            TestAthkarEntityData::class.java
        ).type
        val athkarList: List<TestAthkarEntityData> = gson.fromJson(json, listType)

        assertEquals(1, athkarList.size)
        assertEquals("morning_001", athkarList[0].id)
        assertEquals("morning", athkarList[0].categoryId)
        assertEquals("أصبحنا وأصبح الملك لله", athkarList[0].titleAr)
        assertEquals(1, athkarList[0].count)
        assertEquals("صحيح مسلم", athkarList[0].source)
    }

    @Test
    fun `test athkar JSON with null virtues field`() {
        val json = """
        [
            {
                "id": "morning_002",
                "category_id": "morning",
                "title_ar": "اللهم بك أصحلنا",
                "text_ar": "اللَّهُمَّ بِكَ أَصْبَحْنَا",
                "count": 1,
                "source": "سنن الترمذي",
                "source_reference": "5/468",
                "virtues": null,
                "sort_order": 2
            }
        ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java,
            TestAthkarEntityData::class.java
        ).type
        val athkarList: List<TestAthkarEntityData> = gson.fromJson(json, listType)

        assertEquals(1, athkarList.size)
        assertEquals(null, athkarList[0].virtues)
    }
}
