package task2reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class CacheInvHadler implements InvocationHandler {
    private Object obj;
    CacheInvHadler(Object obj){this.obj = obj;}

    private Boolean cachedFlag = false;
    private HashMap<Method, Object> methodVals = new HashMap<>();

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method m = obj.getClass().getMethod(method.getName(), method.getParameterTypes());
        if (m.getAnnotationsByType(Mutator.class).length > 0) {
            cachedFlag = false;
        }
        if (m.getAnnotationsByType(Cache.class).length > 0) {
            if (!cachedFlag){
                methodVals.put(m, method.invoke(obj, args));
                cachedFlag = true;
            }
            return methodVals.get(m);
        }
        else {
            return method.invoke(obj, args);
        }
    }
}
