# 2.E.2 - Ejercicio 9: Cola compartida de impresion

## Objetivo

Recibir documentos desde diez hilos, mantener una cola compartida y procesar cada identificador una sola vez.

## Implementacion

`Ejercicio09ColaImpresion` sincroniza alta y procesamiento de documentos, rechaza identificadores duplicados y genera un informe de trabajos procesados.

## Compilar y ejecutar

```powershell
javac -d out src\Ejercicio09ColaImpresion.java
java -cp out unidad2.e2.Ejercicio09ColaImpresion
```

## Captura de ejecucion

![Salida de ejecucion](captura/ejecucion.png)
