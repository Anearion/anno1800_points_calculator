package com.anesoft.anno1800influencecalculator.uicomponents

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.anesoft.anno1800influencecalculator.repository.local.entity.Player

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectPlayersBottomSheetv2(players: List<Player>) {

    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            LazyColumn() {
                items(
                    items = players
                ) { player ->
                    ListItem( text = {
                        Text(player.name!!)
                    })
                }
            }
        }) {

    }
}

@Preview
@Composable
fun PreviewBottomSheet(){
    SelectPlayersBottomSheetv2(players = emptyList())
}