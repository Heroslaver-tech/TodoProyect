package com.example.project.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.example.project.model.ToDo
import com.example.project.repository.ToDoRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import okhttp3.Dispatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*


import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock

import org.mockito.Mockito.any
import org.mockito.Mockito.anyObject
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito



class ToDoViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private  lateinit var toDoViewModel: ToDoViewModel
    private lateinit var repository: ToDoRepository
    @Before
    fun setUp() {
        repository = Mockito.mock(ToDoRepository::class.java)
        toDoViewModel = ToDoViewModel(repository)
    }

    @Test
    fun `test getTodos method`(): Unit = runBlocking {
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        //when
        val mockTodos = mutableListOf(
            ToDo("1", "title1", true),
            ToDo("2", "title2", true)
        )
        Mockito.`when`(repository.getToDoList()).thenReturn(mockTodos)
        //then
        toDoViewModel.getToDoList()

        assertEquals(toDoViewModel.toDoList.value, mockTodos)
    }

    @Test
    fun `test addTodo method`(): Unit = runBlocking {
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        //when
        val mockTodo = ToDo("1", "title1", true)
        val mockTodos = mutableListOf(
            ToDo("1", "title1", true),
            ToDo("2", "title2", true)
        )
        Mockito.`when`(repository.addToDo(mockTodo)).thenReturn(true)
        //then
        toDoViewModel.addToDo(mockTodo)

        assertEquals(toDoViewModel.toDoList.value, mockTodos)
    }

    @Test
    fun `test updateTodoStatus method`(): Unit = runBlocking {
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        //when
        val mockTodo = ToDo("1", "title1", true)
        val mockTodos = mutableListOf(
            ToDo("1", "title1", true),
            ToDo("2", "title2", true)
        )
        Mockito.`when`(repository.updateToDoStatus("1", false)).thenReturn(null)
        //then
        toDoViewModel.updateToDoStatus("1", false)

        assertEquals(toDoViewModel.toDoList.value, mockTodos)
    }





}

