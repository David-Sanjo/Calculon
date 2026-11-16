package com.example.calculon

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.calculon.model.Resultado


@Composable
fun PantallaResultados(
    resultado: Resultado,
    volver: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Resultados del cálculo",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 20.dp),
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(14.dp)
            ) {
                Text(
                    text = "Salario neto por paga",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "${"%.2f".format(resultado.salarioPorPaga)} €",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Text(
            text = "Resumen anual",
            style = MaterialTheme.typography.titleLarge,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                FilaImporte(
                    concepto = "Salario bruto",
                    importe = resultado.salarioBruto
                )

                FilaImporte(
                    concepto = "Seguridad Social",
                    importe = resultado.seguridadSocial
                )

                FilaImporte(
                    concepto = "IRPF",
                    importe = resultado.importeIRPF
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                FilaImporte(
                    concepto = "Deducciones totales",
                    importe = resultado.deducciones
                )

                FilaImporte(
                    concepto = "Salario neto anual",
                    importe = resultado.salarioNeto,
                    destacar = true
                )
            }
        }

        Text(
            text = "Desglose por paga",
            style = MaterialTheme.typography.titleLarge,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 20.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                FilaImporte(
                    concepto = "Salario bruto",
                    importe = resultado.brutoPorPaga
                )

                FilaImporte(
                    concepto = "Seguridad Social",
                    importe = resultado.seguridadSocialPorPaga
                )

                FilaImporte(
                    concepto = "IRPF",
                    importe = resultado.irpfPorPaga
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                FilaImporte(
                    concepto = "Deducciones",
                    importe = resultado.deduccionesPorPaga
                )

                FilaImporte(
                    concepto = "Salario neto",
                    importe = resultado.salarioPorPaga,
                    destacar = true
                )
            }
        }
        Button(
            onClick = volver,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("Volver")
        }
    }
}

@Composable
fun FilaImporte(
    concepto: String,
    importe: Double,
    destacar: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = concepto,
            style = if (destacar) {
                MaterialTheme.typography.titleMedium
            } else {
                MaterialTheme.typography.bodyLarge
            }
        )

        Text(
            text = "${"%.2f".format(importe)} €",
            style = if (destacar) {
                MaterialTheme.typography.titleMedium
            } else {
                MaterialTheme.typography.bodyLarge
            }
        )
    }
}