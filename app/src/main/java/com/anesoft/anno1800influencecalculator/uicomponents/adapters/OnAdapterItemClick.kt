package com.anesoft.anno1800influencecalculator.uicomponents.adapters

import com.anesoft.anno1800influencecalculator.repository.local.entity.Player

interface OnAdapterItemClick {

    fun onClick(player: Player)

}