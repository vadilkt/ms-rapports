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
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        final String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

}
