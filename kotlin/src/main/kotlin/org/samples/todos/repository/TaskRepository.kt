package org.samples.todos.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.samples.todos.model.TaskGroup
import java.io.File
import java.nio.file.Paths
import java.io.IOException

class TaskRepository(val taskFileName:String, val mapper:ObjectMapper){

    fun load():MutableList<TaskGroup>{
        try{
            return mapper.readValue(Paths.get(taskFileName).toFile())
        } catch (e:IOException){
            println("Failed to load tasks from file: ${taskFileName}")
            e.printStackTrace()
            return mutableListOf()
        }
    }

    fun save(taskGroups:MutableList<TaskGroup>){
        try{
            mapper.writeValue(File(taskFileName), taskGroups)
        } catch (e:IOException){
            println("Failed to save tasks to file: ${taskFileName}")
            e.printStackTrace()
        }
    }
}