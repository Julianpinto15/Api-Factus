package factusBackend.presentation.controllers;

import factusBackend.domain.model.NumberingRange;
import factusBackend.application.services.NumberingRangeService;
import factusBackend.presentation.dtos.NumberingRangeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/numbering-ranges")
public class NumberingRangeController {

    private final NumberingRangeService numberingRangeService;

    public NumberingRangeController(NumberingRangeService numberingRangeService) {
        this.numberingRangeService = numberingRangeService;
    }

    @GetMapping
    public Flux<NumberingRange> getAllRanges() {
        return numberingRangeService.getAllNumberingRanges();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<NumberingRange>> getRangeById(@PathVariable String id) {
        return numberingRangeService.getNumberingRangeById(id)
                .map(range -> ResponseEntity.ok(range))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public Mono<ResponseEntity<NumberingRange>> getActiveRange() {
        return numberingRangeService.getActiveNumberingRange()
                .map(range -> ResponseEntity.ok(range))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<NumberingRange>> createRange(@RequestBody NumberingRangeDTO rangeDTO) {
        return numberingRangeService.createNumberingRange(convertToNumberingRange(rangeDTO))
                .map(createdRange -> new ResponseEntity<>(createdRange, HttpStatus.CREATED));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<NumberingRange>> updateRange(@PathVariable String id, @RequestBody NumberingRangeDTO rangeDTO) {
        return numberingRangeService.updateNumberingRange(id, convertToNumberingRange(rangeDTO))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteRange(@PathVariable String id) {
        return numberingRangeService.deleteNumberingRange(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private NumberingRange convertToNumberingRange(NumberingRangeDTO dto) {
        NumberingRange range = new NumberingRange();
        range.setPrefix(dto.getPrefix());
        range.setFromNumber(dto.getFrom());
        range.setToNumber(dto.getTo());
        range.setCurrentNumber(dto.getCurrent());
        range.setActive(dto.getActive());
        // Add other fields if necessary
        return range;
    }
}