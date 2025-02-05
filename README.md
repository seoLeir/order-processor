# Order Processor

## Описание
**Order Processor** — это сервис для **распределенной обработки заказов** в электронной коммерции с использованием **Apache Kafka**. Система обрабатывает заказы в многопоточном режиме, обеспечивает отказоустойчивость, обработку ошибок и мониторинг.

**Схема для базы данных находятся в папке database/**

## Функциональность
- **Многопоточная обработка** заказов с безопасной синхронизацией
- **Message Broker (Kafka)** для асинхронного взаимодействия между компонентами
- **Обработка ошибок** и автоматическая отправка в **Dead Letter Queue (DLQ)**
- **Мониторинг** через **Prometheus**
- **Аутентификация и авторизация** через **Spring Security**

## Требования для запуска:
- **Java 17**
- **Установленный docker engine(desktop)**
- **СУБД PostgreSQL и созданная бд order_processor или поменять на свою бд в application.yml, а также указать своего пользователя и пароль**
- 

## Используемые технологии
- **Java 17** + **Spring Boot 3**
- **Apache Kafka** (Producer & Consumer)
- **PostgreSQL** (Хранение заказов)
- **Spring Security + JWT** (Аутентификация)
- **Docker + Docker Compose** (Контейнеризация)

## Запуск проекта локально
### 1️⃣ **Клонируем репозиторий**
```sh
 git clone https://github.com/seoLeir/order-processor.git
 cd order-processor
```

### **Запускаем Kafka (Docker Compose)**
```sh
docker-compose -f docker/docker-compose.yml up -d
```

### 3**Запускаем приложение**
```sh
chmod +x ./gradlew
./gradlew bootRun 
```

### **Curl запрос для отправки тестового event Kafka Producer**
```sh
curl -X POST "http://localhost:9000/api/producer" \
     -H "Content-Type: application/json" \
     -d '{
          "orderId": "550e8400-e29b-41d4-a716-446655440000",
          "customerId": "a3bb189e-8bf9-3888-9912-ace4e6543002",
          "totalAmount": 250.75,
          "itemList": [
            {
              "itemId": "a3bb189e-8bf9-3888-9912-ace4e6543002",
              "quantity": 2,
              "price": 100.50
            },
            {
              "itemId": "550e8400-e29b-41d4-a716-446655440000",
              "quantity": 1,
              "price": 49.75
            }
          ]
        }'
```


## Обработка ошибок и DLQ
- Используется **@RetryableTopic**, чтобы **Kafka автоматически ретраила** неудачные сообщения.
- При **3 неудачных попытках** сообщения отправляются в **orders-dlq**.
- **DLQ Consumer** логирует и анализирует "битые" сообщения.

```java
@DltHandler
public void sendToDeadLetterQueue(String message, OrderEventDTO orderEventDTO, String topic) {
        String topicMessage = "Event on dlt topic= %s, payload=%s, message: %s".formatted(topic, orderEventDTO, message);
        log.error("Dead letter queue: {}", message);
        kafkaTemplate.executeInTransaction(operations -> operations.send(topic, topicMessage));
}
```

---
#### Автор: [seoLeir](https://github.com/seoLeir)

