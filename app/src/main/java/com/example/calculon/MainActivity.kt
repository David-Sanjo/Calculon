
package com.example.calculon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculon.ui.theme.CalculonTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.calculon.model.GrupoProfesional
import com.example.calculon.model.EstadoCivil
import com.example.calculon.model.TipoContrato
import com.example.calculon.model.DatosUsuario
import com.example.calculon.model.Resultado
import com.example.calculon.logic.calcularSalario
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CalculonTheme {

                var mostrarCarga by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(2500.milliseconds)
                    mostrarCarga = false
                }

                if (mostrarCarga) {
                    PantallaCarga()
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        PantallaInicio(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaInicio(modifier: Modifier = Modifier) {

    var salarioBruto by remember { mutableStateOf("0.0") }
    var numeroPagas by remember { mutableStateOf("14") }
    var grupoProfesional by remember {mutableStateOf(GrupoProfesional.GRUPO_1)}
    var edad by remember { mutableStateOf("0") }
    var discapacidad by remember { mutableStateOf("0") }
    var estadoCivil by remember {mutableStateOf(EstadoCivil.SOLTERO)}
    var numeroHijos by remember { mutableStateOf("0") }
    var tipoContrato by remember {mutableStateOf(TipoContrato.INDEFINIDO)}
    var resultado by remember { mutableStateOf<Resultado?>(null)}
    var mostrarResultados by remember { mutableStateOf(false)}

    var errorSalario by remember { mutableStateOf<String?>(null) }
    var errorPagas by remember { mutableStateOf<String?>(null) }
    var errorEdad by remember { mutableStateOf<String?>(null) }
    var errorDiscapacidad by remember { mutableStateOf<String?>(null) }
    var errorHijos by remember { mutableStateOf<String?>(null) }

    if (mostrarResultados && resultado != null) {
        PantallaResultados(
            resultado = resultado!!,
            volver = { mostrarResultados = false },
            modifier = modifier
        )
    } else {

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp).padding(bottom = 8.dp).padding(top = 8.dp)
        ) {
            Text(
                text = "Calculon",
                style = MaterialTheme.typography.headlineMedium,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Calcula tu salario neto.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = salarioBruto,
                onValueChange = {
                    salarioBruto = it
                    errorSalario = null
                },
                label = { Text("Salario bruto anual (€)") },
                isError = errorSalario != null,
                supportingText = {
                    errorSalario?.let {
                        Text(text = it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )

            OutlinedTextField(
                value = numeroPagas,
                onValueChange = {
                    numeroPagas = it.filter { caracter -> caracter.isDigit() }
                    errorPagas = null
                },
                label = { Text("Número de pagas") },
                isError = errorPagas != null,
                supportingText = {
                    errorPagas?.let {
                        Text(text = it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            SelectorGrupoProfesional(
                grupoSeleccionado = grupoProfesional,
                alSeleccionar = { grupoProfesional = it },
                modifier = Modifier
                    .fillMaxWidth(),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                OutlinedTextField(
                    value = edad,
                    onValueChange = {
                        edad = it.filter { caracter -> caracter.isDigit() }
                        errorEdad = null
                    },
                    label = { Text("Edad") },
                    isError = errorEdad != null,
                    supportingText = {
                        errorEdad?.let {
                            Text(text = it)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                OutlinedTextField(
                    value = discapacidad,
                    onValueChange = {
                        discapacidad = it.filter { caracter -> caracter.isDigit() }
                        errorDiscapacidad = null
                    },
                    label = { Text("Discapacidad (%)") },
                    isError = errorDiscapacidad != null,
                    supportingText = {
                        errorDiscapacidad?.let {
                            Text(text = it)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }

            SelectorEstadoCivil(
                estadoSeleccionado = estadoCivil,
                alSeleccionar = { estadoCivil = it },
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = numeroHijos,
                onValueChange = {
                    numeroHijos = it.filter { caracter -> caracter.isDigit() }
                    errorHijos = null
                },
                label = { Text("Número de hijos") },
                isError = errorHijos != null,
                supportingText = {
                    errorHijos?.let {
                        Text(text = it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            SelectorTipoContrato(
                contratoSeleccionado = tipoContrato,
                alSeleccionar = { tipoContrato = it },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Button(
                onClick = {
                    val salario = salarioBruto.replace(",", ".").toDoubleOrNull()
                    val pagas = numeroPagas.toIntOrNull()
                    val edadNumero = edad.toIntOrNull()
                    val discapacidadNumero = discapacidad.toIntOrNull()
                    val hijos = numeroHijos.toIntOrNull()

                    // Validar salario
                    errorSalario = when {
                        salario == null || !salario.isFinite() ->
                            "Introduce un salario válido"
                        salario < 0 ->
                            "Ese salario es demasiado bajo"
                        salario == 0.0 ->
                            "Introduce un salario mayor que 0 €"
                        else -> null
                    }

                    // Validar pagas
                    errorPagas = when {
                        pagas == null || pagas !in 1..16 ->
                            "Introduce entre 1 y 16 pagas"
                        else -> null
                    }

                    // Validar edad
                    errorEdad = when {
                        edadNumero == null || edadNumero !in 14..99 ->
                            "Con esa edad no estás para trabajar"
                        else -> null
                    }

                    // Validar discapacidad
                    errorDiscapacidad = when {
                        discapacidadNumero == null || discapacidadNumero !in 0..100 ->
                            "Introduce un porcentaje entre 0 y 100"
                        else -> null
                    }

                    // Validar hijos
                    errorHijos = when {
                        hijos == null || hijos < 0 ->
                            "Introduce un número válido"
                        hijos > 13 ->
                            "¡Que son muchos para sobrevivir!"
                        else -> null
                    }

                    // Si hay errores, no continuar
                    if (listOf(
                            errorSalario,
                            errorPagas,
                            errorEdad,
                            errorDiscapacidad,
                            errorHijos
                        ).any { it != null }
                    ) {
                        return@Button
                    }

                    val datos = DatosUsuario(
                        salarioBruto = salario!!,
                        numeroPagas = pagas!!,
                        grupoProfesional = grupoProfesional,
                        edad = edadNumero!!,
                        discapacidad = discapacidadNumero!!,
                        estadoCivil = estadoCivil,
                        numeroHijos = hijos!!,
                        tipoContrato = tipoContrato
                    )

                    resultado = calcularSalario(datos)
                    mostrarResultados = true
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Calcular")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorGrupoProfesional(
    grupoSeleccionado: GrupoProfesional,
    alSeleccionar: (GrupoProfesional) -> Unit,
    modifier: Modifier = Modifier
) {
    var desplegado by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = desplegado,
        onExpandedChange = { desplegado = !desplegado }
    ) {
        OutlinedTextField(
            value = grupoSeleccionado.categoria,
            onValueChange = {},
            readOnly = true,
            label = { Text("Grupo profesional") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = desplegado
                )
            },
            modifier = modifier.menuAnchor(
                ExposedDropdownMenuAnchorType.PrimaryNotEditable
            ).padding(bottom = 16.dp),
            shape = RoundedCornerShape(18.dp)
        )

        ExposedDropdownMenu(
            expanded = desplegado,
            onDismissRequest = { desplegado = false }
        ) {
            GrupoProfesional.entries.forEach { grupo ->
                DropdownMenuItem(
                    text = { Text(grupo.categoria) },
                    onClick = {
                        alSeleccionar(grupo)
                        desplegado = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorEstadoCivil(
    estadoSeleccionado: EstadoCivil,
    alSeleccionar: (EstadoCivil) -> Unit,
    modifier: Modifier = Modifier
) {
    var desplegado by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = desplegado,
        onExpandedChange = { desplegado = !desplegado }
    ) {
        OutlinedTextField(
            value = estadoSeleccionado.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Estado civil") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = desplegado
                )
            },
            modifier = modifier.menuAnchor(
                ExposedDropdownMenuAnchorType.PrimaryNotEditable
            ).padding(bottom = 16.dp),
            shape = RoundedCornerShape(18.dp)
        )

        ExposedDropdownMenu(
            expanded = desplegado,
            onDismissRequest = { desplegado = false }
        ) {
            EstadoCivil.entries.forEach { estado ->
                DropdownMenuItem(
                    text = { Text(estado.name) },
                    onClick = {
                        alSeleccionar(estado)
                        desplegado = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorTipoContrato(
    contratoSeleccionado: TipoContrato,
    alSeleccionar: (TipoContrato) -> Unit,
    modifier: Modifier = Modifier
) {
    var desplegado by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = desplegado,
        onExpandedChange = { desplegado = !desplegado }
    ) {
        OutlinedTextField(
            value = contratoSeleccionado.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tipo de contrato") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = desplegado
                )
            },
            modifier = modifier.menuAnchor(
                ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            shape = RoundedCornerShape(18.dp)
        )

        ExposedDropdownMenu(
            expanded = desplegado,
            onDismissRequest = { desplegado = false }
        ) {
            TipoContrato.entries.forEach { contrato ->
                DropdownMenuItem(
                    text = { Text(contrato.name) },
                    onClick = {
                        alSeleccionar(contrato)
                        desplegado = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInicioPreview() {
    CalculonTheme {
        PantallaInicio()
    }
}
