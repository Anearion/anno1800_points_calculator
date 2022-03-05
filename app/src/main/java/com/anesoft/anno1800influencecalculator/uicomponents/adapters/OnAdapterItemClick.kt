package com.anesoft.anno1800influencecalculator.uicomponents.adapters

import com.anesoft.anno1800influencecalculator.repository.local.entity.Player

interface OnAdapterItemClick<T> {

    fun onClick(player: T)

}