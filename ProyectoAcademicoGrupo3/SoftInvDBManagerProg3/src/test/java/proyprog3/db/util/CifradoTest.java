package proyprog3.db.util;

import pe.edu.pucp.softinv.db.util.Cifrado;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author andres
 */
public class CifradoTest {
    
    public CifradoTest() {
    }

    /**
     * Test of cifrarMD5 method, of class Cifrado.
     */
    @Test
    public void testCifrarMD5() {
        System.out.println("cifrarMD5");
        String texto = "prog3_bd_2025_1";        
        String resultado = Cifrado.cifrarMD5(texto);
        String resultado_esperado = "ZJ9HYDoTdUciG7GjgRFi2A==";                                        
        assertEquals(resultado_esperado, resultado);        
    }

    /**
     * Test of descifrarMD5 method, of class Cifrado.
     */
    @Test
    public void testDescifrarMD5() {
        System.out.println("descifrarMD5");
        String textoEncriptado = "ZJ9HYDoTdUciG7GjgRFi2A==";
        String resultado_esperado = "prog3_bd_2025_1";
        String resultado = Cifrado.descifrarMD5(textoEncriptado);
        System.out.println(resultado);
        assertEquals(resultado_esperado, resultado);
    }
    
}