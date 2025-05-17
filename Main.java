import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String rutaCarpeta = "/tmp/directori";
        System.out.println("   ___  ____  ____  _____ _   _    _    ____   ___  ____    ____  _____      _    ____   ____ _   _ _____     _____  ____  \n" +
                "  / _ \\|  _ \\|  _ \\| ____| \\ | |  / \\  |  _ \\ / _ \\|  _ \\  |  _ \\| ____|    / \\  |  _ \\ / ___| | | |_ _\\ \\   / / _ \\/ ___| \n" +
                " | | | | |_) | | | |  _| |  \\| | / _ \\ | | | | | | | |_) | | | | |  _|     / _ \\ | |_) | |   | |_| || | \\ \\ / / | | \\___ \\ \n" +
                " | |_| |  _ <| |_| | |___| |\\  |/ ___ \\| |_| | |_| |  _ <  | |_| | |___   / ___ \\|  _ <| |___|  _  || |  \\ V /| |_| |___) |\n" +
                "  \\___/|_| \\_\\____/|_____|_| \\_/_/   \\_\\____/ \\___/|_| \\_\\ |____/|_____| /_/   \\_\\_| \\_\\\\____|_| |_|___|  \\_/  \\___/|____/ \n" +
                "                                                                                                                           ");

        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║           MENÚ PRINCIPAL           ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║  1. Ordenar archivos               ║");
        System.out.println("║  2. Eliminar los originales        ║");
        System.out.println("║  3. Salir                          ║");
        System.out.println("╚════════════════════════════════════╝");

        System.out.println("Que quieres hacer?:");
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                for (Path cosa : Files.walk(Path.of(rutaCarpeta)).toList()) {
                    if (Files.isRegularFile(cosa)){
                        try {
                            FileTime fechahora = Files.getLastModifiedTime(cosa);

                            String anyo = fechahora.toString().substring(0,4);
                            String mes = fechahora.toString().substring(5,7);
                            String dia = fechahora.toString().substring(8,10);

                            System.out.println(anyo + "/" + mes + "/" + dia);

                            String fecha = (anyo + "/" + mes + "/" + dia);

                            Path ruta = Path.of(fecha);
                            if (!Files.exists(ruta)){
                                Files.createDirectories(ruta);
                            }
                            Files.copy(cosa, Path.of(fecha + cosa.getFileName()));
                            String nombreArchivo = cosa.getFileName().toString();
                            System.out.println("Archivo " + nombreArchivo + " copiado en " + ruta);
                        } catch (Exception e) {
                            if (Files.exists(cosa)){
                                System.out.println("El archivo " + cosa + " ya existe en ese directorio");
                            } else {
                                System.out.println("error");
                            }
                        }
                    }
                }
                break;
            case 2:
                //pasa por todas las carpetas de las copiasdas y las normales para ver si estan iguales, si lo estan las borra
                for (Path cosa : Files.walk(Path.of(rutaCarpeta)).toList()) {
                    if (Files.isRegularFile(cosa)){
                        try {
                            FileTime fechahora = Files.getLastModifiedTime(cosa);

                            String anyo = fechahora.toString().substring(0,4);
                            String mes = fechahora.toString().substring(5,7);
                            String dia = fechahora.toString().substring(8,10);

                            System.out.println(anyo + "/" + mes + "/" + dia);

                            String fecha = (anyo + "/" + mes + "/" + dia);
                            Path ruta = Path.of(fecha);

                            for (Path archivosOrdenado : Files.walk(ruta).toList()) {
                                if (Files.isRegularFile(archivosOrdenado)){
                                try {
                                    if (archivosOrdenado.getFileName().equals(cosa.getFileName()) && Files.size(archivosOrdenado) == Files.size(cosa)){
                                            Files.delete(cosa); //esto no va, preguntar porque
                                            System.out.println("Archivo " + cosa + " eliminado");
                                    }
                                } catch (Exception e) {
                                    System.out.println("error");
                                    }
                                }
                            }
                        }
                        catch (Exception e){
                            System.out.println("error");
                        }
                    }
                }
                break;
            case 3:
                System.out.println("Saliendo");
                break;
            default:
                System.out.println("Opción no válida");
        }
    }
}