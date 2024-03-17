package task2reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestProxy {
    @Test
    @DisplayName("Объект и Proxy считают одинаково")
    public void objEq() {
        Fraction fr = new Fraction(17,3);
        Fractionable num = (Fractionable) Utils.cache(fr);
        Assertions.assertEquals(fr.doubleValue(), num.doubleValue());
        // второй раз у Proxy будет использовано кэшированное значение
        Assertions.assertEquals(fr.doubleValue(), num.doubleValue());
    }
}
