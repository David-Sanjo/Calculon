package com.example.calculon.model

data class Resultado(
    val salarioBruto: Double,
    val salarioNeto: Double,
    val porcentajeIRPF: Double,
    val importeIRPF: Double,
    val deducciones: Double,
    val salarioPorPaga: Double,
    val brutoPorPaga: Double,
    val seguridadSocial: Double,
    val seguridadSocialPorPaga: Double,
    val irpfPorPaga: Double,
    val deduccionesPorPaga: Double
)