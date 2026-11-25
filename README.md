# SoundPlayer

SoundPlayer es un programa en Java que permite gestionar canciones, crear playlists, reproducir música y generar álbumes aleatorios según el estado de ánimo.

## 1. Cómo ejecutar el programa

1. Abrir el proyecto SoundPlayer en Java.
2. Abrir el archivo `Main.java`, situado en `src/main/java/`.
3. Ejecutar el programa desde la clase `Main`.
4. Utilizar el menú de la consola para seleccionar las diferentes opciones.

## 2. Ejemplos de uso

Al ejecutar el programa aparece el siguiente menú:

```text
===== SOUNDPLAYER =====
1. Gestionar canciones
2. Gestionar playlists
3. Reproducir
4. Generar album random por mood
Q. Salir
```

**Gestionar canciones:** Seleccionando la opción `1`, podemos consultar las canciones guardadas, añadir nuevas canciones o eliminar las existentes.

**Gestionar playlists:** Con la opción `2`, podemos crear playlists, consultar las existentes y añadir o eliminar canciones de ellas.

**Reproducir música:** Seleccionando la opción `3`, podemos reproducir una canción o una playlist introduciendo su ID. Solo se reproducen las canciones que tienen notas musicales disponibles.

**Generar álbum aleatorio:** Con la opción `4`, seleccionamos un mood y una duración en minutos. El programa genera automáticamente una playlist con canciones que cumplen las condiciones.

Para salir del programa, introducimos `Q`.

## 3. Estructura de la aplicación

El programa está dividido en diferentes paquetes:

- **model:** contiene las clases que representan las canciones, playlists y notas musicales.
- **dao:** se encarga de guardar y recuperar los datos en archivos JSON.
- **manager:** gestiona las canciones, playlists, reproducción y generación de álbumes.
- **view:** muestra los menús y recoge las respuestas del usuario.
- **controller:** conecta las opciones del menú con las funciones del programa.
- **audio:** contiene las clases encargadas de generar los sonidos.

La clase `Main.java` conecta todos estos componentes e inicia el programa.

Las canciones y playlists se almacenan en los archivos `songs.json` y `playlists.json`, dentro de la carpeta `data`.
