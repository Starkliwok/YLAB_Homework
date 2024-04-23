package com.Y_LAB.homework.util.init;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class PropertiesLoaderTest {

    @Test
    @DisplayName("Получение файла .properties по пути к файлу, проверка содержимых данных в этом файле")
    void getProperties() {
        Properties properties = PropertiesLoader.getProperties("src/main/resources/application.properties");

        assertThat(properties.isEmpty()).isEqualTo(false);
        assertThat(properties.get("url"))
                .isEqualTo("jdbc:postgresql://127.0.0.1:5433/homework?currentSchema=training_diary_service");
        assertThat(properties.get("username")).isEqualTo("starkliw");
        assertThat(properties.get("password")).isEqualTo("ylab");
        assertThat(properties.get("changeLogFile")).isEqualTo("db/changelog/changelog.xml");
    }
}