package com.example.barnbook.domain

class AddBarnItemUseCase (private  val barnListRepository: BarnListRepository){

  suspend  fun addBarnItem(barnItem: BarnItem){
      barnListRepository.
      addBarnItem(barnItem )
    }
}