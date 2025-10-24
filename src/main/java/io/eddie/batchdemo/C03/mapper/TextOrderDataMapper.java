package io.eddie.batchdemo.C03.mapper;

import io.eddie.batchdemo.C03.model.OrderData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.LineMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TextOrderDataMapper implements LineMapper<OrderData> {

    @Override
    public OrderData mapLine(String line, int lineNumber) throws Exception {

        // line -> ORD20250801001,갤럭시 버즈2 프로,1,189000,2025-08-01 09:15:23

        String[] lineParts = line.split(",");


        return new OrderData(
                lineParts[0],
                lineParts[1],
                Long.parseLong(lineParts[2]),
                Long.parseLong(lineParts[3]),
                convertStringToLocalDateTime(lineParts[4])
        );
    }

    private LocalDateTime convertStringToLocalDateTime(String dateStr) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
