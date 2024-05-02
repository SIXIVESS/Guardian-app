package mx.itson.edu.guardian

data class User(var nombre:String?=null,
                var correo:String?=null,
                var contra:String?=null
){
    override fun toString()=nombre+"\t"+correo+"\t"+contra
}