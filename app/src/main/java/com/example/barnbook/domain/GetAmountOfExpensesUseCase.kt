package com.example.barnbook.domain

class GetAmountOfExpensesUseCase(private  val barnListRepository: BarnListRepository) {

   fun getAmount(list:List<BarnItem>):Float {

      return  barnListRepository.getAmount(list)
    }
}