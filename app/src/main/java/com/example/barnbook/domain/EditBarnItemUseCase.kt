package com.example.barnbook.domain

class EditBarnItemUseCase(private  val barnListRepository: BarnListRepository) {

    fun editBarnItem(barnItem: BarnItem){
barnListRepository.editBarnItem(barnItem)
    }
}