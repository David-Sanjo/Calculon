package com.example.calculon.model

data class DesgloseSS(

    val baseCotizacion: Double,

    val porcentajeContingenciasComunes: Double,
    val importeContingenciasComunes: Double,

    val porcentajeDesempleo: Double,
    val importeDesempleo: Double,

    val porcentajeFormacionProfesional: Double,
    val importeFormacionProfesional: Double,

    val porcentajeMEI: Double,
    val importeMEI: Double
) {
    val porcentajeTotal: Double
        get() = porcentajeContingenciasComunes +
                porcentajeDesempleo +
                porcentajeFormacionProfesional +
                porcentajeMEI

    val totalCotizacion: Double
        get() = importeContingenciasComunes +
                importeDesempleo +
                importeFormacionProfesional +
                importeMEI
}