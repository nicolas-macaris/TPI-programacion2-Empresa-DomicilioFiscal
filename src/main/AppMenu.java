package main;

import entities.DomicilioFiscal;
import entities.Empresa;
import service.EmpresaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AppMenu {

    private final Scanner scanner;
    private final EmpresaService empresaService;

    public AppMenu() {
        this.scanner = new Scanner(System.in);
        this.empresaService = new EmpresaService();
    }

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    crearEmpresa();
                    break;
                case "2":
                    listarEmpresas();
                    break;
                case "3":
                    buscarEmpresaPorId();
                    break;
                case "4":
                    actualizarEmpresa();
                    break;
                case "5":
                    eliminarEmpresa();
                    break;
                case "6":
                    buscarPorCuit();
                    break;
                case "0":
                    salir = true;
                    System.out.println("Saliendo de la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

            System.out.println();
        }
    }

    private void mostrarMenu() {
        System.out.println("=====================================");
        System.out.println("     MENÚ PRINCIPAL - EMPRESAS");
        System.out.println("=====================================");
        System.out.println("1) Crear empresa");
        System.out.println("2) Listar empresas");
        System.out.println("3) Buscar empresa por ID");
        System.out.println("4) Actualizar empresa");
        System.out.println("5) Eliminar empresa (baja lógica)");
        System.out.println("6) Buscar empresa por CUIT");
        System.out.println("0) Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void crearEmpresa() {
        try {
            System.out.println("--- Crear nueva empresa ---");

            System.out.print("Razón social: ");
            String razonSocial = scanner.nextLine().trim();

            System.out.print("CUIT: ");
            String cuit = scanner.nextLine().trim();

            System.out.print("Actividad principal: ");
            String actividad = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            Empresa empresa = new Empresa();
            empresa.setEliminado(false);
            empresa.setRazonSocial(razonSocial);
            empresa.setCuit(cuit);
            empresa.setActividadPrincipal(actividad);
            empresa.setEmail(email);

            System.out.print("¿Desea cargar domicilio fiscal? (S/N): ");
            String resp = scanner.nextLine().trim().toUpperCase();

            if (resp.equals("S")) {
                DomicilioFiscal df = crearDomicilioPorConsola();
                empresa.setDomicilioFiscal(df);
            }

            empresaService.insertar(empresa);

            System.out.println("Empresa creada con éxito. ID generado: " + empresa.getId());

        } catch (Exception e) {
            System.out.println("Error al crear empresa: " + e.getMessage());
        }
    }

    private DomicilioFiscal crearDomicilioPorConsola() {
        System.out.println("--- Datos de domicilio fiscal ---");

        System.out.print("Calle: ");
        String calle = scanner.nextLine().trim();

        System.out.print("Número (ENTER para dejar vacío): ");
        String numeroStr = scanner.nextLine().trim();
        Integer numero = null;
        if (!numeroStr.isBlank()) {
            try {
                numero = Integer.parseInt(numeroStr);
            } catch (NumberFormatException e) {
                System.out.println("Número inválido, se dejará en null.");
            }
        }

        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine().trim();

        System.out.print("Provincia: ");
        String provincia = scanner.nextLine().trim();

        System.out.print("Código postal: ");
        String cp = scanner.nextLine().trim();

        System.out.print("País: ");
        String pais = scanner.nextLine().trim();

        DomicilioFiscal df = new DomicilioFiscal();
        df.setEliminado(false);
        df.setCalle(calle);
        df.setNumero(numero);
        df.setCiudad(ciudad);
        df.setProvincia(provincia);
        df.setCodigoPostal(cp);
        df.setPais(pais);

        return df;
    }

    private void listarEmpresas() {
        try {
            System.out.println("--- Listado de empresas ---");
            List<Empresa> empresas = empresaService.getAll();

            if (empresas.isEmpty()) {
                System.out.println("No hay empresas cargadas.");
                return;
            }

            for (Empresa e : empresas) {
                System.out.println("---------------------------------");
                System.out.println("ID: " + e.getId());
                System.out.println("Razón social: " + e.getRazonSocial());
                System.out.println("CUIT: " + e.getCuit());
                System.out.println("Actividad: " + e.getActividadPrincipal());
                System.out.println("Email: " + e.getEmail());
                if (e.getDomicilioFiscal() != null) {
                    DomicilioFiscal df = e.getDomicilioFiscal();
                    System.out.println("Domicilio: " + df.getCalle() + " "
                            + (df.getNumero() != null ? df.getNumero() : "")
                            + ", " + df.getCiudad() + ", " + df.getProvincia()
                            + " (" + df.getCodigoPostal() + ") " + df.getPais());
                } else {
                    System.out.println("Domicilio fiscal: [no cargado]");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar empresas: " + e.getMessage());
        }
    }

    private void buscarEmpresaPorId() {
        try {
            System.out.print("Ingrese ID de la empresa: ");
            String idStr = scanner.nextLine().trim();
            Long id = Long.parseLong(idStr);

            Empresa e = empresaService.getById(id);
            if (e == null) {
                System.out.println("No se encontró empresa con ID " + id);
                return;
            }

            mostrarDetalleEmpresa(e);

        } catch (NumberFormatException ex) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Error al buscar empresa: " + e.getMessage());
        }
    }

    private void mostrarDetalleEmpresa(Empresa e) {
        System.out.println("----- Detalle de empresa -----");
        System.out.println("ID: " + e.getId());
        System.out.println("Razón social: " + e.getRazonSocial());
        System.out.println("CUIT: " + e.getCuit());
        System.out.println("Actividad: " + e.getActividadPrincipal());
        System.out.println("Email: " + e.getEmail());
        if (e.isEliminado()) {
            System.out.println("Estado: ELIMINADA (baja lógica)");
        } else {
            System.out.println("Estado: ACTIVA");
        }

        DomicilioFiscal df = e.getDomicilioFiscal();
        if (df != null && !df.isEliminado()) {
            System.out.println("Domicilio fiscal:");
            System.out.println("  Calle: " + df.getCalle());
            System.out.println("  Número: " + (df.getNumero() != null ? df.getNumero() : ""));
            System.out.println("  Ciudad: " + df.getCiudad());
            System.out.println("  Provincia: " + df.getProvincia());
            System.out.println("  Código postal: " + df.getCodigoPostal());
            System.out.println("  País: " + df.getPais());
        } else {
            System.out.println("Domicilio fiscal: [no cargado o eliminado]");
        }
    }

    private void actualizarEmpresa() {
        try {
            System.out.print("Ingrese ID de la empresa a actualizar: ");
            String idStr = scanner.nextLine().trim();
            Long id = Long.parseLong(idStr);

            Empresa existente = empresaService.getById(id);
            if (existente == null) {
                System.out.println("No se encontró empresa con ID " + id);
                return;
            }

            System.out.println("Deje vacío para mantener el valor actual.");
            System.out.println("Razón social actual: " + existente.getRazonSocial());
            System.out.print("Nueva razón social: ");
            String razon = scanner.nextLine().trim();
            if (!razon.isBlank()) {
                existente.setRazonSocial(razon);
            }

            System.out.println("CUIT actual: " + existente.getCuit());
            System.out.print("Nuevo CUIT: ");
            String cuit = scanner.nextLine().trim();
            if (!cuit.isBlank()) {
                existente.setCuit(cuit);
            }

            System.out.println("Actividad actual: " + existente.getActividadPrincipal());
            System.out.print("Nueva actividad: ");
            String act = scanner.nextLine().trim();
            if (!act.isBlank()) {
                existente.setActividadPrincipal(act);
            }

            System.out.println("Email actual: " + existente.getEmail());
            System.out.print("Nuevo email: ");
            String email = scanner.nextLine().trim();
            if (!email.isBlank()) {
                existente.setEmail(email);
            }

            System.out.print("¿Desea actualizar el domicilio fiscal? (S/N): ");
            String resp = scanner.nextLine().trim().toUpperCase();
            if (resp.equals("S")) {
                DomicilioFiscal df = existente.getDomicilioFiscal();
                if (df == null) {
                    df = crearDomicilioPorConsola();
                    existente.setDomicilioFiscal(df);
                } else {
                    actualizarDomicilioPorConsola(df);
                }
            }

            empresaService.actualizar(existente);
            System.out.println("Empresa actualizada con éxito.");

        } catch (NumberFormatException ex) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Error al actualizar empresa: " + e.getMessage());
        }
    }

    private void actualizarDomicilioPorConsola(DomicilioFiscal df) {
        System.out.println("--- Actualizar domicilio fiscal ---");
        System.out.println("Deje vacío para mantener el valor actual.");

        System.out.println("Calle actual: " + df.getCalle());
        System.out.print("Nueva calle: ");
        String calle = scanner.nextLine().trim();
        if (!calle.isBlank()) {
            df.setCalle(calle);
        }

        System.out.println("Número actual: " + (df.getNumero() != null ? df.getNumero() : ""));
        System.out.print("Nuevo número: ");
        String numeroStr = scanner.nextLine().trim();
        if (!numeroStr.isBlank()) {
            try {
                df.setNumero(Integer.parseInt(numeroStr));
            } catch (NumberFormatException e) {
                System.out.println("Número inválido, se mantiene el actual.");
            }
        }

        System.out.println("Ciudad actual: " + df.getCiudad());
        System.out.print("Nueva ciudad: ");
        String ciudad = scanner.nextLine().trim();
        if (!ciudad.isBlank()) {
            df.setCiudad(ciudad);
        }

        System.out.println("Provincia actual: " + df.getProvincia());
        System.out.print("Nueva provincia: ");
        String provincia = scanner.nextLine().trim();
        if (!provincia.isBlank()) {
            df.setProvincia(provincia);
        }

        System.out.println("CP actual: " + df.getCodigoPostal());
        System.out.print("Nuevo CP: ");
        String cp = scanner.nextLine().trim();
        if (!cp.isBlank()) {
            df.setCodigoPostal(cp);
        }

        System.out.println("País actual: " + df.getPais());
        System.out.print("Nuevo país: ");
        String pais = scanner.nextLine().trim();
        if (!pais.isBlank()) {
            df.setPais(pais);
        }
    }

    private void eliminarEmpresa() {
        try {
            System.out.print("Ingrese ID de la empresa a eliminar: ");
            String idStr = scanner.nextLine().trim();
            Long id = Long.parseLong(idStr);

            empresaService.eliminar(id);
            System.out.println("Empresa eliminada (baja lógica) correctamente.");

        } catch (NumberFormatException ex) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Error al eliminar empresa: " + e.getMessage());
        }
    }

    private void buscarPorCuit() {
        try {
            System.out.print("Ingrese CUIT de la empresa: ");
            String cuit = scanner.nextLine().trim();

            Empresa e = empresaService.getByCuit(cuit);
            if (e == null) {
                System.out.println("No se encontró empresa con CUIT " + cuit);
                return;
            }

            mostrarDetalleEmpresa(e);

        } catch (Exception e) {
            System.out.println("Error al buscar empresa por CUIT: " + e.getMessage());
        }
    }

}
