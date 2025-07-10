package org.app.repository;

import java.util.Optional;

public interface NameableRepository <T, ID> extends GenericRepository <T, ID> {
    Optional<T> getByName(String name);
}
