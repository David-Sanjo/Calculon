
package com.example.calculon

import com.example.calculon.logic.calcularSalario
import com.example.calculon.model.DatosUsuario
import com.example.calculon.model.EstadoCivil
import com.example.calculon.model.GrupoProfesional
import com.example.calculon.model.TipoContrato
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculadoraIRPFTest {

    @Test
    fun salario24000SinHijos() {
        val datos = DatosUsuario(
            salarioBruto = 24000.0,
            numeroPagas = 12,
            grupoProfesional = GrupoProfesional.GRUPO_5,
            edad = 40,
            discapacidad = 0,
            estadoCivil = EstadoCivil.SOLTERO,
            numeroHijos = 0,
            tipoContrato = TipoContrato.INDEFINIDO
        )

        val resultado = calcularSalario(datos)

        assertEquals(1560.0, resultado.deducciones - resultado.importeIRPF, 0.01)
        assertEquals(3243.0, resultado.importeIRPF, 0.01)
        assertEquals(19197.0, resultado.salarioNeto, 0.01)
    }

    @Test
    fun salario24000DosHijosCasado() {
        val datos = DatosUsuario(
            salarioBruto = 24000.0,
            numeroPagas = 12,
            grupoProfesional = GrupoProfesional.GRUPO_5,
            edad = 40,
            discapacidad = 0,
            estadoCivil = EstadoCivil.CASADO,
            numeroHijos = 2,
            tipoContrato = TipoContrato.INDEFINIDO
        )

        val resultado = calcularSalario(datos)

        assertEquals(2758.50, resultado.importeIRPF, 0.01)
        assertEquals(19681.50, resultado.salarioNeto, 0.01)
    }

    @Test
    fun salario24000DosHijosSoltero() {
        val datos = DatosUsuario(
            salarioBruto = 24000.0,
            numeroPagas = 12,
            grupoProfesional = GrupoProfesional.GRUPO_5,
            edad = 40,
            discapacidad = 0,
            estadoCivil = EstadoCivil.SOLTERO,
            numeroHijos = 2,
            tipoContrato = TipoContrato.INDEFINIDO
        )

        val resultado = calcularSalario(datos)

        assertEquals(2274.0, resultado.importeIRPF, 0.01)
        assertEquals(20166.0, resultado.salarioNeto, 0.01)
    }

    @Test
    fun salario12000CompruebaBaseMinima() {
        val datos = DatosUsuario(
            salarioBruto = 12000.0,
            numeroPagas = 12,
            grupoProfesional = GrupoProfesional.GRUPO_5,
            edad = 40,
            discapacidad = 0,
            estadoCivil = EstadoCivil.SOLTERO,
            numeroHijos = 0,
            tipoContrato = TipoContrato.INDEFINIDO
        )

        val resultado = calcularSalario(datos)

        assertEquals(1111.08, resultado.deducciones - resultado.importeIRPF, 0.01)
        assertEquals(634.39, resultado.importeIRPF, 0.01)
        assertEquals(10254.53, resultado.salarioNeto, 0.01)
    }
}
