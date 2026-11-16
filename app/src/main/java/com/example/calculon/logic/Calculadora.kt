package com.example.calculon.logic

import com.example.calculon.model.DatosUsuario
import com.example.calculon.model.Resultado

fun calcularSalario(datos: DatosUsuario): Resultado {

    if (datos.salarioBruto <= 0.0) {
        return Resultado(
            salarioBruto = 0.0,
            salarioNeto = 0.0,
            porcentajeIRPF = 0.0,
            importeIRPF = 0.0,
            deducciones = 0.0,
            salarioPorPaga = 0.0,
            brutoPorPaga = 0.0,
            seguridadSocial = 0.0,
            seguridadSocialPorPaga = 0.0,
            irpfPorPaga = 0.0,
            deduccionesPorPaga = 0.0
        )
    }

    // Seguridad Social
    val desgloseSS = calcularCotizacionSS(datos)
    val cotizacionSS = desgloseSS.totalCotizacion * 12

    // IRPF
    val importeIRPF = calcularIRPF(datos, desgloseSS)

    // Deducciones
    val deducciones = cotizacionSS + importeIRPF

    // Salario neto
    val salarioNeto = datos.salarioBruto - deducciones

    // Importes por paga
    val brutoPorPaga = datos.salarioBruto / datos.numeroPagas
    val seguridadSocialPorPaga = cotizacionSS / datos.numeroPagas
    val irpfPorPaga = importeIRPF / datos.numeroPagas
    val deduccionesPorPaga = deducciones / datos.numeroPagas
    val salarioPorPaga = salarioNeto / datos.numeroPagas

    // Porcentaje de IRPF
    val porcentajeIRPF = importeIRPF / datos.salarioBruto * 100

    return Resultado(
        salarioBruto = datos.salarioBruto,
        salarioNeto = salarioNeto,
        porcentajeIRPF = porcentajeIRPF,
        importeIRPF = importeIRPF,
        deducciones = deducciones,
        salarioPorPaga = salarioPorPaga,
        brutoPorPaga = brutoPorPaga,
        seguridadSocial = cotizacionSS,
        seguridadSocialPorPaga = seguridadSocialPorPaga,
        irpfPorPaga = irpfPorPaga,
        deduccionesPorPaga = deduccionesPorPaga
    )
}