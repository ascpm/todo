package com.ascpm.todo.data.entity.comm;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Optional;

public interface MappingTypeConverter<T extends MappingType<V>, V> extends AttributeConverter<T, V> {

    @Override
    default V convertToDatabaseColumn(T attribute) {
        return Optional.ofNullable(attribute)
                .map(T::getCode)
                .orElse(null);
    }

    @Override
    default T convertToEntityAttribute(V dbData) {
        return Optional.ofNullable(dbData).flatMap(d -> Arrays.stream(this.getTypes())
                .filter(t -> t.getCode().equals(d))
                .findAny())
                .orElse(null);
    }

    T[] getTypes();
}
