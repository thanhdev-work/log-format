# Read Me First

This is a log handler module for request, response and log format module to fit input data into the ELK Stack.

# Getting Started

## Log output sample

Following json format:
```json
{
  "@timestamp": "2023-08-25T14:57:51.473+07:00",
  "RequestId": "null",
  "service": "demo-log-service",
  "level": "INFO",
  "logger_name": "vn.com.demologservice.logger.LoggingAspect",
  "message": {
    "requestPath": "/post-end-point",
    "method": "POST",
    "request": {
      "name": "Thành"
    }
  },
  "thread_name": "http-nio-8081-exec-2"
}
```

```json
{
  "@timestamp": "2023-08-21T14:50:20.927+07:00",
  "service" : "demo-log-app",
  "level": "INFO",
  "logger_name": "vn.com.demologservice.DemoLogServiceApplication",
  "message": "We have message!!!",
  "thread_name": "main"
}
```

```json
{
  "@timestamp": "2023-08-25T14:01:15.86+07:00",
  "level": "ERROR",
  "logger_name": "org.springframework.boot.SpringApplication",
  "message": "Application run failed",
  "thread_name": "main",
  "stackTrace": "java.lang.IllegalStateException: Logback configuration error detected: \r\nERROR in net.logstash.logback.composite.loggingevent.LoggingEventPatternJsonProvider@f5958c9 - Invalid [pattern]: pattern is not a valid JSON object net.logstash.logback.pattern.AbstractJsonPatternParser$JsonPatternException: pattern is not a valid JSON object etc"
}
```

## Dependencies and service stack versions

### Application dependencies:

```xml
<dependencies>
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjrt</artifactId>
    </dependency>
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjrt</artifactId>
    </dependency>
    <dependency>
        <groupId>net.logstash.logback</groupId>
        <artifactId>logstash-logback-encoder</artifactId>
        <version>7.3</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback.contrib</groupId>
        <artifactId>logback-json-classic</artifactId>
        <version>0.1.5</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback.contrib</groupId>
        <artifactId>logback-jackson</artifactId>
        <version>0.1.5</version>
    </dependency>
</dependencies>
```

### ELK Version:

Elasticsearch: 6.8.23 <br>
Kibana: 6.8.23 <br>
Logstash: 6.8.23 <br>
Filebeat: 6.8.23

## Application configuration

### Step 1: Config application name

Define the app name in the <code>application.properties</code>
<code> spring.application.name = example-app-name </code>

### Step 2: Define annotation path

In the <code>LoggingAspect</code> set value to reference <code>LogActivities</code> annotation based on source root <br>
Ex: <code>@annotation(vn.com.demologservice.logger.LogActivities)</code>

### Step 3: Use log annotation

The controller will receive the http request, we will use the annotation with the scope of `METHOD` on which endpoints
need to log <br>
Ex:
```java
    @LogActivities
    @PostMapping("post")
    private String sendName(PersonRequest request){
        //snipped
    }
```

## Stack installation and configuration
### In

