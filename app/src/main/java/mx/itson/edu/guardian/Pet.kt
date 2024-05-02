package mx.itson.edu.guardian

data class Pet(
val nombre: String = "",
val raza: String = "",
val descripcion: String = "",
val edad: Int = 0,

val idUser: String? = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nombre" to nombre,
            "raza" to raza,
            "descripcion" to descripcion,
            "edad" to edad,

            )
    }
}


