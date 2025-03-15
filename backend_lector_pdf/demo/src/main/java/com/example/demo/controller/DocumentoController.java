package com.example.demo.controller;

import com.example.demo.model.Documento;
import com.example.demo.repository.DocumentoRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DocumentoController {

    @Autowired
    private DocumentoRepository documentoRepository;

    @PostMapping("/upload")
    public Map<String, String> uploadPDF(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            // Leer el PDF
            PDDocument document = PDDocument.load(file.getInputStream());
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();

            // Procesar el texto (aquí puedes agregar tu lógica)
            String campo1 = "Valor1"; // Ejemplo
            String campo2 = "Valor2"; // Ejemplo

            // Guardar en la base de datos
            Documento documento = new Documento();
            documento.setCampo1(campo1);
            documento.setCampo2(campo2);
            documentoRepository.save(documento);

            response.put("mensaje", "Archivo procesado y guardado correctamente");
        } catch (IOException e) {
            response.put("error", "Error al procesar el archivo");
        }
        return response;
    }

    @GetMapping("/documentos")
    public List<Documento> getDocumentos() {
        return documentoRepository.findAll();
    }
}
