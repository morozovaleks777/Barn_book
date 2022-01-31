package com.example.barnbook.domain



class GetCurrentTimeCase(private  val barnListRepository: BarnListRepository) {
 suspend fun getCurrentTime():String {
    return barnListRepository.getCurrentTime()
    }
}