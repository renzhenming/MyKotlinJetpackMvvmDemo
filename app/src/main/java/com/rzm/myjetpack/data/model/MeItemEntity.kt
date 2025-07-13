package com.rzm.myjetpack.data.model

import me.hgj.jetpackmvvm.demo.data.model.enums.MeItemType

data class MeItemEntity(
    var itemType: MeItemType,
    var itemPosition: Int,
    var data: Any
)