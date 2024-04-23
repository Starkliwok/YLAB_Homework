package com.Y_LAB.homework.entity.trainings;

import lombok.*;

/**
 * Класс дополнительной информации для тренировки
 * @author Денис Попов
 * @version 2.0
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class AdditionalData {

    /** Поле значения с идентификационным номером*/
    private final long id;

    /** Поле названия дополнительной информации*/
    @Setter
    private String name;

    /** Поле значения дополнительной информации*/
    @Setter
    private String value;

    /** Поле значения с идентификационным номером тренировки*/
    private final long trainingId;

    @Override
    public String toString() {
        return "\n" + name +
                ": " + value;
    }
}
