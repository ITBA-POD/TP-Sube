S.U.B.E. - Sistema Útil para Berificar Estudiantes
=======================================

El objetivo de este trabajo práctico es implementar un servicio distribuído que registre operaciones de consulta, uso y carga de una supuesta tarjeta similar a la SUBE.

Este trabajo práctico se realizará en grupos de 3 o 4 personas, y contará con dos entregas: una presentación del diseño de la solución y otra donde se prueba el sistema en clase. 

**La presentación de diseño se realizará el Lunes 29 de Septiembrey la prueba del sistema el Lunes 6 de Octubre.**

Descripción del problema
------------------------
- Existe un servidor central que contiene los saldos iniciales de tarjetas, registra nuevas tarjetas y recibe las actualizaciones de los saldos de las tarjetas. Este servidor central esta provisto por los profesores y tendrá una interfaz única para todos los grupos. Este servidor es lento para responder por definición.
- El servicio a implementar viene a funcionar como un *cache* de los saldos, recibiendo operaciones de consultas, gastos y recargos sobre las tarjetas.
- No es necesario que el servicio *cache* mantenga actualizado online al servidor central, puede utilizar la estrategia que defina para actualizar los saldos en el servidor central. 
- El servidor central es quien registra las nuevas tarjetas, el servicio no se entera de las nuevas tarjetas hasta que se usan por los clientes.

Alcance
-------
A partir de las directivas descriptas a continuación los grupos deben diseñar la arquitectura servicio, lo cual implica:
- Reglas de consistencia 
- 

