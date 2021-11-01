package com.example.barnbook.domain

class DeleteBarnItemUseCase(private  val barnListRepository: BarnListRepository) {

    fun deleteBarnItem(barnItem: BarnItem){
barnListRepository.deleteBarnItem(barnItem)
    }
}