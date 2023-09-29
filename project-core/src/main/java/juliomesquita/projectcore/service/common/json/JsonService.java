package juliomesquita.projectcore.service.common.json;

import lombok.RequiredArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JsonService {

  public static <T> Optional<T> getJsonNullableValue(JsonNullable<T> jsonNullable) {
    if (Objects.isNull(jsonNullable) || !jsonNullable.isPresent()) {
      return Optional.empty();
    }

    return Optional.ofNullable(jsonNullable.get());
  }
}
