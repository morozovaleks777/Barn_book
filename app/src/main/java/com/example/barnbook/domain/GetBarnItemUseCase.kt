package com.example.barnbook.domain

class GetBarnItemUseCase(private  val barnListRepository: BarnListRepository) {
   suspend fun getBarnItem(itemId: Int):BarnItem{
 return barnListRepository.getBarnItem(itemId)
    }
}