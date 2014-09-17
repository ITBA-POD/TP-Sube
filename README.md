S.U.B.E. - Sistema Útil para Berificar Estudiantes
=======================================

El objetivo de este trabajo práctico es implementar un servicio distribuído que registre operaciones de consulta, uso y carga de una supuesta tarjeta similar a la SUBE.

Este trabajo práctico se realizará en grupos de 3 o 4 personas, y contará con dos entregas: una presentación del diseño de la solución y otra donde se prueba el sistema en clase. 

**La presentación de diseño se realizará el Lunes 29 de Septiembre y la prueba del sistema el Lunes 6 de Octubre.**

Descripción del problema
------------------------
- Existe un servidor central que contiene los saldos iniciales de tarjetas, registra nuevas tarjetas y recibe las actualizaciones de los saldos de las tarjetas. Este servidor central esta provisto por los profesores y tendrá una interfaz única para todos los grupos. Este servidor es lento para responder por definición.
- El servicio a implementar por los alumnos viene a funcionar como un *cache* de los saldos, recibiendo operaciones de consultas, gastos y recargos sobre las tarjetas. No se necesita guardar registro de las operaciones.
- No es necesario que el servicio *cache* mantenga actualizado al servidor central en forma constante, puede utilizar cualquier estrategia que definan para actualizar los saldos en el servidor central. El único requerimiento es que a pedido se vuelquen todos los saldos al servidor central para su control. De todas maneras se debe definir explícitamente que grado de desfasaje habrá entre el servicio de cache y el servidor central.
- El servicio **debe** estar implementado con un cluster de máquinas funcionando en forma transparente para los usuarios, mientras cumplan con las reglas de consistencia que definan.
- El servidor central es quien registra las nuevas tarjetas, el servicio no se entera de las nuevas tarjetas hasta que se usan por los clientes.
- Los clientes se van a conectar mediante RMI a un único *distribuidor de carga* que repartirá las operaciones entre los miembros del cluster en forma aleatoria. Los miembros del cluster que implementa el servicio *cache* deben registrarse en el *distribuidor de carga* para operar. Las invocaciones entre el balanceador y los miembros del cluster también será por RMI.
- El *distribuidor de carga* está provisto por los profesores.

Alcance
-------
A partir de las directivas descriptas a continuación los grupos deben diseñar la arquitectura servicio, lo cual implica:
- Reglas de consistencia: en que forma los resultados de las operaciones son consistentes. **Se debe definir claramente las reglas de consistencia en términos de tiempo y/o datos y/o actualizaciones.**
- Las reglas de consistencia se definen en forma separada para los clientes y para el servidor central. 
- Tiempos de respuesta: se espera un tiempo de respuesta promedio de **50 milisegundos** cada 1000 operaciones, indepdendiente de la carga. Esa medición la realizará el balanceador de carga.
- Si el balanceador solicita al servicio que baje todos los saldos al servidor central, esta operación debe ser sincrónica dado que apenas se complete se verificarán los saldos de las tarjetas.
- Los grupos deben implementar tanto el servicio como el cliente que se comunicará con el servicio. Luego todos los alumnos del curso ejecutarán el cliente para generar alta carga en el servicio y verificar la consistencia.
