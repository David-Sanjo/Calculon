package com.example.calculon.model

data class DatosUsuario(
    val salarioBruto: Double,
    val numeroPagas: Int,
    val tipoContrato: TipoContrato,
    val grupoProfesional: GrupoProfesional,
    val edad: Int,
    val estadoCivil: EstadoCivil,
    val numeroHijos: Int,
    val discapacidad: Int
)