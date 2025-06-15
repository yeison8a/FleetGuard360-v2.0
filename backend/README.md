# 🚍 Feature 4. Alertas y Notificaciones Inteligentes (EV04) 

FleetGuard360 – Sistema Inteligente de Monitoreo Satelital para Transporte de Pasajeros

### Integrantes de Arq. de Software en el equipo: 
- Angie Paola Yarce Gomez
- Yeison Ochoa Cárdenas 
- Juan Carlos Santa Hurtado 


## 🧭 Contexto del Sistema y Alcance General

El sistema de alertas y notificaciones inteligentes busca centralizar la recepción, gestión y notificación de eventos relevantes relacionados con la operación de flotas vehiculares, como exceso de velocidad, cambios de conductor o eventos importantes. Está orientado a mejorar la seguridad, trazabilidad y reacción ante incidentes.

**Usuarios principales**:
- Operadores de flota
- Conductores
- Administradores de transporte

### 📈 Diagrama de Contexto
A continuación una vista de alto nivel del sistema, en donde mostramos cómo interactúa con los actores externos (usuarios, sistemas, dispositivos), sin entrar aún en el detalle de los procesos internos. 

El objetivo es alinear al equipo sobre los límites del sistema y sus interacciones clave.

![Diagrama de Contexto](imagenes/1.2.%20Diagrama%20de%20contexto.png)

#### Entradas:
- Eventos desde dispositivos IoT/GPS en vehículos
- Ingreso de operadores mediante Login, o registro
- CRUD de tipo de alertas por parte de operadores

#### Salidas:
- Notificaciones por email y mensajes internos
- Visualización cronológica de alertas
- Reportes estadísticos exportables

#### Sistemas externos:
- GPS y Dispositivos IoT de monitorización
- Servicio de correo electrónico transaccional
- Base de datos
---

## 🔧 Requisitos Arquitectónicos

Se definen los principales requisitos no funcionales, estos serán algunos criterios que se usarán para juzgar la operación de sistema, entre ellos se mirará rendimiento, disponibilidad, escalabilidad y seguridad. 

A su vez se identifican y se establecen formalmente restricciones técnicas.


### Requisitos No Funcionales
- **Rendimiento**: Soporte para múltiples flotas simultáneamente con baja latencia. Se espera tiempos de respuesta < 500 ms para vistas de alerta.
- **Alta Disponibilidad (99.9%)**: Arquitectura tolerante a fallos.
- **Escalabilidad**: El sistema debe soportar crecimiento en número de alertas y usuarios.
- **Seguridad**: Autenticación basada en JWT, roles y permisos, cifrado de datos sensibles en tránsito (HTTPS) y en reposo.

### Restricciones Técnicas
#### Frontend:
- **Lenguaje**: JavaScript/TypeScript (Node.js y React)
- **Frameworks**: React.js, Next.js, Tailwind CSS
- Plataforma web responsive

