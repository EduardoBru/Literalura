package com.Challenge.Literalura.Service;

public interface ConversorDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
