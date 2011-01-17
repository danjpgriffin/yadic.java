package com.googlecode.yadic.resolvers;

import com.googlecode.totallylazy.*;
import com.googlecode.yadic.ContainerException;
import com.googlecode.yadic.Resolver;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.googlecode.totallylazy.Arrays.exists;
import static com.googlecode.totallylazy.Callables.cast;
import static com.googlecode.totallylazy.Methods.genericParameterTypes;
import static com.googlecode.totallylazy.Methods.genericReturnType;
import static com.googlecode.totallylazy.Methods.modifier;
import static com.googlecode.totallylazy.Option.none;
import static com.googlecode.totallylazy.Option.some;
import static com.googlecode.totallylazy.Predicates.not;
import static com.googlecode.totallylazy.Predicates.where;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.yadic.generics.TypeConverter.convertParametersToInstances;
import static com.googlecode.yadic.generics.Types.classOf;
import static com.googlecode.yadic.generics.Types.matches;
import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.STATIC;

public class StaticMethodResolver<T> implements Resolver<T> {
    private final Resolver<?> resolver;

    public StaticMethodResolver(Resolver<?> resolver) {
        this.resolver = resolver;
    }

    public T resolve(Type type) throws Exception {
        Class<T> concrete = classOf(type);
        Sequence<Method> methods = sequence(concrete.getMethods()).
                filter(modifier(PUBLIC).and(modifier(STATIC)).
                        and(where(genericReturnType(), matches(type)).
                                and(where(genericParameterTypes(), not(exists(matches(type)))))));
        if (methods.isEmpty()) {
            throw new ContainerException(concrete.getName() + " does not have any public static methods that return " + type);
        }
        final List<Exception> exceptions = new ArrayList<Exception>();
        return methods.tryPick(firstSatisfiableMethod(exceptions, type)).map(cast(concrete)).
                getOrElse(Callables.<T>callThrows(new ContainerException(concrete.getName() + " does not have a satisfiable public static method", exceptions)));
    }

    private Callable1<Method, Option<Object>> firstSatisfiableMethod(final List<Exception> exceptions, final Type type) {
        return new Callable1<Method, Option<Object>>() {
            public Option<Object> call(Method method) throws Exception {
                try {
                    Object[] instances = convertParametersToInstances(resolver, type, sequence(method.getGenericParameterTypes()));
                    return some(method.invoke(null, instances));
                } catch (Exception e) {
                    exceptions.add(e);
                    return none();
                }
            }
        };
    }
}
