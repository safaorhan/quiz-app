package com.hoopow.apps.quiz.quiz

import app.cash.turbine.test
import com.hoopow.apps.quiz.quiz.category.Category
import com.hoopow.apps.quiz.quiz.category.CategoryRepository
import com.hoopow.apps.quiz.quiz.config.Environment.GRAND_LAGOON
import com.hoopow.apps.quiz.quiz.config.Ship.THE_LITTLE_SAILBOAT
import com.hoopow.apps.test.CoroutinesTestRule
import com.hoopow.apps.test.givenRespondsSuccess
import com.hoopow.apps.test.testApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class QuizConfigViewModelTest {
    @get:Rule
    val server = MockWebServer()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `grand lagoon and the little sailboat should be selected initially`() = runBlocking {
        server.givenRespondsSuccess("fetch-categories-success.json")
        val viewModel = testViewModel(server)

        viewModel.config.test {
            val initialConfig = expectItem()

            assertThat(initialConfig.categoryId).isEmpty()
            assertThat(initialConfig.environment).isEqualTo(GRAND_LAGOON)
            assertThat(initialConfig.ship).isEqualTo(THE_LITTLE_SAILBOAT)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given server returns success, categories should be loaded`() = runBlocking {
        server.givenRespondsSuccess("fetch-categories-success.json")

        val viewModel = testViewModel(server)

        viewModel.categories.test {
            expectItem() // initially empty
            val categories = expectItem()

            assertThat(categories).hasSize(3)

            assertThat(categories[0].id).isEqualTo("1615793689969-1")
            assertThat(categories[0].label).isEqualTo("Les Prophètes")

            assertThat(categories[1].id).isEqualTo("1615793689969-2")
            assertThat(categories[1].label).isEqualTo("Pays musulmans")
            assertThat(categories[2].id).isEqualTo("1615793689969-3")
            assertThat(categories[2].label).isEqualTo("Le Coran")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given server returns success, first category should be selected`() = runBlocking {
        server.givenRespondsSuccess("fetch-categories-success.json")

        val viewModel = testViewModel(server)

        viewModel.config.test {
            expectItem() // initialConfig
            val config = expectItem()

            assertThat(config.categoryId).isEqualTo("1615793689969-1")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when user clicks a category item, that should be selected`() = runBlocking {
        server.givenRespondsSuccess("fetch-categories-success.json")

        val viewModel = testViewModel(server)

        var categories: List<Category> = emptyList()
        viewModel.categories.test {
            expectItem() // initially empty
            categories = expectItem()
            cancelAndIgnoreRemainingEvents()
        }


        viewModel.config.test {
            expectItem() // initially categoryId = 1615793689969-1 (auto select)

            viewModel.onCategoryClick(categories[2])
            assertThat(expectItem().categoryId).isEqualTo("1615793689969-3")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when user clicks a category item, it should navigate to next screen`() = runBlocking {
        server.givenRespondsSuccess("fetch-categories-success.json")

        val viewModel = testViewModel(server)

        val category = Category("", "", 0, 0)

        viewModel.navigation.navigateTo.test {
            val initial = expectItem()
            assertThat(initial).isEqualTo(null)

            viewModel.onCategoryClick(category)

            val destination = expectItem()
            assertThat(destination!!.actionId).isEqualTo(R.id.go_to_next_screen)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when user clicks random button, it should navigate to next screen`() = runBlocking {
        server.givenRespondsSuccess("fetch-categories-success.json")

        val viewModel = testViewModel(server)

        viewModel.categories.test {
            expectItem() // initially empty
            expectItem() // categories are fetched

            cancelAndIgnoreRemainingEvents()
        }

        viewModel.navigation.navigateTo.test {
            val initial = expectItem()
            assertThat(initial).isEqualTo(null)

            viewModel.onRandomButtonClick()

            val destination = expectItem()
            assertThat(destination!!.actionId).isEqualTo(R.id.go_to_next_screen)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when user clicks back button, it should navigate back`() = runBlocking {
        server.givenRespondsSuccess("fetch-categories-success.json")

        val viewModel = testViewModel(server)

        // -- If you remove these v , some coroutines will leak to other next test. --
        viewModel.categories.test {
            cancelAndIgnoreRemainingEvents()
        }
        viewModel.config.test {
            cancelAndIgnoreRemainingEvents()
        }
        // -- If you remove these ^, some coroutines will leak to other next test. --

        viewModel.navigation.navigateBack.test {
            val initial = expectItem()
            assertThat(initial).isFalse

            viewModel.onBackButtonClick()

            val navigateBack = expectItem()
            assertThat(navigateBack).isTrue

            cancelAndIgnoreRemainingEvents()
        }
    }

    // TODO: add more tests to cover error and loading popups

    companion object {
        private fun testViewModel(server: MockWebServer) = QuizConfigViewModel(
            repository = CategoryRepository(
                api = testApi(server)
            )
        )
    }
}