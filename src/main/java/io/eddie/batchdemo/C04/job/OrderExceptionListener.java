package io.eddie.batchdemo.C04.job;

import io.eddie.batchdemo.C04.exception.InvalidOrderDataException;
import io.eddie.batchdemo.C04.model.OrderData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.FlatFileParseException;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderExceptionListener
        implements SkipListener<OrderData, OrderData>, StepExecutionListener {

    private final List<InvalidOrderDataException> skippedReadExceptions = new ArrayList<>();
    private final Path outputPath = Path.of("src/main/resources/out/order_ng_list.txt");

    @Override
    public void onSkipInRead(Throwable t) {

        if ( t instanceof FlatFileParseException ffpe ) {

            Throwable cause = ffpe.getCause();

            if ( cause instanceof InvalidOrderDataException iod ) {
                skippedReadExceptions.add(iod);
            } else {
                skippedReadExceptions.add(new InvalidOrderDataException(
                        0,
                        "",
                        "",
                        (cause != null ? cause : ffpe)
                ));
            }
        }

    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        skippedReadExceptions.clear();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        Path out = outputPath.isAbsolute()
                ? outputPath
                : outputPath.toAbsolutePath();

        try(BufferedWriter bw = Files.newBufferedWriter(out,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            bw.write(" lineNumber  /  reason  /  rawLine  ");
            bw.newLine();

            for (InvalidOrderDataException ex : skippedReadExceptions) {
                bw.write(String.format("%d  /  %s  /  %s",
                        ex.getLineNumber(),
                        ex.getReason(),
                        ex.getRawLine()));
                bw.newLine();
            }


        } catch (Exception e) {
            log.error("ㅋㅋ;");
        }

        return stepExecution.getExitStatus();
    }

}
