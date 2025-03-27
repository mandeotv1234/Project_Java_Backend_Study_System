# Sử dụng OpenJDK làm môi trường chạy
FROM openjdk:21-jdk-slim

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép file JAR vào container
COPY target/*.jar app.jar

# Mở cổng 8080
EXPOSE 8080

# Chạy ứng dụng
CMD ["java", "-jar", "app.jar"]
