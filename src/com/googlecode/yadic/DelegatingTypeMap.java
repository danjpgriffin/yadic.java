package com.googlecode.yadic;

//import com.googlecode.totallylazy.Option;

import java.lang.reflect.Type;
import java.util.Iterator;

public abstract class DelegatingTypeMap implements TypeMap{
//    private final TypeMap map;
//
//    public DelegatingTypeMap(TypeMap map) {
//        this.map = map;
//    }

//    protected abstract TypeMap self();
//
    public TypeMap addType(Type type, Resolver<?> resolver) {
        throw new RuntimeException("DAN");
//        map.addType(type, resolver);
//        return self();
    }
//
    public TypeMap addType(Type type, Class<? extends Resolver> resolverClass) {
        throw new RuntimeException("DAN");
    }
//
    public TypeMap addType(Type type, Type concrete) {
        throw new RuntimeException("DAN");
    }
//
//    public <T> Resolver<T> getResolver(Type type) {
//        return map.getResolver(type);
//    }
//
    public <T> Resolver<T> remove(Type type) {
        throw new RuntimeException("DAN");
//        return map.remove(type);
    }
//
    public boolean contains(Type type) {
        throw new RuntimeException("DAN");
    }
//
    public TypeMap decorateType(Type anInterface, Type concrete) {
        throw new RuntimeException("DAN");
    }
//
    public Object resolve(Type type) throws Exception {
        throw new RuntimeException("DAN");
        //return map.resolve(type);
    }
//
//    public <T> Option<Resolver<T>> removeOption(Type type) {
//        return map.removeOption(type);
//    }
//
    public <T> T create(Type type) {
        throw new RuntimeException("DAN");
        //return map.<T>create(type);
    }

    @Override
    public Iterator<Type> iterator() {
        throw new RuntimeException("DAN");
        //return map.iterator();
    }
}
