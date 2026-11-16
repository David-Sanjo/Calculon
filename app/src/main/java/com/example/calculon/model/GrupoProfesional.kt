package com.example.calculon.model


enum class GrupoProfesional(
    val numero: Int,
    val categoria: String,
    val baseMinimaMensual: Double
) {
    GRUPO_1(
        1,
        "Ingenieros y licenciados. Personal de alta dirección no excluido",
        1989.30,
    ),
    GRUPO_2(
        2,
        "Ingenieros técnicos, peritos y ayudantes titulados",
        1649.70,
    ),
    GRUPO_3(
        3,
        "Jefes administrativos y de taller",
        1435.20,
    ),
    GRUPO_4(
        4,
        "Ayudantes no titulados",
        1424.40,
    ),
    GRUPO_5(
        5,
        "Oficiales administrativos",
        1424.40,
    ),
    GRUPO_6(
        6,
        "Subalternos",
        1424.40,
    ),
    GRUPO_7(
        7,
        "Auxiliares administrativos",
        1424.40,
    ),
    GRUPO_8(
        8,
        "Oficiales de primera y segunda",
        1424.40,
    ),
    GRUPO_9(
        9,
        "Oficiales de tercera y especialistas",
        1424.40,
    ),
    GRUPO_10(
        10,
        "Peones",
        1424.40,
    ),
    GRUPO_11(
        11,
        "Trabajadores menores de 18 años",
        1424.40,
    )
}