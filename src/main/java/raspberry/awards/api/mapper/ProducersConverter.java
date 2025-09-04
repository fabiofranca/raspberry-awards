package raspberry.awards.api.mapper;

import com.opencsv.bean.AbstractBeanField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ProducersConverter extends AbstractBeanField<ArrayList<String>, String > {

    public static final String AND_REGEX = "\\s+and\\s+";

    @Override
    protected Object convert(String value) {
        String[] column = value.split(",");

        return Arrays.stream(column)
                .map(name -> name.replaceAll(AND_REGEX, ","))
                .flatMap(name -> Arrays.stream(name.split(",")))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }

}
