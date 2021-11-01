package com.example.barnbook.domain

class AddBarnItemUseCase (private  val barnListRepository: BarnListRepository){

    fun addBarnItem(barnItem: BarnItem){
      barnListRepository.addBarnItem(barnItem )
    }
}