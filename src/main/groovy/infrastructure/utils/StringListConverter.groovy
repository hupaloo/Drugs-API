package main.groovy.infrastructure.utils

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Collections.*

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ","

    @Override
    String convertToDatabaseColumn(List<String> stringList) {
        return stringList != null ? String.join(SPLIT_CHAR, stringList) : ""
    }

    @Override
    List<String> convertToEntityAttribute(String string) {
        string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : emptyList()
    }
}
