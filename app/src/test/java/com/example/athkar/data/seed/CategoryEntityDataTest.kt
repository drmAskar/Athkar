package com.example.athkar.data.seed

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Unit tests for JSON deserialization with snake_case and camelCase mapping.
 * Tests that @SerializedName annotations with alternate values properly map
 * both snake_case JSON (category_id) and camelCase JSON (categoryId) to Kotlin fields.
 */
class CategoryEntityDataTest {

    private val gson = Gson()

    // DTOs matching the repository's internal data classes
    private data class TestCategoryJsonData(
        val id: String,
        @SerializedName(value = "name_ar", alternate = ["nameAr"])
        val nameAr: String?,
        @SerializedName(value = "name_en", alternate = ["nameEn"])
        val nameEn: String?,
        val icon: String?,
        @SerializedName(value = "sort_order", alternate = ["sortOrder"])
        val sortOrder: Int?
    )

    private data class TestAthkarJsonData(
        val id: String,
        @SerializedName(value = "category_id", alternate = ["categoryId"])
        val categoryId: String?,
        @SerializedName(value = "title_ar", alternate = ["titleAr"])
        val titleAr: String?,
        @SerializedName(value = "text_ar", alternate = ["textAr"])
        val textAr: String?,
        val count: Int?,
        val source: String?,
        @SerializedName(value = "source_reference", alternate = ["sourceReference"])
        val sourceReference: String?,
        val virtues: String?,
        @SerializedName(value = "sort_order", alternate = ["sortOrder"])
        val sortOrder: Int?
    )

    private data class TestSurahJsonData(
        val id: String,
        @SerializedName(value = "name_ar", alternate = ["nameAr"])
        val nameAr: String?,
        @SerializedName(value = "name_en", alternate = ["nameEn"])
        val nameEn: String?,
        @SerializedName(value = "surah_number", alternate = ["surahNumber"])
        val surahNumber: Int?,
        @SerializedName(value = "ayat_count", alternate = ["ayatCount"])
        val ayatCount: Int?,
        @SerializedName(value = "revelation_type", alternate = ["revelationType"])
        val revelationType: String?,
        @SerializedName(value = "juz_number", alternate = ["juzNumber"])
        val juzNumber: Int?,
        @SerializedName(value = "text_ar", alternate = ["textAr"])
        val textAr: String?,
        val virtues: String?,
        @SerializedName(value = "source_reference", alternate = ["sourceReference"])
        val sourceReference: String?
    )

