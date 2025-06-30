package com.example.userInfo

import com.example.userInfo.data.model.UserData
import com.example.userInfo.domain.usecase.AddUserInfoUseCase
import com.example.userInfo.domain.usecase.GetUserInfoUseCase
import com.example.userInfo.domain.usecase.RefreshUserInfoUseCase
import com.example.userInfo.domain.usecase.RemoveUserInfoUseCase
import com.example.userInfo.presentation.viewmodel.UserInfoState
import com.example.userInfo.presentation.viewmodel.UserInfoViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserInfoViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @RelaxedMockK
    lateinit var mockGetUserInfoUseCase: GetUserInfoUseCase

    @RelaxedMockK
    lateinit var mockRefreshUserInfoUseCase: RefreshUserInfoUseCase

    @RelaxedMockK
    lateinit var mockAddUserInfoUseCase: AddUserInfoUseCase

    @RelaxedMockK
    lateinit var mockRemoveUserInfoUseCase: RemoveUserInfoUseCase

    private var viewModel: UserInfoViewModel? = null

    private val userData = listOf(
        UserData(id = 1, name = "Joe", email = "john@example.com"),
        UserData(id = 2, name = "Jane", email = "jane@example.com"),
        UserData(id = 2, name = "Janice", email = "Janice@example.com")
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = UserInfoViewModel(
            mockGetUserInfoUseCase,
            mockRefreshUserInfoUseCase,
            mockAddUserInfoUseCase,
            mockRemoveUserInfoUseCase
        )
    }

    @Test
    fun `when the users are loaded, the starting state is set and the Success state is set when users are returned`() = runTest {

        coEvery { mockGetUserInfoUseCase.invoke() } returns userData
        assertTrue(viewModel?.uiState?.value is UserInfoState.StartingState)

        viewModel?.fetchUsers()

        advanceUntilIdle()
        assertTrue(viewModel?.uiState?.value is UserInfoState.Success)

        (viewModel?.uiState?.value as UserInfoState.Success).let {
            assert(it.content.size == 3)
            assert(it.content[2].name == "Janice")
        }
    }

    @Test
    fun `loadUsers emits Error state when use case returns empty list`() = runTest {
        coEvery { mockGetUserInfoUseCase.invoke() } returns emptyList()

        viewModel?.fetchUsers()
        advanceUntilIdle()

        // Check if no content error is produced, no error thrown.
        assertTrue(viewModel?.uiState?.value is UserInfoState.Error)
        assertTrue((viewModel?.uiState?.value as UserInfoState.Error).errorMessage == "No content to display")
    }

    @Test
    fun `loadUsers emits Error state when use case throws exception`() = runTest {
        coEvery { mockGetUserInfoUseCase.invoke() } throws Exception("data fetch error")

        viewModel?.fetchUsers()
        advanceUntilIdle()

        // error thrown, exception catch block.
        assert(viewModel?.uiState?.value is UserInfoState.Error)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}