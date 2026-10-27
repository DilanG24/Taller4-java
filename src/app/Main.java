package app;

import modelo.*;   // GUIADO: trae Titular, CuentaAhorro y Banco desde el paquete modelo

/*
 * EJERCICIO 4 (EJ4) - Programa principal                             [50 pts de salida + 3 de estructura]
 *
 * Main esta en el paquete "app"; las clases estan en "modelo". Por eso Main solo puede usar
 * lo que es PUBLIC (no puede usar validarMonto ni calcularComision directamente).
 *
 * Los datos NO se escriben a mano: salen de Escenario con la semilla.
 * Ejemplos:  Escenario.nombre(semilla, 1)   Escenario.edad(semilla, 2)   Escenario.montoOp(semilla, 3)
 * Lea los comentarios de Escenario.java para ver que devuelve cada metodo.
 *
 * Los formatos EXACTOS de salida estan en el documento del taller (Parte 4: Main).
 */
public class Main {

    public static void main(String[] args) {
        int semilla = Contexto.semilla(args);
        Contexto.imprimirCabecera(semilla);   // GUIADO: imprime las 2 primeras lineas (no las cambie)

       
        // TODO(EJ4) PARTE 1: crear los 2 titulares, imprimir, intentar cambiar la edad, contar jubilados
       
        System.out.println("== PARTE 1: TITULARES ==");
Titular t1 = new Titular(Escenario.nombre(semilla, 1), Escenario.apellido(semilla, 1), Escenario.edad(semilla, 1));
Titular t2 = new Titular(Escenario.nombre(semilla, 2), Escenario.apellido(semilla, 2), Escenario.edad(semilla, 2));

System.out.println("Titular 1: " + t1.getNombreCompleto() + " | edad=" + t1.getEdad()
        + " | jubilado=" + (t1.esJubilado() ? "SI" : "NO"));
System.out.println("Titular 2: " + t2.getNombreCompleto() + " | edad=" + t2.getEdad()
        + " | jubilado=" + (t2.esJubilado() ? "SI" : "NO"));

int edadNueva1 = Escenario.edadNueva(semilla, 1);
boolean ok1 = t1.setEdad(edadNueva1);
System.out.println("Cambio de edad titular 1 a " + edadNueva1 + ": "
        + (ok1 ? "ACEPTADO" : "RECHAZADO") + " | edad=" + t1.getEdad());

int edadNueva2 = Escenario.edadNueva(semilla, 2);
boolean ok2 = t2.setEdad(edadNueva2);
System.out.println("Cambio de edad titular 2 a " + edadNueva2 + ": "
        + (ok2 ? "ACEPTADO" : "RECHAZADO") + " | edad=" + t2.getEdad());

int jubilados = 0;
if (t1.esJubilado()) {
    jubilados++;
}
if (t2.esJubilado()) {
    jubilados++;
}
System.out.println("Jubilados tras el cambio: " + jubilados);
       
        // TODO(EJ4) PARTE 2: crear las 2 cuentas (una con cada constructor) e imprimir su estado()
       
        System.out.println("== PARTE 2: CUENTAS ==");
CuentaAhorro cuenta1 = new CuentaAhorro(Escenario.numeroCuenta(semilla, 1), t1,
        Escenario.saldoInicial(semilla, 1));
CuentaAhorro cuenta2 = new CuentaAhorro(Escenario.numeroCuenta(semilla, 2), t2);
cuenta2.depositar(Escenario.saldoInicial(semilla, 2));

System.out.println(cuenta1.estado());
System.out.println(cuenta2.estado());
     
        // TODO(EJ4) PARTE 3: ciclo for de 6 operaciones (deposito/retiro) sobre la cuenta indicada
       
        System.out.println("== PARTE 3: OPERACIONES ==");
for (int i = 1; i <= 6; i++) {
    CuentaAhorro cuenta = (Escenario.cuentaOp(semilla, i) == 1) ? cuenta1 : cuenta2;
    int tipo = Escenario.tipoOp(semilla, i);
    double monto = Escenario.montoOp(semilla, i);

    boolean ok;
    String nombreTipo;
    if (tipo == 1) {
        nombreTipo = "DEPOSITO";
        ok = cuenta.depositar(monto);
    } else {
        nombreTipo = "RETIRO";
        ok = cuenta.retirar(monto);
    }
    System.out.println("Op " + i + " " + nombreTipo + " " + Contexto.dinero(monto)
            + " cuenta " + cuenta.getNumero() + ": " + (ok ? "OK" : "RECHAZADO")
            + " | saldo=" + Contexto.dinero(cuenta.getSaldo()));
}
     
        // TODO(EJ4) PARTE 4: transferencia entre las cuentas
        System.out.println("== PARTE 4: TRANSFERENCIA ==");
CuentaAhorro origen;
CuentaAhorro destino;
if (Escenario.cuentaOrigen(semilla) == 1) {
    origen = cuenta1;
    destino = cuenta2;
} else {
    origen = cuenta2;
    destino = cuenta1;
}
double montoTransf = Escenario.montoTransferencia(semilla);
boolean okTransf = origen.transferir(destino, montoTransf);
System.out.println("Transferencia de " + origen.getNumero() + " a " + destino.getNumero()
        + " por " + Contexto.dinero(montoTransf) + ": " + (okTransf ? "OK" : "RECHAZADA"));
System.out.println("Origen: saldo=" + Contexto.dinero(origen.getSaldo())
        + " | Destino: saldo=" + Contexto.dinero(destino.getSaldo()));
      
        // TODO(EJ4) PARTE 5: crear el Banco e imprimir el resumen
      
        System.out.println("== PARTE 5: BANCO ==");
Banco banco = new Banco(cuenta1, cuenta2);
CuentaAhorro mayor = banco.cuentaMayorSaldo();
double montoComision = Escenario.montoComision(semilla);

System.out.println("Total depositos: " + Contexto.dinero(banco.totalDepositos()));
System.out.println("Cuenta con mayor saldo: " + mayor.getNumero());
System.out.println("Comision estimada cuenta " + mayor.getNumero() + " por "
        + Contexto.dinero(montoComision) + ": "
        + Contexto.dinero(banco.comisionEstimada(mayor, montoComision)));
System.out.println("Movimientos totales: " + banco.totalMovimientos());
    }
}
