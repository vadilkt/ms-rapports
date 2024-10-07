package tech.chillo.rapports;

import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.chillo.rapports.entities.Bordereau;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@AllArgsConstructor
@RestController
@RequestMapping("bordereaux")
public class BordereauController {

    private final BordereauService service;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Bordereau> search() {
        return this.service.search();
    }

    @GetMapping(path = "{id}")
    public Bordereau read(@PathVariable final int id) {

        return this.service.read(id);
    }

    @GetMapping(path = "{id}/generate")
    public ResponseEntity<byte[]> exportReport(@PathVariable final int id, @RequestParam(name = "format", defaultValue = "pdf") final String format) throws JRException, IOException {

        final byte[] content = this.service.exportReport(id, format);
        final HttpHeaders headers = new HttpHeaders();

        switch (format.toLowerCase()){
            case "pdf":
                headers.setContentType(MediaType.APPLICATION_PDF);
                break;
            case "word":
                headers.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
                break;
            case "excel":
                headers.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                break;
            case "html":
                headers.setContentType(MediaType.TEXT_HTML);
                break;
            case "xml":
                headers.setContentType(MediaType.APPLICATION_XML);
                break;
            default:
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                break;
        }

        final String extension = switch (format.toLowerCase()){
            case "pdf"->"pdf";
            case "word"->"docx";
            case "excel"->"xlsx";
            case "html"->"html";
            case "xml"->"xml";
            default -> "bin";
        };
        final String filename ="bordereau-"+id+"."+extension;
        headers.setContentDispositionFormData("inline", filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

}
