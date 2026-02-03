---
name: creador_de_habilidades
description: Asistente para la creación de nuevas habilidades siguiendo la documentación oficial de Antigravity.
---
# Instrucciones para el Creador de Habilidades

Esta habilidad te permite actuar como un experto en la creación de otras habilidades ("Skills"). Sigue estos pasos para guiar al usuario en el proceso de creación.

## Contexto
Las habilidades son carpetas que contienen instrucciones y recursos que extienden las capacidades del agente. Cada habilidad reside en su propia carpeta y debe tener obligatoriamente un archivo `SKILL.md`.

## Flujo de Trabajo

### 1. Definición de la Habilidad
Interactúa con el usuario para definir los siguientes parámetros obligatorios. Si el usuario no los proporciona de inicio, pregúntalos paso a paso:

*   **Nombre de la carpeta (ID)**: Debe ser compatible con sistemas de archivos (sin espacios, uso de guiones bajos o medios). Ejemplo: `mi_nueva_habilidad`.
*   **Nombre (Frontmatter)**: El identificador interno de la habilidad.
*   **Descripción**: Un resumen breve de lo que hace la habilidad.
*   **Instrucciones**: El contenido principal de la habilidad. ¿Qué pasos debe seguir el agente cuando la ejecute?

### 2. Creación de la Estructura
Utiliza la herramienta `write_to_file` para crear los archivos necesarios.

1.  **Directorio**: Crea una nueva carpeta en el directorio raíz de habilidades con el nombre definido.
    *   *Nota*: Si estás ejecutando esta habilidad desde `.../skills/creador_de_habilidades`, la nueva habilidad debe ir en `.../skills/<nueva_carpeta>`.
2.  **Archivo SKILL.md**: Crea este archivo dentro de la nueva carpeta.

### 3. Formato del Archivo SKILL.md
El contenido del archivo debe seguir estrictamente este formato (preserva las líneas de YAML al inicio):

```markdown
---
name: <NOMBRE_ELEGIDO>
description: <DESCRIPCION_ELEGIDA>
---
<INSTRUCCIONES_EN_MARKDOWN>
```

### 4. Archivos Adicionales (Opcional)
Si el usuario menciona scripts o recursos adicionales:
*   Crea subcarpetas como `scripts/` o `resources/` dentro de la carpeta de la habilidad.
*   Guarda los archivos correspondientes allí.

### 5. Verificación y Cierre
*   Confirma al usuario la ruta donde se ha creado la habilidad.
*   Sugiérele que puede pedir al agente "usar la habilidad <nombre>" en el futuro.
