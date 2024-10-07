package tech.chillo.rapports;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.*;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import tech.chillo.rapports.entities.Bordereau;
import tech.chillo.rapports.entities.Courrier;
import tech.chillo.rapports.entities.PieceJointe;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@AllArgsConstructor
@Service
public class BordereauService {

    private final BordereauRepository repository;
    private final PJRepository pjRepository;


    public List<Bordereau> search() {
        return this.repository.findAll();
    }

    public byte[] exportReport(final int id, final String format) throws IOException, JRException {

        final Bordereau bordereau = this.repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Aucun bordereau ne correspond à %s", id)));
        List<Courrier> courriers = bordereau.getCourriers();
        List<Object[]> pj = pjRepository.print(id);

        List<RapportDTO> rapportDTOS = pj
                .stream()
                .map(fwp -> new RapportDTO((PieceJointe) fwp[0], (Courrier) fwp[1],(Bordereau) fwp[2]))
                .toList();
        //load file and compile it
        final File file = ResourceUtils.getFile("classpath:liste-courriers.jrxml");
        final JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rapportDTOS);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("provenance", bordereau.getProvenance());
        parameters.put("destination", bordereau.getDestination());
        parameters.put("numero", bordereau.getNumero());
        parameters.put("emission", bordereau.getEmission());
        parameters.put("nbre_courrier", courriers.size());
        final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (format.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, "classpath:liste-courriers.html");
        }
        if (format.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, "classpath:liste-courriers.pdf");
        }
        if (format.equalsIgnoreCase("xml")) {
            JasperExportManager.exportReportToXmlFile(jasperPrint, "classpath:liste-courriers.xml", true);
        }
        if(format.equalsIgnoreCase("word")){
            File tempFile = File.createTempFile("Liste-Courriers",".docx");
            JRDocxExporter docxExporter = new JRDocxExporter();
            docxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            docxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(tempFile));
            SimpleDocxReportConfiguration docxConfiguration = new SimpleDocxReportConfiguration();
            docxConfiguration.setFlexibleRowHeight(true);
            docxExporter.setConfiguration(docxConfiguration);
            docxExporter.exportReport();
            return FileUtils.readFileToByteArray(tempFile);
        }
        if(format.equalsIgnoreCase("excel")){
            File tempFile = File.createTempFile("Liste-Courriers", ".xlsx");
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true);
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            exporter.setConfiguration(configuration);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(tempFile));
            exporter.exportReport();
            return FileUtils.readFileToByteArray(tempFile);
        }

        final File f = new File("classpath:liste-courriers.pdf");

        return FileUtils.readFileToByteArray(f);
    }

    public Bordereau read(final int id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Aucun bordereau ne correspond à %s", id)));
    }

}
