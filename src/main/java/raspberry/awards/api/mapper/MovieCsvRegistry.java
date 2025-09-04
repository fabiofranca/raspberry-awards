package raspberry.awards.api.mapper;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.*;
import raspberry.awards.api.persistency.Producer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCsvRegistry {

    @CsvBindByName(required = true)
    private Integer year;
    @CsvBindByName(required = true)
    private String title;
    @CsvBindByName(required = true)
    private String studios;

    @CsvCustomBindByName(converter = ProducersConverter.class)
    private List<String> producers;
    @CsvBindByName
    private String winner;
}
