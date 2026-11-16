package com.example.calculon

import com.example.calculon.logic.calcularSalario
import com.example.calculon.model.DatosUsuario
import com.example.calculon.model.EstadoCivil
import com.example.calculon.model.GrupoProfesional
import com.example.calculon.model.TipoContrato
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculadoraTest {

    @Test
    fun calcularSalario_conContratoIndefinido_devuelveNetoCorrecto() {

        val datos = DatosUsuario(
            salarioBruto = 24000.0,
            numeroPagas = 12,
            grupoProfesional = GrupoProfesional.GRUPO_5,
            edad = 30,
            discapacidad = 0,
            estadoCivil = EstadoCivil.SOLTERO,
            numeroHijos = 0,
            tipoContrato = TipoContrato.INDEFINIDO
        )

        val resultado = calcularSalario(datos)

        assertEquals(19197.00, resultado.salarioNeto, 0.01)
        assertEquals(1560.0, resultado.seguridadSocial, 0.01)
    }

    @Test
    fun calcularSalario_conContratoTemporal_devuelveNetoCorrecto() {

        val datos = DatosUsuario(
            salarioBruto = 24000.0,
            numeroPagas = 12,
            grupoProfesional = GrupoProfesional.GRUPO_5,
            edad = 30,
            discapacidad = 0,
            estadoCivil = EstadoCivil.SOLTERO,
            numeroHijos = 0,
            tipoContrato = TipoContrato.TEMPORAL
        )

        val resultado = calcularSalario(datos)

        assertEquals(19188.60, resultado.salarioNeto, 0.01)
        assertEquals(1572.0, resultado.seguridadSocial, 0.01)
    }

    @Test
    fun calcularSalario_conSalarioInferiorAlMinimo_aplicaBaseMinima() {

        val datos = DatosUsuario(
            salarioBruto = 12000.0,
            numeroPagas = 12,
            grupoProfesional = GrupoProfesional.GRUPO_5,
            edad = 30,
            discapacidad = 0,
            estadoCivil = EstadoCivil.SOLTERO,
            numeroHijos = 0,
            tipoContrato = TipoContrato.INDEFINIDO
        )

        val resultado = calcularSalario(datos)

        val cotizacionMensualEsperada = 66.95 + 22.08 + 1.42 + 2.14
        val cotizacionEsperada = cotizacionMensualEsperada * 12

        assertEquals(
            10254.53,
            resultado.salarioNeto,
            0.02
        )
    }

    @Test
    fun calcularSalario_conSalarioSuperiorAlMaximo_aplicaBaseMaxima() {

        val datos = DatosUsuario(
            salarioBruto = 72000.0,
            numeroPagas = 12,
            grupoProfesional = GrupoProfesional.GRUPO_5,
            edad = 30,
            discapacidad = 0,
            estadoCivil = EstadoCivil.SOLTERO,
            numeroHijos = 0,
            tipoContrato = TipoContrato.INDEFINIDO
        )

        val resultado = calcularSalario(datos)

        val cotizacionMensualEsperada =
            239.76 + 79.07 + 5.10 + 7.65

        val cotizacionEsperada = cotizacionMensualEsperada * 12

        assertEquals(
            48464.57,
            resultado.salarioNeto,
            0.02
        )
    }
}