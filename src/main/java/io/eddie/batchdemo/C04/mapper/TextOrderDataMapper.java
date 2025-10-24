package io.eddie.batchdemo.C04.mapper;

import io.eddie.batchdemo.C04.exception.InvalidOrderDataException;
import io.eddie.batchdemo.C04.model.OrderData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.LineMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TextOrderDataMapper implements LineMapper<OrderData> {

    @Override
    public OrderData mapLine(String line, int lineNumber) throws Exception {

        log.info("{}번 째 줄 내용 : {}", lineNumber, line);

        String[] lineParts = line.split(",");

        if ( lineParts.length != 5 )
            throw new InvalidOrderDataException(lineNumber,line, "컬럼 수가 올바르지 않습니다. 기대값=5, 실제값=%d".formatted(lineParts.length));


        String orderId = lineParts[0];
        String productName = lineParts[1];

        Long price;
        Long quantity;
        LocalDateTime orderDate;

        try {
            quantity = Long.parseLong(lineParts[2]);
        } catch ( Exception e ) {
            throw new InvalidOrderDataException(lineNumber,line, "갯수 파싱 실패 : %s".formatted(e.getMessage()), e);
        }

        try {
            price = Long.parseLong(lineParts[3]);
        } catch ( Exception e ) {
            throw new InvalidOrderDataException(lineNumber,line, "가격 파싱 실패 : %s".formatted(e.getMessage()), e);
        }

        try {
            orderDate = convertStringToLocalDateTime(lineParts[4]);
        } catch ( Exception e ) {
            throw new InvalidOrderDataException(lineNumber,line, "날짜 파싱 실패 : %s".formatted(e.getMessage()), e);
        }

        return new OrderData(
                orderId,
                productName,
                quantity,
                price,
                orderDate
        );
    }

    private LocalDateTime convertStringToLocalDateTime(String dateStr) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
