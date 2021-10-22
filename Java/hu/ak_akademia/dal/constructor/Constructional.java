package hu.ak_akademia.dal.constructor;

import java.util.Set;

public interface Constructional<T, E> {

	Set<T> construct(E e);
}