    // ========== CATEGORY TESTS ==========

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
            List::class.java, TestCategoryJsonData::class.java
        ).type
        val categories: List<TestCategoryJsonData> = gson.fromJson(json, listType)

        assertEquals(1, categories.size)
        assertEquals("morning", categories[0].id)
        assertEquals("أذكار الصباح", categories[0].nameAr)
        assertEquals("Morning Athkar", categories[0].nameEn)
        assertEquals("wb_sunny", categories[0].icon)
        assertEquals(1, categories[0].sortOrder)
    }

    @Test
    fun `test category JSON with camelCase fields deserializes correctly`() {
        val json = """
            [
                {
                    "id": "evening",
                    "nameAr": "أذكار المساء",
                    "nameEn": "Evening Athkar",
                    "icon": "nights_stay",
                    "sortOrder": 2
                }
            ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java, TestCategoryJsonData::class.java
        ).type
        val categories: List<TestCategoryJsonData> = gson.fromJson(json, listType)

        assertEquals(1, categories.size)
        assertEquals("evening", categories[0].id)
        assertEquals("أذكار المساء", categories[0].nameAr)
        assertEquals("Evening Athkar", categories[0].nameEn)
        assertEquals(2, categories[0].sortOrder)
    }

    @Test
    fun `test category JSON with null fields handles gracefully`() {
        val json = """
            [
                {
                    "id": "test",
                    "name_ar": "",
                    "name_en": null,
                    "icon": null,
                    "sort_order": null
                }
            ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java, TestCategoryJsonData::class.java
        ).type
        val categories: List<TestCategoryJsonData> = gson.fromJson(json, listType)

        assertEquals(1, categories.size)
        assertEquals("", categories[0].nameAr)
        assertNull(categories[0].nameEn)
        assertNull(categories[0].icon)
        assertNull(categories[0].sortOrder)
    }

    // ========== ATHKAR TESTS ==========

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
            List::class.java, TestAthkarJsonData::class.java
        ).type
        val athkarList: List<TestAthkarJsonData> = gson.fromJson(json, listType)

        assertEquals(1, athkarList.size)
        assertEquals("morning_001", athkarList[0].id)
        assertEquals("morning", athkarList[0].categoryId)
        assertEquals("أصبحنا وأصبح الملك لله", athkarList[0].titleAr)
        assertEquals(1, athkarList[0].count)
        assertEquals("صحيح مسلم", athkarList[0].source)
    }

    @Test
    fun `test athkar JSON with camelCase fields deserializes correctly`() {
        // This tests the format used in athkar_sleep.json
        val json = """
            [
                {
                    "id": "sleep_001",
                    "categoryId": "sleep",
                    "titleAr": "أذكار قبل النوم",
                    "textAr": "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا.",
                    "count": 1,
                    "source": "صحيح البخاري",
                    "sourceReference": "11/71",
                    "virtues": null,
                    "sortOrder": 1
                }
            ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java, TestAthkarJsonData::class.java
        ).type
        val athkarList: List<TestAthkarJsonData> = gson.fromJson(json, listType)

        assertEquals(1, athkarList.size)
        assertEquals("sleep_001", athkarList[0].id)
        assertEquals("sleep", athkarList[0].categoryId)
        assertEquals("أذكار قبل النوم", athkarList[0].titleAr)
        assertEquals("بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا.", athkarList[0].textAr)
        assertEquals(1, athkarList[0].count)
        assertEquals("صحيح البخاري", athkarList[0].source)
        assertEquals("11/71", athkarList[0].sourceReference)
        assertNull(athkarList[0].virtues)
        assertEquals(1, athkarList[0].sortOrder)
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
            List::class.java, TestAthkarJsonData::class.java
        ).type
        val athkarList: List<TestAthkarJsonData> = gson.fromJson(json, listType)

        assertEquals(1, athkarList.size)
        assertNull(athkarList[0].virtues)
    }

    @Test
    fun `test athkar JSON with missing count defaults to null`() {
        val json = """
            [
                {
                    "id": "test_001",
                    "category_id": "morning",
                    "title_ar": "Test Title",
                    "text_ar": "Test Text"
                }
            ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java, TestAthkarJsonData::class.java
        ).type
        val athkarList: List<TestAthkarJsonData> = gson.fromJson(json, listType)

        assertEquals(1, athkarList.size)
        assertNull(athkarList[0].count)
        assertNull(athkarList[0].sortOrder)
        // Repository should default to count=1, sortOrder=0 when mapping to entity
    }

    // ========== SURAH TESTS ==========

    @Test
    fun `test surah JSON with snake_case fields deserializes correctly`() {
        val json = """
            [
                {
                    "id": "surah_fatiha",
                    "name_ar": "الفاتحة",
                    "name_en": "Al-Fatiha",
                    "surah_number": 1,
                    "ayat_count": 7,
                    "revelation_type": "مكية",
                    "juz_number": 1,
                    "text_ar": "بِسْمِ اللَّهِ الرَّحْمَـٰنِ الرَّحِيمِ",
                    "virtues": "أعظم سورة",
                    "source_reference": "القرآن الكريم 1:1-7"
                }
            ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java, TestSurahJsonData::class.java
        ).type
        val surahs: List<TestSurahJsonData> = gson.fromJson(json, listType)

        assertEquals(1, surahs.size)
        assertEquals("surah_fatiha", surahs[0].id)
        assertEquals("الفاتحة", surahs[0].nameAr)
        assertEquals("Al-Fatiha", surahs[0].nameEn)
        assertEquals(1, surahs[0].surahNumber)
        assertEquals(7, surahs[0].ayatCount)
        assertEquals("مكية", surahs[0].revelationType)
        assertEquals(1, surahs[0].juzNumber)
    }

    @Test
    fun `test surah JSON with camelCase fields deserializes correctly`() {
        val json = """
            [
                {
                    "id": "surah_ikhlas",
                    "nameAr": "الإخلاص",
                    "nameEn": "Al-Ikhlas",
                    "surahNumber": 112,
                    "ayatCount": 4,
                    "revelationType": "مكية",
                    "juzNumber": 30,
                    "textAr": "قُلْ هُوَ اللَّهُ أَحَدٌ",
                    "virtues": "عدلت ثلث القرآن",
                    "sourceReference": "القرآن الكريم 112:1-4"
                }
            ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java, TestSurahJsonData::class.java
        ).type
        val surahs: List<TestSurahJsonData> = gson.fromJson(json, listType)

        assertEquals(1, surahs.size)
        assertEquals("surah_ikhlas", surahs[0].id)
        assertEquals("الإخلاص", surahs[0].nameAr)
        assertEquals(112, surahs[0].surahNumber)
        assertEquals(4, surahs[0].ayatCount)
    }

    // ========== NULL HANDLING TESTS ==========

    @Test
    fun `test defensive null handling for missing required categoryId`() {
        val json = """
            [
                {
                    "id": "test_id",
                    "title_ar": "Test",
                    "text_ar": "Test Text"
                }
            ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java, TestAthkarJsonData::class.java
        ).type
        val athkarList: List<TestAthkarJsonData> = gson.fromJson(json, listType)

        assertEquals(1, athkarList.size)
        // categoryId is null - repository should filter this out with mapNotNull
        assertNull(athkarList[0].categoryId)
    }

    @Test
    fun `test defensive null handling for missing titleAr`() {
        val json = """
            [
                {
                    "id": "test_id",
                    "category_id": "morning",
                    "text_ar": "Test Text"
                }
            ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java, TestAthkarJsonData::class.java
        ).type
        val athkarList: List<TestAthkarJsonData> = gson.fromJson(json, listType)

        assertEquals(1, athkarList.size)
        // titleAr is null - repository should filter this out with mapNotNull
        assertNull(athkarList[0].titleAr)
    }

    @Test
    fun `test multiple athkar entries with mixed formats`() {
        val json = """
            [
                {
                    "id": "morning_001",
                    "category_id": "morning",
                    "title_ar": "Morning Dua",
                    "text_ar": "Morning text",
                    "count": 1
                },
                {
                    "id": "sleep_001",
                    "categoryId": "sleep",
                    "titleAr": "Sleep Dua",
                    "textAr": "Sleep text",
                    "count": 3
                }
            ]
        """.trimIndent()

        val listType = com.google.gson.reflect.TypeToken.getParameterized(
            List::class.java, TestAthkarJsonData::class.java
        ).type
        val athkarList: List<TestAthkarJsonData> = gson.fromJson(json, listType)

        assertEquals(2, athkarList.size)
        // First entry - snake_case
        assertEquals("morning", athkarList[0].categoryId)
        assertEquals("Morning Dua", athkarList[0].titleAr)
        // Second entry - camelCase
        assertEquals("sleep", athkarList[1].categoryId)
        assertEquals("Sleep Dua", athkarList[1].titleAr)
    }
}
