package ies.torredelrey.jfma.appgestionparking.PDF;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import ies.torredelrey.jfma.appgestionparking.util.Rutas;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FacturaPDF {

    public static void generarFactura(String archivo, int idFactura, String nombreCliente,  LocalDateTime fecha,float total,String matricula) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(archivo));
            document.open();

            // Cargar logo desde resources
            InputStream is = FacturaPDF.class.getResourceAsStream(Rutas.IMAGENHOTEL);
            if (is != null) {
                Image img = Image.getInstance(javax.imageio.ImageIO.read(is), null);
                img.scaleToFit(300, 300);
                img.setAlignment(Image.ALIGN_CENTER);
                document.add(img);
            } else {
                System.out.println("Imagen no encontrada en: " + Rutas.IMAGENHOTEL);
            }

            document.add(Chunk.NEWLINE);

            // Título de la factura
            Paragraph titulo = new Paragraph("FACTURA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(Chunk.NEWLINE);

            // Formatear la fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
            String fechaFormateada = fecha.format(formatter);

            // Tabla con datos de factura
            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(20f);
            tabla.setSpacingAfter(20f);
            tabla.setWidths(new float[]{1, 2});

            tabla.addCell(getCelda("Factura: ", true));
            tabla.addCell(getCelda(String.valueOf("#"+idFactura), false));
            tabla.addCell(getCelda("Cliente:", true));
            tabla.addCell(getCelda(nombreCliente, false));
            tabla.addCell(getCelda("Matrícula:", true));
            tabla.addCell(getCelda(matricula, false));
            tabla.addCell(getCelda("Fecha de pago:", true));
            tabla.addCell(getCelda(fechaFormateada, false));

            document.add(tabla);

            document.add(Chunk.NEWLINE);


            Paragraph totalTexto = new Paragraph("TOTAL: " + String.format("%.2f", total) + " €",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLUE));
            totalTexto.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalTexto);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static PdfPCell getCelda(String texto, boolean negrita) {
        Font fuente = negrita ?
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12) :
                FontFactory.getFont(FontFactory.HELVETICA, 12);
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBorder(Rectangle.NO_BORDER);
        return celda;
    }

}
