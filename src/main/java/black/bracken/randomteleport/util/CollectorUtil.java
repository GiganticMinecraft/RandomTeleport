package black.bracken.randomteleport.util;

import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CollectorUtil {

    private CollectorUtil() {
    }

    public static <T> Collector<T, ?, Stream<T>> toReversed() {
        return Collectors.collectingAndThen(Collectors.toList(), list -> {
            Collections.reverse(list);

            return list.stream();
        });
    }

}
