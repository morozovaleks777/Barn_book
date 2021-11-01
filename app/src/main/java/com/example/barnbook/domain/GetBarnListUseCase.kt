package com.example.barnbook.domain

import androidx.lifecycle.LiveData

class GetBarnListUseCase(private  val barnListRepository: BarnListRepository) {

    fun getBarnList():LiveData<List<BarnItem>>{
      return  barnListRepository.getBarnList()
    }


}