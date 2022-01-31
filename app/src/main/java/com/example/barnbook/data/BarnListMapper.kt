package com.example.barnbook.data

import com.example.barnbook.domain.BarnItem

class BarnListMapper {
    fun mapEntityToDBModel(barnItem: BarnItem):BarnItemDBModel{
return BarnItemDBModel(
    itemId = barnItem.itemId,
    name = barnItem.name,
    count = barnItem.count,
    price = barnItem.price,
    enabled = barnItem.enabled
)
    }

    fun mapDBModelToEntity(barnItemDBModel: BarnItemDBModel):BarnItem{
        return BarnItem(
            itemId = barnItemDBModel.itemId,
            name = barnItemDBModel.name,
            count = barnItemDBModel.count,
            price = barnItemDBModel.price,
            enabled = barnItemDBModel.enabled
        )
    }

    fun mapListDBModelToListEntity(list:List<BarnItemDBModel>)=
        list.map {
            mapDBModelToEntity(it)
        }
    }
