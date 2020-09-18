package academy.tich;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.*;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class Thumbnail {

    public long lengt;
    public final String mimeType = "image/png";
    public ByteArrayInputStream inputStream;

    public Thumbnail() {
        this.lengt = 0;
    }

    public void setImageFromPdfFile(InputStream pdfFile, int pageIndex) throws IOException {
        System.out.println(pdfFile.available());
        PDDocument document = PDDocument.load(pdfFile);
        System.out.println("PDFDocument loaded");
        BufferedImage renderedImage = new PDFRenderer(document).renderImage(pageIndex);
        System.out.println("Page image got");
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(renderedImage, "png", arrayOutputStream);
        System.out.println("Image writed");
        this.lengt = arrayOutputStream.size();
        this.inputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
        document.close();
        arrayOutputStream.close();
    }

    @Override
    public String toString() {
        return "Thumbnail [inputStream=" + (inputStream.available()) + ", lengt=" + lengt + ", mimeType=" + mimeType
                + "]";
    }

}
