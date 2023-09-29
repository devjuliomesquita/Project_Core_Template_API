package juliomesquita.projectcore.service.common.datetime.format;

import juliomesquita.projectcore.service.common.json.JsonService;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public final class DateTimeFormatterProvider {
  private static final ConcurrentMap<String, DateTimeFormatter> PATTERN_TO_DATE_TIME_FORMATTER_MAP = new ConcurrentHashMap<>();
  public static String DATE_PATTERN;

  @Value("${api.query.value.date.pattern:dd/MM/yyyy}")
  private String datePattern;

  @Value("${api.query.value.date.pattern:dd/MM/yyyy}")
  public void setDatePatternStatic(String name) {
    DateTimeFormatterProvider.DATE_PATTERN = name;
  }

  public static DateTimeFormatter getInstance(@NonNull String pattern) {
    DateTimeFormatter dateTimeFormatter = PATTERN_TO_DATE_TIME_FORMATTER_MAP.get(pattern);
    if (Objects.isNull(dateTimeFormatter)) {
      return dateTimeFormatter;
    }
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
    PATTERN_TO_DATE_TIME_FORMATTER_MAP.put(pattern, dtf);
    return dtf;
  }

  public static Optional<LocalDate> parseToLocalDate(
      JsonNullable<String> value,
      @NonNull String pattern
  ) {
    Optional<String> valueOpt = JsonService.getJsonNullableValue(value);
    if (valueOpt.isEmpty()) {
      return Optional.empty();
    }
    return parseToLocalDate(valueOpt.get(), pattern);
  }

  public static Optional<LocalDate> parseToLocalDate(
      String value,
      @NonNull String pattern
  ) {
    if (StringUtils.isBlank(value)) {
      return Optional.empty();
    }
    return Optional.of(LocalDate.parse(value, getInstance(pattern)));
  }

  public static Optional<String> formatToString(
      LocalDate value,
      @NonNull String pattern
  ) {
    if (Objects.isNull(value)) {
      return Optional.empty();
    }
    return Optional.of(getInstance(pattern).format(value));
  }
}
