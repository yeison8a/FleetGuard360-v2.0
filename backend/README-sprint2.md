# 🚨 Feature 4: Alertas y Notificaciones Inteligentes (EV04)  
**FleetGuard360 – Sistema Inteligente de Monitoreo Satelital para Transporte de Pasajeros**

## 1. 📘 Diseño Detallado de APIs

### 📄 Documentación Formal de APIs (OpenAPI/Swagger)

La documentación completa de la API está disponible en formato Swagger (OpenAPI 3.0) en el siguiente enlace:

👉 [Ver documentación Swagger](https://fleetguard360-v2-0.onrender.com/swagger-ui/index.html)

![swagger](https://github.com/user-attachments/assets/33a66ca2-a7fc-4f5c-94ec-cc1322a0c821)


También se incluye el archivo `openapi.json` en este [enlace](https://drive.google.com/file/d/1HqMLy_cshQkSsKrsI412fNhyjohfehV_/view?usp=drive_link)

---

### 📑 Definición de Contratos

Define los atributos requeridos en el cuerpo del request (POST y PUT), y los parámetros necesarios en la ruta o query (GET, DELETE con `{id}`).

#### 🔹 Endpoint: `/api/alerts`  
- **GET**: Obtener todas las alertas  
- **POST**: Crear nueva alerta  
  - **Campos obligatorios:**
    - `mensaje`: string
    - `tipoAlertaId`: integer
  - **Respuesta esperada (201):**
    ```json
    {
      "id": "string",
      "mensaje": "string",
      "prioridad": "string",
      "tipoAlerta": "string",
      "generadaPor": "string",
      "vehiculoId": "string",
      "fecha": "2025-06-18T04:37:24.579Z",
      "responsables": "string",
      "conductor": "string",
      "placaTransporte": "string",
      "ubicacion": "string"
    }
    ```
  - **Códigos de error:**
    - `400`: Campos faltantes o inválidos
    - `401`: No autenticado (falta JWT)
    - `500`: Error del servidor

#### 🔹 Endpoint: `/api/alerts/{id}`
- **GET**: Obtener alerta por ID  
- **PUT**: Actualizar alerta  
- **DELETE**: Eliminar alerta  
  - **Códigos de error:**
    - `404`: Alerta no encontrada
    - `403`: No autorizado
    - `500`: Error interno

#### 🔹 Endpoint: `/api/alerts/login`  
- **POST**: Autenticación de usuario  
  - **Campos obligatorios:**  
    - `correo`, `contrasena`
  - **Respuesta esperada (200):**
    ```json
    {
      "token": "<jwt_token>",
      "usuario": {
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "nombre": "string",
          "correo": "string",
          "rol": "ADMIN",
          "enabled": true,
          "username": "string",
          "authorities": [
            {
              "authority": "string"
            }
          ],
          "password": "string",
          "accountNonExpired": true,
          "accountNonLocked": true,
          "credentialsNonExpired": true
      }
    }
    ```

#### 🔹 Endpoint: `/api/alerts/register`  
- **POST**: Registro de usuario  
  - Similar a login, devuelve el token JWT.

#### 🔹 Endpoint: `/api/tipeAlerts`  
- **GET**: Obtener tipos de alerta  
- **POST**: Crear nuevo tipo de alerta

#### 🔹 Endpoint: `/api/tipeAlerts/{id}`  
- **DELETE**: Eliminar tipo de alerta por ID

---

### 🔐 Especificación de Mecanismos de Seguridad

- **Autenticación:**  
  - Basada en **JWT** (`Authorization: Bearer <token>`)
  - Tokens generados al iniciar sesión o registrarse.

- **Autorización por roles:**  
  - Roles disponibles: `ADMIN`, `OPERADOR`, `CONDUCTOR`
  - Criterios:
    - Solo `ADMIN` puede eliminar tipos de alerta
    - `OPERATOR` puede crear y consultar alertas

- **Protección de rutas:**  
  - Usamos `@EnableWebSecurity`, `JwtAuthenticationFilter`, y `SecurityConfig` para definir filtros y restricciones de acceso a rutas protegidas.

---

## 2. 🔌 Especificación de Protocolo de Comunicación Interna

### 📡 Comunicación entre componentes

| Comunicación | Participantes | Protocolo | Detalle |
|--------------|---------------|-----------|---------|
| Usuario → Frontend | Navegador → App en Vercel | `HTTPS` (HTTP/2) | Acceso seguro vía navegador al frontend. |
| Frontend → Backend | App React → API REST (Render.com) | `HTTPS` (HTTP/1.1) | Comunicación vía `fetch` con JWT en el header. |
| Backend → Base de Datos | API en Render → Supabase (PostgreSQL) | `PostgreSQL` TCP sobre `SSL/TLS` | Comunicación a través de drivers PostgreSQL sobre puerto 5432 con SSL habilitado. |

---

### 🌐 Protocolos hacia sistemas externos

| Sistema | Protocolo | Descripción |
|--------|-----------|-------------|
| Servicio de correos | `HTTPS` (API REST) | Envío de correos con **SendGrid**. Se usa `POST` a `https://api.sendgrid.com/v3/mail/send` con cabecera `Authorization: Bearer <API_KEY>` |

---

## 3. ⚙️ Implementación Completa del Backend

- Repositorio del código backend:  
  👉 [fleetguard360](https://github.com/yeison8a/FleetGuard360-v2.0/tree/main/backend)

- Estructura:  
  - `controllers/`: Manejo de endpoints
  - `services/`: Lógica de negocio
  - `repositories/`: Acceso a datos
  - `security/`: Configuración de seguridad y JWT
    

- 🧪 **Pruebas con Postman**  
  - Pruebas para cada ruta: éxito, error 400, 401, 403, 404.
  - Algunas prueba fueron:
    ![post-alerts](https://github.com/user-attachments/assets/decd7b70-a663-4512-b9b6-2a42eabab900)
    ![alerts](https://github.com/user-attachments/assets/14419fdf-dbc7-4538-b510-db131df55558)
    ![put-alerts](https://github.com/user-attachments/assets/f18786a7-331b-4103-8661-155f50bfe2f7)
    ![delete-alerts](https://github.com/user-attachments/assets/ece6a48f-0452-4d8a-8642-fd37afa5f1c0)

    


- 🐳 **Contenedores Docker**  
  - `Dockerfile` para backend
  - `docker-compose.yml` para entorno completo (backend + base de datos)
    
    ![docker-compose](https://github.com/user-attachments/assets/dc4380ba-9128-42d9-bdc1-a19451e577b7)

    ![despliegue-docker-local](https://github.com/user-attachments/assets/4430fb52-2fd2-4c50-a0da-9a5236ac67e6)


---

## 📎 Recursos Incluidos en el despliegue

- `docker-compose.yml` – Contenedores para despliegue local
