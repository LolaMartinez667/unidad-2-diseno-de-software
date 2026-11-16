# Unidad 2 - Diseno de Software

Resoluciones en Java de las ejercitaciones 2.E.1 y 2.E.2 sobre el patron Singleton.

## Estructura

- `src/main/java/unidad2/e1`: ejercicios 1 al 10 de la ejercitacion 2.E.1.
- `src/main/java/unidad2/e2`: ejercicios 1 al 10 de la ejercitacion 2.E.2.
- `config.properties`: configuracion inicial del ejercicio 1 de 2.E.1.

Cada ejercicio es una clase independiente con su propio metodo `main`.

## Compilar

Desde la raiz del proyecto, con JDK 17 o posterior:

```powershell
javac -d out (Get-ChildItem -Recurse src\main\java -Filter *.java | ForEach-Object { $_.FullName })
```

Luego se puede ejecutar una clase, por ejemplo:

```powershell
java -cp out unidad2.e1.Ejercicio01Configuracion
java -cp out unidad2.e2.Ejercicio05ReservasSala
```

El ejercicio 1 de 2.E.1 lee sus propiedades desde `config.properties` en la raiz del proyecto.
