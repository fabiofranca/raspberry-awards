package raspberry.awards.api.mapper;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.*;

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

    @CsvBindAndSplitByName(elementType = String.class, required = true, splitOn = ",")
    private List<String> producers;
    @CsvBindByName(required = false)
    private String winner;
}
