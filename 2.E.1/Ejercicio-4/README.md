# 2.E.1 - Ejercicio 4: Cache en memoria

## Objetivo

Compartir una cache de clave-valor, permitiendo almacenar, consultar, validar y eliminar elementos.

## Implementacion

`Ejercicio04CacheMemoria` inicializa su Singleton al primer acceso y encapsula un `ConcurrentHashMap` para operaciones seguras entre hilos.

## Compilar y ejecutar

```powershell
javac -d out src\Ejercicio04CacheMemoria.java
java -cp out unidad2.e1.Ejercicio04CacheMemoria
```

## Captura de ejecucion

![Salida de ejecucion](captura/ejecucion.png)
