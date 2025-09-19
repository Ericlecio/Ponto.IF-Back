package br.edu.ifpe.pontoif.pontoif.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
// arquivo de exemplo
@SpringBootTest
class CalcUnitTest {
    @Test
    void calcularSoma() {
        var result = 6 + 6;
        assertEquals(12, result);
    }
}
