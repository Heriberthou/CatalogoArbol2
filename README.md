# **Catálogo de Árboles - Universidad del Mar**

> Aplicación web interactiva para explorar, registrar e identificar árboles del campus universitario.

## Descripción

Este proyecto fue desarrollado con fines educativos para la **Universidad del Mar**. 
Permite a los usuarios conocer la vegetación arbórea del campus, registrar nuevas especies y fomentar el interés por la biodiversidad local .


## Objetivo

> Brindar una herramienta digital que ayude a:
- Identificar especies de árboles.
- Registrar información botánica (nombre común, nombre científico, ubicación).
- Cargar imágenes asociadas.
- Promover la conservación ambiental y el aprendizaje ecológico.

## Funcionalidades

| Funcionalidad                      | Descripción |
|------------------------------------|----------------|
| Visualización del catálogo         | Muestra una lista de árboles con sus datos. |
| Registro de nuevas especies        | Formulario para añadir árboles al catálogo. |
| Búsqueda y filtrado                |  Filtra por nombre o especie. |
| Interfaz responsiva                | Compatible con móviles y tablets. |
| Soporte de imágenes                | Permite subir una imagen del árbol. |

## Tecnologías utilizadas
✔ Android SDK
✔ Java
✔ SQLite
✔ JUnit

## Estrucutura del proyecto
/catalogo-arbol
app/

├── manifests/

│   └── AndroidManifest.xml             # Archivo de manifiesto de la app

├── java/

│   └── com.example.catlogoarbol/       # Paquete principal

│       ├── Arbol.java                  # Clase modelo de árbol

│       ├── ArbolAdapter.java           # Adaptador para lista de árboles

│       ├── AtributosActivity.java      # Actividad para atributos del árbol

│       ├── DatabaseHelper.java         # Clase para gestión de la base de datos SQLite

│       ├── DatosGeneralesActivity.java # Actividad para datos generales del árbol

│       ├── InteraccionActivity.java    # Actividad para interacciones con el árbol

│       ├── MainActivity.java           # Actividad principal (pantalla inicial)

│       ├── ObservacionesFotosActivity.java # Actividad para fotos y observaciones

│       └── SplashActivity.java         # Pantalla de bienvenida

│
├── androidTest/

│   └── com.example.catlogoarbol/

│       └── ExampleInstrumentedTest.java # Pruebas instrumentadas (con dispositivo)

│
├── test/

│   └── com.example.catlogoarbol/

│       └── ExampleUnitTest.java        # Pruebas unitarias

│
├── res/                                # Recursos de la app (layouts, strings, drawables, etc.)

└── res (generated)/                    # Recursos generados automáticamente

└── README.md                  # Este archivo

 ## ¿Cómo usar?
### Clona el repositorio:
      git clone https://github.com/Heriberthou/CatalogoArbol2.git

- Abre index.html en tu navegador.
- Explora árboles, añade nuevos y visualiza detalles.

## Autores
CORTES PEREZ IVAN ALEXANDER: 
ivanalexander.cortesperez@aulavirual.umar.mx

GALVAN GERMAN ALONDRA: 
alondra.galvangerman@aulavirual.umar.mx

GOMEZ BOLAINA HERIBERTO: 
heriberto.gomezbolaina@aulavirual.umar.mx

LOPEZ CRUZ NELIDA: 
nelida.lopezcruz@aulavirual.umar.mx

QUINTAS ROJAS ABRIL AZENETH: 
abrilazeneth.quintasrojas@aulavirual.umar.mx

## Universidad del Mar

## Licencia
Este proyecto es de uso educativo y puede ser adaptado para otros contextos escolares o universitarios.
