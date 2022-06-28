package com.sam43.mindvalleychannels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okio.buffer
import okio.source
import org.junit.Rule
import java.lang.reflect.Type

@ExperimentalCoroutinesApi
abstract class BaseApiTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    fun <T> parseJsonFileToObject(fileName: String, type: Type): T? {
        val json = getJsonStringFromFile(fileName)
        return Gson().fromJson(json, type)
    }

    fun getJsonStringFromFile(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = inputStream?.source()?.buffer()
        return source!!.readString(Charsets.UTF_8)
    }
}