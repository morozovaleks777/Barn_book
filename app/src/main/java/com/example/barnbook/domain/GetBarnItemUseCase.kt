package com.example.barnbook.domain

class GetBarnItemUseCase(private  val barnListRepository: BarnListRepository) {
    fun getBarnItem(itemId: Int):BarnItem{
 return barnListRepository.getBarnItem(itemId)
    }
}