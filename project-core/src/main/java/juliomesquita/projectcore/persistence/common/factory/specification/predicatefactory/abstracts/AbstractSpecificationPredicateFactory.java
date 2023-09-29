package juliomesquita.projectcore.persistence.common.factory.specification.predicatefactory.abstracts;

import com.google.common.base.Splitter;
import juliomesquita.projectcore.persistence.common.factory.specification.interfaces.SpringSpecificationPredicateFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.List;

public abstract class AbstractSpecificationPredicateFactory<T>
    implements SpringSpecificationPredicateFactory<T> {

    @Value("${api.query.value.delimiter:\\|}")
    private String valueDelimiter;

    protected List<String> splitValue(String value) {
        if (StringUtils.isBlank(value)) {
            return Collections.emptyList();
        }
        return Splitter.on(this.valueDelimiter)
                .omitEmptyStrings()
                .trimResults()
                .splitToList(value);
    }
}
