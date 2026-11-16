package com.example.calculon.logic

import com.example.calculon.model.DatosUsuario
import com.example.calculon.model.DesgloseSS
import com.example.calculon.model.TipoContrato

private const val PORCENTAJE_CONTINGENCIAS_COMUNES = 4.70
private const val PORCENTAJE_DESEMPLEO_INDEFINIDO = 1.55
private const val PORCENTAJE_DESEMPLEO_TEMPORAL = 1.60
private const val PORCENTAJE_FORMACION_PROFESIONAL = 0.10
private const val PORCENTAJE_MEI = 0.15
private const val BASE_MAXIMA_COTIZACION = 5101.20

private fun redondeo(importe: Double): Double {
    return kotlin.math.round(importe * 100) / 100
}

private fun calcularBaseCotizacion(datos: DatosUsuario): Double {

    val salarioMensual = datos.salarioBruto / 12.0

    val baseMinima = datos.grupoProfesional.baseMinimaMensual

    return minOf(
        maxOf(salarioMensual, baseMinima),
        BASE_MAXIMA_COTIZACION
    )
}

fun calcularCotizacionSS(datos: DatosUsuario): DesgloseSS {

    // Obtenemos la base mensual de cotización
    val base = calcularBaseCotizacion(datos)

    // Seleccionamos el desempleo según el tipo de contrato
    val porcentajeDesempleo = when (datos.tipoContrato) {
        TipoContrato.INDEFINIDO ->
            PORCENTAJE_DESEMPLEO_INDEFINIDO

        TipoContrato.TEMPORAL ->
            PORCENTAJE_DESEMPLEO_TEMPORAL
    }

    // Calculamos cada concepto por separado
    val importeContingenciasComunes = redondeo(
        base * PORCENTAJE_CONTINGENCIAS_COMUNES / 100.0
    )

    val importeDesempleo = redondeo(
        base * porcentajeDesempleo / 100.0
    )

    val importeFormacionProfesional = redondeo(
        base * PORCENTAJE_FORMACION_PROFESIONAL / 100.0
    )

    val importeMEI = redondeo(
        base * PORCENTAJE_MEI / 100.0
    )

    // Devolvemos el desglose completo
    return DesgloseSS(
        baseCotizacion = base,

        porcentajeContingenciasComunes =
            PORCENTAJE_CONTINGENCIAS_COMUNES,
        importeContingenciasComunes =
            importeContingenciasComunes,

        porcentajeDesempleo = porcentajeDesempleo,
        importeDesempleo = importeDesempleo,

        porcentajeFormacionProfesional =
            PORCENTAJE_FORMACION_PROFESIONAL,
        importeFormacionProfesional =
            importeFormacionProfesional,

        porcentajeMEI = PORCENTAJE_MEI,
        importeMEI = importeMEI
    )
}


