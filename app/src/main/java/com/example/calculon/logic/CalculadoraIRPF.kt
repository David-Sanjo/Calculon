package com.example.calculon.logic

import com.example.calculon.model.DatosUsuario
import com.example.calculon.model.DesgloseSS
import com.example.calculon.model.EstadoCivil

private fun calcularMinimoPersonal(edad: Int): Double {
    var minimo = 5550.0

    if (edad > 65) {
        minimo += 1150.0
    }

    if (edad > 75) {
        minimo += 1400.0
    }

    return minimo
}

private fun calcularMinimoDiscapacidad(datos: DatosUsuario): Double {

    val minimoDiscapacidad = when (datos.discapacidad) {
        in 33..64 -> 3000.0
        in 65..100 -> 9000.0
        else -> 0.0
    }

    return minimoDiscapacidad
}

private fun calcularMinimoHijos(datos: DatosUsuario): Double {

    var minimoHijos = 0.0

    for (hijo in 1..datos.numeroHijos) {
        minimoHijos += when (hijo) {
            1 -> 2400.0
            2 -> 2700.0
            3 -> 4000.0
            else -> 4500.0
        }
    }

    if (datos.estadoCivil == EstadoCivil.CASADO ||
        datos.estadoCivil == EstadoCivil.DIVORCIADO) {
        minimoHijos *= 0.5
    }

    return minimoHijos
}

private fun calcularGastosDeducibles(desgloseSS: DesgloseSS): Double {
    val gastosSeguridadSocial = desgloseSS.totalCotizacion * 12
    val gastosGenerales = 2000.0

    return gastosSeguridadSocial + gastosGenerales
}

private fun calcularBaseIRPF(datos: DatosUsuario, desgloseSS: DesgloseSS): Double {

    val rendimientoNeto = datos.salarioBruto - calcularGastosDeducibles(desgloseSS)

    return rendimientoNeto
}

private data class Tramo(val limiteSuperior: Double, val tipo: Double)

private val TRAMOS = listOf(
    Tramo(12450.0, 0.19),
    Tramo(20200.0, 0.24),
    Tramo(35200.0, 0.30),
    Tramo(60000.0, 0.37),
    Tramo(300000.0, 0.45),
    Tramo(Double.POSITIVE_INFINITY, 0.47)
)

private fun calcularImpuestoTeorico(baseIRPF: Double): Double {

    if (baseIRPF <= 0.0) return 0.0

    var impuesto = 0.0
    var limiteInferior = 0.0

    for (tramo in TRAMOS) {
        if (baseIRPF <= limiteInferior) break
        val baseEnTramo = minOf(baseIRPF, tramo.limiteSuperior) - limiteInferior
        impuesto += baseEnTramo * tramo.tipo
        limiteInferior = tramo.limiteSuperior
    }
    return impuesto
}

private fun calcularImpuesto(datos: DatosUsuario, desgloseSS: DesgloseSS): Double {

    val baseIRPF = calcularBaseIRPF(datos, desgloseSS)

    return calcularImpuestoTeorico(baseIRPF)
}

private fun calcularTotalMinimos(datos: DatosUsuario): Double {

    val minimoPersonal = calcularMinimoPersonal(datos.edad)
    val minimoDiscapacidad = calcularMinimoDiscapacidad(datos)
    val minimoHijos = calcularMinimoHijos(datos)

    return minimoPersonal + minimoDiscapacidad + minimoHijos
}

private fun calcularCuotaMinimos(datos: DatosUsuario, baseIRPF: Double): Double {

    val totalMinimos = calcularTotalMinimos(datos)

    val minimoAplicable = minOf(totalMinimos, baseIRPF)

    return minimoAplicable * 0.19
}

fun calcularIRPF(datos: DatosUsuario, desgloseSS: DesgloseSS): Double {

    val baseIRPF = calcularBaseIRPF(datos, desgloseSS)

    val impuestoTeorico = calcularImpuestoTeorico(baseIRPF)

    val cuotaMinimos = calcularCuotaMinimos(datos, baseIRPF)

    val cuotaFinal = maxOf(0.0, impuestoTeorico - cuotaMinimos)

    return kotlin.math.round(cuotaFinal * 100) / 100
}