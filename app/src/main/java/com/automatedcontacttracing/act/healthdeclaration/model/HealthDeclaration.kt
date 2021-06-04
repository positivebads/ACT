package com.automatedcontacttracing.act.healthdeclaration.model

data class HealthDeclaration(
    var withFever: Boolean = false,
    var withWoresningCough: Boolean = false,
    var withSoreThroat: Boolean = false,
    var withDifficultyOfBreathing: Boolean = false,
    var withLossOfSmell: Boolean = false,
    var withRunnyNose: Boolean = false,
    var withHeadache: Boolean = false,
    var withMusclePain: Boolean = false,
    var withDiarrhea: Boolean = false,
    var withCloseContact: Boolean = false,
    var affirmative: Boolean = false
)