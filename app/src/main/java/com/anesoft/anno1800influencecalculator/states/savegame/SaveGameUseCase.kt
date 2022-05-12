package com.anesoft.anno1800influencecalculator.states.savegame

class SaveGameUseCase(val step: SaveGameStep, var value: Int) {

    enum class SaveGameStep{
        THREE_POINTS,
        FIVE_POINTS,
        EIGHT_POINTS,
        EXPEDITION,
        GOLD,
        OBJ_ONE,
        OBJ_TWO,
        OBJ_THREE,
        OBJ_FOUR,
        OBJ_FIVE,
        FIREWORK,
        END
    }

    fun nextStep() : SaveGameUseCase{
        return when(step){
            SaveGameStep.THREE_POINTS -> SaveGameUseCase(SaveGameStep.FIVE_POINTS, 0)
            SaveGameStep.FIVE_POINTS -> SaveGameUseCase(SaveGameStep.EIGHT_POINTS, 0)
            SaveGameStep.EIGHT_POINTS -> SaveGameUseCase(SaveGameStep.EXPEDITION, 0)
            SaveGameStep.EXPEDITION -> SaveGameUseCase(SaveGameStep.GOLD, 0)
            SaveGameStep.GOLD -> SaveGameUseCase(SaveGameStep.OBJ_ONE, 0)
            SaveGameStep.OBJ_ONE -> SaveGameUseCase(SaveGameStep.OBJ_TWO, 0)
            SaveGameStep.OBJ_TWO -> SaveGameUseCase(SaveGameStep.OBJ_THREE, 0)
            SaveGameStep.OBJ_THREE -> SaveGameUseCase(SaveGameStep.OBJ_FOUR, 0)
            SaveGameStep.OBJ_FOUR -> SaveGameUseCase(SaveGameStep.OBJ_FIVE, 0)
            SaveGameStep.OBJ_FIVE -> SaveGameUseCase(SaveGameStep.FIREWORK, 0)
            SaveGameStep.FIREWORK -> SaveGameUseCase(SaveGameStep.END, 0)
            SaveGameStep.END -> throw IllegalArgumentException("END state does not have a next step.")
        }
    }

    fun previousStep() : SaveGameUseCase{
        return when(step){
            SaveGameStep.THREE_POINTS -> throw IllegalArgumentException("THREE POINT state does not have a previous step.")
            SaveGameStep.FIVE_POINTS -> SaveGameUseCase(SaveGameStep.THREE_POINTS, 0)
            SaveGameStep.EIGHT_POINTS -> SaveGameUseCase(SaveGameStep.FIVE_POINTS, 0)
            SaveGameStep.EXPEDITION -> SaveGameUseCase(SaveGameStep.EIGHT_POINTS, 0)
            SaveGameStep.GOLD -> SaveGameUseCase(SaveGameStep.EXPEDITION, 0)
            SaveGameStep.OBJ_ONE -> SaveGameUseCase(SaveGameStep.GOLD, 0)
            SaveGameStep.OBJ_TWO -> SaveGameUseCase(SaveGameStep.OBJ_ONE, 0)
            SaveGameStep.OBJ_THREE -> SaveGameUseCase(SaveGameStep.OBJ_TWO, 0)
            SaveGameStep.OBJ_FOUR -> SaveGameUseCase(SaveGameStep.OBJ_THREE, 0)
            SaveGameStep.OBJ_FIVE -> SaveGameUseCase(SaveGameStep.OBJ_FOUR, 0)
            SaveGameStep.FIREWORK -> SaveGameUseCase(SaveGameStep.FIVE_POINTS, 0)
            SaveGameStep.END -> SaveGameUseCase(SaveGameStep.FIREWORK, 0)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SaveGameUseCase

        if (step != other.step) return false

        return true
    }

    override fun hashCode(): Int {
        return step.hashCode()
    }


}