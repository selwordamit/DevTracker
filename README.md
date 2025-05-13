# DevTrack 🧠📺

**DevTrack** is a self-learning management app built for beginner developers and self-taught learners who feel overwhelmed by the endless content on YouTube and across the web.  
Its goal is to help users build a structured learning roadmap based on their target role, track their progress, and keep all their important videos, notes, and tasks in one organized place.

---

## ✨ Key Features

- 🔐 **User Authentication:** Sign up, login, and access management using JWT and Spring Security  
- 🗺️ **Custom Learning Roadmaps:** Users can create a personalized roadmap (e.g., *Backend Developer*)  
- 🎥 **Modules with YouTube Videos:** Each module includes a title, description, curated video, and user notes  
- ✅ **Progress Tracking & Tasks:** Mark modules as watched, add personal notes, and create tasks per module  
- 🛠️ **Full REST API:** Complete RESTful API with integrated Swagger documentation

---

## 🧰 Tech Stack

| Layer         | Technology                    |
|---------------|-------------------------------|
| Programming   | Java 21                        |
| Framework     | Spring Boot                    |
| Security      | Spring Security + JWT          |
| ORM           | Spring Data JPA (Hibernate)    |
| Database      | PostgreSQL                     |
| Build Tool    | Gradle                         |
| API Docs      | Swagger (OpenAPI)              |
| DevOps        | Docker + docker-compose        |

---

## 🧪 How to Run the Project

1. Make sure you have **Java 21** and **Gradle** installed  
2. Set up your `application.properties` with PostgreSQL connection details  
3. Run the application:

```bash
./gradlew bootRun
