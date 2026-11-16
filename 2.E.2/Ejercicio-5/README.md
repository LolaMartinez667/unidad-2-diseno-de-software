# 2.E.2 - Ejercicio 5: Reservas concurrentes de sala

## Objetivo

Administrar diez lugares compartidos y evitar reservas duplicadas o exceso de capacidad ante solicitudes simultaneas.

## Implementacion

`Ejercicio05ReservasSala` sincroniza cada operacion atomica y simula 30 solicitudes de socios en hilos concurrentes.

## Compilar y ejecutar

```powershell
javac -d out src\Ejercicio05ReservasSala.java
java -cp out unidad2.e2.Ejercicio05ReservasSala
```

## Captura de ejecucion

![Salida de ejecucion](captura/ejecucion.png)
