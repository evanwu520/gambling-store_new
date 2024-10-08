package com.ampletec.boot.spring.utils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

public class ConvertUtils {
    private ConvertUtils() {
    }

    /**
     * This is a method to convert an object to destination object.
     * It's only for the simple object that can use
     * {@link BeanUtils#copyProperties(Object, Object)} method.
     * And the destination class should have the non-parameter constructor.
     * {@link Class#newInstance()} method is used.
     *
     * @param source           the source object
     * @param destinationClass the destination class
     * @param <S>              the source type
     * @param <D>              the destination type
     * @return destination object
     */
    public static <S, D> D convertObject(S source, Class<D> destinationClass) {
        if (source == null) {
            return null;
        }
        try {
            D destination = destinationClass.newInstance();
            BeanUtils.copyProperties(source, destination);
            return destination;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Can not instantiate the class type: {} with no-arg constructor.");
        }
    }

    /**
     * convert page with special converter
     *
     * @param source    source page
     * @param converter converter for list content of page
     * @param <S>       the source content type
     * @param <D>       the destination content type
     * @return destination page
     */
//    public static <S, D> Page<D> convertPage(Page<S> source, Function<S, D> converter) {
//        if (source == null) {
//            return null;
//        }
//        Page<D> destination = new Page<>();
//        BeanUtils.copyProperties(source, destination, "content");
//        if (source.getContent() != null) {
//            destination.setContent(source.getContent().stream().map(converter).collect(Collectors.toList()));
//        } else {
//            destination.setContent(new ArrayList<>());
//        }
//        return destination;
//    }

    /**
     * convert page with default beanUtils
     *
     * @param source source page
     * @param <S>    the source content type
     * @param <D>    the destination content type
     * @return destination page
     */
//    public static <S, D> Page<D> convertPage(Page<S> source, Class<D> destinationClass) {
//        if (source == null) {
//            return null;
//        }
//        Page<D> destination = new Page<>();
//        BeanUtils.copyProperties(source, destination, "content");
//        if (source.getContent() != null) {
//            destination.setContent(source.getContent().stream().map(s -> convertObject(s, destinationClass)).collect(Collectors.toList()));
//        }
//        return destination;
//    }

    /**
     * convert List with default beanUtils
     *
     * @param source source page
     * @param <S>    the source content type
     * @param <D>    the destination content type
     * @return destination page
     */
    public static <S, D> List<D> convertList(List<S> source, Class<D> destinationClass) {
        if (source == null) {
            return Collections.emptyList();
        }
        if (source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream().map(s -> convertObject(s, destinationClass)).collect(Collectors.toList());
    }


    /**
     * convert List with special converter
     *
     * @param source source page
     * @param <S>    the source content type
     * @param <D>    the destination content type
     * @return destination page
     */
    public static <S, D> List<D> convertList(List<S> source, Function<S, D> converter) {
        if (source == null) {
            return Collections.emptyList();
        }
        if (source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream().map(converter).collect(Collectors.toList());
    }
}