#### Backend:
- **Lenguaje**: Java 17 (implícito por uso de Spring Boot)
- **Framework**: Spring Boot (framework Java para backend REST AP

#### Base de Datos:
- Se utiliza **PostgreSQL**

#### Autenticación y Seguridad:
- Uso de **JWT (JSON Web Token)** como mecanismos de autenticación/autorización obligatorios.

#### Herramientas de Desarrollo y DevOps:
- **Azure DevOps** (Integración continua/entrega continua, planificación de tareas, etc.)
- **GitHub** (repositorio de código)
- **SonarQube** (análisis de calidad del código)
- **ScreenPlay** (posiblemente para pruebas automatizadas siguiendo el patrón Screenplay)

#### Plataformas de Despliegue:
- Frontend: Despliegue en **Vercel**
- Backend: Despliegue en **Render**
- Base de Datos: Despliegue en **Supabase**

---

## 🧱 Estilos y Patrones Arquitectónicos Seleccionados

### Estilos Adoptados
- **Monolito Modular**: Separación por capas internas (servicios, repositorios, controladores).

  Este estilo hace referencia a la Arquitectura en Capas (Layered Architecture):
  - **Capa de presentación**: Gestiona las solicitudes HTTP, la autenticación y la conversión JSON.
  
  - **Capa de negocio**: Contiene la lógica de negocio, la validación y la autorización.
  
  - **Capa de persistencia**: Gestiona las interacciones con la base de datos mediante el marco ORM Spring Data JPA.
  
  - **Capa de base de datos**: Almacena los datos de la aplicación mediante la base de datos relacional PostgreSQL.


- **Eventual Event-Driven (futuro)**: Para alertas y notificaciones.

### Patrones Utilizados
- **Patrón Repositorio**: Para el acceso a datos con JPA. Separa lógica de persistencia.
- **Patrón DTO y Mapeo**: Transferencia de datos desacoplada.
- **Patrón de Servicio**: Encapsulamiento de lógica de negocio.
- **Patrón Controller REST**: Comunicación HTTP con clientes.
- **JWT + Filtro**: Autenticación y autorización basada en tokens.
- **Eventualmente Patrón Notificación**: Observador/EventListener para alertas.

---

## 📦 Vista de Paquetes

### Diagrama de Paquetes
Se realiza una modularización de la feature asignada dando como resultado esta vista:
![Diagrama de Paquetes](imagenes/4.%20Vista%20de%20Paquetes.png)

### Descripción de Paquetes principales
- `controller`: Expone las APIs REST para acceso de los clientes y/o desde el frontend.
- `service`: Lógica de negocio centralizada.
- `repository`: Interfaces JPA para acceso a datos.
- `model`: Entidades del dominio persistentes.
- `dto`: Objetos de transferencia de datos entre capas.
- `filter`: Filtro JWT que valida peticiones entrantes.
- `security`: Configuraciones de seguridad, autenticación y roles.

### La relación entre paquetes
- Un cliente envía una solicitud HTTP a la capa del controlador.
- El controlador válida la solicitud y la reenvía a la capa de servicio. Si la solicitud trae un json se transforma en DTO, y ya para la respuesta es lo contrario, de DTO a json.
- La capa de servicio procesa la lógica de negocio y llama a la capa de repositorio. Al llamar a la capa repositorio el DTO es convertido a una entidad modelo, pero ya cuando devuelve los datos al controlador la entidad modelo se pasa a DTO.
- La capa de repositorio obtiene o actualiza los datos en la base de datos.
- La capa de modelo asigna los registros de la base de datos a objetos Java.
- Los datos procesados ​​se envían de vuelta a la capa de servicio, luego a la capa del controlador y, finalmente, se devuelven al cliente como una respuesta de la API.

---

## 🧩 Vista de Componentes

### Diagrama de Componentes
Con la vista de paquetes creada, se procede a identificar los componentes de cada uno. Para simplificar, en el diagrama se muestra un componente que representa al resto de componentes.

La vista obtenida es la siguiente:
![Diagrama de Componentes](imagenes/5.%20Vista%20de%20Componentes.png)

### Responsabilidades de los principales componentes:
- **API Gateway**: Gestiona las solicitudes de entrada, actuando como punto central para redirigir las peticiones a los controladores apropiados, aplicar políticas de seguridad, y manejar errores globalmente.
  
- **Endpoints management**
  - AlertController: *Expone los endpoints relacionados con la gestión de alertas (crear, consultar, eliminar, etc.).*
  - AuthController: *Maneja los endpoints de autenticación y autorización de usuarios (inicio de sesión, registro, renovación de tokens).*
  - GlobalExceptionHandler: *Captura y gestiona las excepciones lanzadas por los controladores para devolver respuestas consistentes al cliente.*
  - TipeAlertController: *Gestiona los endpoints asociados a los tipos de alertas, permitiendo su creación, modificación y consulta.*
    
- **Service management**
  - AlertService: *Contiene la lógica de negocio para la gestión de alertas, coordinando entre el controlador y el repositorio.*
  - AuthService: *Encapsula la lógica de autenticación de usuarios, como validación de credenciales y generación de tokens.*
  - JwtService: *Encargado de la generación, validación y decodificación de tokens JWT usados para la autenticación.*
  - TipeAlertService: *Implementa la lógica de negocio para la administración de los tipos de alerta.*
    
- **Repository management**
  - AlertRepository: *Accede y gestiona los datos persistentes de alertas en la base de datos.*
  - TipeAlertRepository: *Provee operaciones de acceso a datos para los tipos de alerta.*
  - UserRepository: *Maneja el acceso a la información de los usuarios, utilizada principalmente para autenticación y autorización.*
    
- **Security**:
  - AppicationConfig: *Define la configuración general de la aplicación, como beans de seguridad y servicios personalizados.*
  - SecurityConfig: *Configura las políticas de seguridad de la aplicación, como rutas públicas/protegidas, filtros y autenticación.*

- **Filter**: Controla jornadas y rotaciones.
  - JwtAuthenticationFilter: *Filtro que intercepta las peticiones HTTP para validar el token JWT y establecer el contexto de seguridad del usuario autenticado.*

**Interfaces**:
- Adicional, en el diagrama se muestra el tipo de interfaces (suministradas y/o requeridas) junto con la descripción general.
- Esta vista muestra el flujo de datos y como se comunican los conponentes en el sistema.

**Interfaces en general**:
- REST API (`/api/alerts`, `/api/users`, `/api/auth`)
- Integración futura con GPS Tracker externo via MQTT

---

## ☁️ Vista de Despliegue

### Diagrama de Despliegue
En nuestro escenario (en el que el Frontend será desplegado en Vercel, el Backend en Render, y la Base de Datos en Supabase), el siguiente diagrama de despliegue muestra los los servicios en la nube a usar, elementos que hacen parte y protocolos de comunicación que se utilizarán los diferentes componentes.

![Diagrama de Despliegue](imagenes/6.%20Vista%20de%20Despliegue.png)

### Tecnologías Previstas
- Vercel como **cloud platform as a service** para desplegar el frontend.
- Render como **cloud platform as a service** para entregar la API en un entorno de producción, se requiere un contenedor con Docker para ello.
- Supabase como **backend platform as a service** para alojar la base de datos en PostgreSQL.
- **Contenedores** Docker para empaquetado de servicios, previo al despligue del backend en Render.

---

## 🔌 Definición Inicial de APIs (REST)

La API REST está diseñada en estilo RESTful, con seguridad JWT.

### Endpoints Principales

#### 🚨 Gestión de Alertas

```http
GET    /api/alerts
POST   /api/alerts
DELETE /api/alerts/{id}
```

#### 🔐 Autenticación
```http
POST /api/login
POST /api/register
````

#### 📄 Gestión de Tipos de Alertas

```http
GET    /api/tipeAlerts
POST   /api/tipeAlerts
DELETE /api/tipeAlerts/{id}
```

---
## ▶️ ¿Cómo Ejecutar el Proyecto?

1. **Clonar el Repositorio**

```bash
git clone https://github.com/yeison8a/FleetGuard360.git
cd FleetGuard360
cd backend
```

2. **Configuración de la Base de Datos**

* Crear base de datos PostgreSQL
* Configurar `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fleetguard360
spring.datasource.username=tuusuario
spring.datasource.password=tucontraseña
spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
```

3. **Ejecutar con Maven**

```bash
./mvnw spring-boot:run
```

4. **Acceder al Sistema a través de Postman**

* URL: [http://localhost:8080](http://localhost:8080)
