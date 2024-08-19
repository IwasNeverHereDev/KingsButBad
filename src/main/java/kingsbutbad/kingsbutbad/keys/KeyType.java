package kingsbutbad.kingsbutbad.keys;

import org.bukkit.persistence.PersistentDataType;

import java.util.function.Function;

public record KeyType<Z>(PersistentDataType<?, Z> dataType, Function<String, Z> parser) {}
