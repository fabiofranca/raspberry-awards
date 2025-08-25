package raspberry.awards.api.mapper;

public interface CsvMapper <T, K>{

    T mapTo(K k);

    K mapFrom(T t);
}
