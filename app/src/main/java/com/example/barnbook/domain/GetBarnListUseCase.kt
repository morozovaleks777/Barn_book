package com.example.barnbook.domain

class GetBarnListUseCase(private  val barnListRepository: BarnListRepository) {

    fun getBarnList():List<BarnItem>{
      return  barnListRepository.getBarnList()
    }


}