# 2.E.2 - Ejercicio 10: Administrador de turnos

## Objetivo

Evitar asignar el mismo horario profesional a mas de un paciente cuando multiples solicitudes llegan al mismo tiempo.

## Implementacion

`Ejercicio10AdministradorTurnos` sincroniza reserva y cancelacion. El ejemplo hace que 20 hilos compitan por el mismo turno y verifica que solo uno lo obtiene.

## Compilar y ejecutar

```powershell
javac -d out src\Ejercicio10AdministradorTurnos.java
java -cp out unidad2.e2.Ejercicio10AdministradorTurnos
```

## Captura de ejecucion

![Salida de ejecucion](captura/ejecucion.png)
