S.U.B.E. - Sistema Útil para "B"erificar Estudiantes
=======================================

El objetivo de este trabajo práctico es implementar un servicio distribuído que registre operaciones de consulta, uso y carga de una supuesta tarjeta similar a la SUBE.

Este trabajo práctico se realizará en grupos de 3 o 4 personas, y contará con tres entregas: una presentación del diseño de la solución, una prueba inicial y una presentación final del sistema en clase. 

**La presentación de diseño se realizará el Lunes 29 de Septiembre y la primera prueba del sistema el Lunes 6 de Octubre. La entrega final será el 20 de Octubre**

Descripción del problema
------------------------
- Existe un servidor central que contiene los saldos iniciales de tarjetas, registra nuevas tarjetas y recibe las actualizaciones de los saldos de las tarjetas. Este servidor central esta provisto por los profesores y tendrá una interfaz única para todos los grupos. Este servidor es lento para responder por definición, y puede presentar fallas o timeouts.
- El servicio a implementar por los alumnos viene a funcionar como un *cache* de los saldos, recibiendo operaciones de consultas, gastos y recargos sobre las tarjetas. No se necesita guardar registro de las operaciones, solo la última fecha de actualización del saldo.
- No es necesario que el servicio *cache* mantenga actualizado al servidor central en forma constante, puede utilizar cualquier estrategia que definan para actualizar los saldos en el servidor central. El único requerimiento es que a pedido se vuelquen todos los saldos al servidor central para su control. De todas maneras se debe definir explícitamente que grado de desfasaje habrá entre el servicio de cache y el servidor central. La única condición de consistencia es que los saldos no estén más de **2** minutos desactualizados con el servidor (o sea: una tarjeta que registró movimientos no puede estar más de 2 minutos sin que se registre en el servidor central).
- El servicio **debe** estar implementado con un cluster de máquinas funcionando en forma transparente para los usuarios, mientras cumplan con las reglas de consistencia que definan.
- El servidor central es quien registra las nuevas tarjetas, el servicio no se entera de las nuevas tarjetas hasta que se usan por los clientes.
- Los clientes se van a conectar mediante RMI a un único *distribuidor de carga* que repartirá las operaciones entre los miembros del cluster en forma aleatoria. Los miembros del cluster que implementa el servicio *cache* deben registrarse en el *distribuidor de carga* para operar. Las invocaciones entre el balanceador y los miembros del cluster también será por RMI.
- El *distribuidor de carga* está provisto por los profesores.
- Las operaciones con las tarjetas tienen restricciones: 
 - el saldo no puede ser menor a cero (0.01 > 0) 
 - el monto de las recargas no puede ser mayor a 100
 - los gastos no pueden ser mayores a 100 y menores a 1
 - las recargas y gastos son montos con centavos (no puede ser 0.55555)

Alcance
-------
A partir de las directivas descriptas a continuación los grupos deben diseñar la arquitectura servicio, lo cual implica:
- Reglas de consistencia: en que forma los resultados de las operaciones son consistentes. **Se debe definir claramente las reglas de consistencia en términos de tiempo y/o datos y/o actualizaciones.**
- Las reglas de consistencia se definen en forma separada para los clientes y para el servidor central. 
- Tiempos de respuesta: se espera un tiempo de respuesta promedio de **50 milisegundos** cada 1000 operaciones, indepdendiente de la carga. Esa medición la realizará el balanceador de carga.
- Si el balanceador solicita al servicio que baje todos los saldos al servidor central, esta operación debe ser sincrónica dado que apenas se complete se verificarán los saldos de las tarjetas.
- Los grupos deben implementar tanto el servicio como el cliente que se comunicará con el servicio. Luego todos los alumnos del curso ejecutarán el cliente para generar alta carga en el servicio y verificar la consistencia.
- Los clientes que implementen deben considerar los errores que puede generar el servicio y manejarlos adecuadamente (reintentos, errors de validación). 
- Para probar bien los clientes deben tener dos versiones/modos: uno interactivo y otro automático. El interactivo es para probar a mano el uso de una tarjeta, el otro es para generar carga (y deberían usarlo en sus pruebas)

Prototipos de Interfaces Java
-

### Servidor Central (**CardRegistry**) - ESTA IMPLEMENTADO
Esta interfaz es usada por el servicio
- consultarSaldo(idTarjeta): double (si es negativo ver los códigos de error)
- agregarOperacion(idTarjeta, descripcion, monto): double (si es negativo ver los códigos de error)

### ServicioSube (**CardService**) - ESTE ES EL QUE TIENEN QUE IMPLEMENTAR
Esta interfaz es implementada por el servicio e invocada por el balanceador
- viajo(idTarjeta, costo): double (devuelve el saldo luego de descontar el viaje, si es negativo ver los códigos de error)
- recargo(idTarjeta, monto): double (devuelve el saldo luego de aplicar la recarga, si es negativo ver los códigos de error)
- consulto(idTarjeta): double (devuelve el saldo, si es negativo ver los códigos de error)
 
### ClienteSube extends ServicioSube - ESTE ES QUE TIENEN QUE USAR COMO CLIENTE
Esta interfaz es usada por clientes, e implementada por el balanceador
- nuevaTarjeta("nombre"): idTarjeta (registra una nueva tarjeta)

### Códigos de error
- **-1**: la tarjeta no existe
- **-2**: no se puede procesar el pedido, reintente
- **-3**: hubo un fallo en la comunicación con el servicio
- **-4**: el saldo de la tarjeta no permite realizar la operación
- **-5**: el servicio no respondío (timeout)
- Cualquier otro valor negativo puede ser usado por los grupos como código de error, pero debe ser documentado y explicado.
