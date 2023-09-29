package juliomesquita.projectcore.persistence.common.factory.specification.enums;

import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public enum ModelQueryFilterOperators {
    EQ("EQ"), NEQ("NEQ"),
    GTE("GTE"), GT("GT"),
    LTE("LTE"), LT("LT"),
    BETWEEN("BTW"),
    IN("IN"), NOT_IN("NOT_IN", "NI"),
    LIKE("LIKE", "LK"), NOT_LIKE("NOT_LIKE", "NLK")
    ;
    private static final Map<String, ModelQueryFilterOperators> ALIAS_TO_OPERATOR_MAP = initMap();
    private static final Set<String> ALL_ALIASES = Set.copyOf(ALIAS_TO_OPERATOR_MAP.keySet());

    @Getter
    private final Set<String> aliases;

    ModelQueryFilterOperators(@NonNull String... aliases) {
        if (aliases.length < 1) {
            throw new IllegalStateException("Aliases cannot be empty.");
        }
        this.aliases = Set.of(aliases);
    }

    private static Map<String, ModelQueryFilterOperators> initMap() {
        Map<String, ModelQueryFilterOperators> aliasesOperatorMap = new HashMap<>();

        for (ModelQueryFilterOperators enumOperator : ModelQueryFilterOperators.values()) {
            enumOperator.getAliases().forEach(alias -> aliasesOperatorMap.merge(
                    alias, enumOperator,
                    (e1, e2) -> {
                        throw new IllegalStateException(
                                "Duplicated Alias on enum members. Check " + e1 + " and " + e2 + "."
                        );
                    }
            ));
        }
        return aliasesOperatorMap;
    }

    public static Optional<ModelQueryFilterOperators> fromAlias(String alias) {
        if (StringUtils.isBlank(alias)) {
            return Optional.empty();
        }
        return Optional.ofNullable(ALIAS_TO_OPERATOR_MAP.get(alias));
    }

    public static Set<String> getAllAliases() {
        return ALL_ALIASES;
    }
}
