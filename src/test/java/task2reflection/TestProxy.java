package task2reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

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
    @Test
    @DisplayName("Set-теры работают корректно")
    public void tstGetSet() {
        Fraction fr = new Fraction(1,1);
        Fractionable num = (Fractionable) Utils.cache(fr);
        fr.setNum(15);
        fr.setDenum(8);
        num.setNum(15);
        num.setDenum(8);
        double d = (double) 15 / 8;
        Assertions.assertEquals(fr.doubleValue(), d);
        Assertions.assertEquals(num.doubleValue(), d);
        // второй раз у Proxy будет использовано кэшированное значение
        Assertions.assertEquals(fr.doubleValue(), num.doubleValue());
    }

    @Test
    @DisplayName("Проверка кэширования")
    public void tstCache() throws NoSuchFieldException, IllegalAccessException {
        Fraction fr = new Fraction(57,1);
        Fractionable num = (Fractionable) Utils.cache(fr);
        // получаем Handler нашего Proxy
        CacheInvHadler numProxy = (CacheInvHadler) Proxy.getInvocationHandler(num);
        // анализируем поле флага кэширования
        Class cls = numProxy.getClass();
        Field f = cls.getDeclaredField("cachedFlag");
        f.setAccessible(true);
        // ----------------
        num.setDenum(9);
        // д.б. false
        Assertions.assertEquals(f.get(numProxy), false);
        num.doubleValue();
        // должно стать true
        Assertions.assertEquals(f.get(numProxy), true);
        num.doubleValue();
        // остаётся true
        Assertions.assertEquals(f.get(numProxy), true);
        num.setNum(1);
        // должно сброситься в false
        Assertions.assertEquals(f.get(numProxy), false);
    }
}
