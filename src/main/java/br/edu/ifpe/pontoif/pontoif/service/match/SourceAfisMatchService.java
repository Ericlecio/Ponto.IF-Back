package br.edu.ifpe.pontoif.pontoif.service.match;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SourceAfisMatchService {
    public Optional<Double> calculateScoreSafe(
            byte[] storedTemplate,
            byte[] sampleTemplate
    ) {
        try {
            if (storedTemplate == null || storedTemplate.length < 100) {
                return Optional.empty();
            }

            if (sampleTemplate == null || sampleTemplate.length < 100) {
                return Optional.empty();
            }

            FingerprintTemplate probe =
                    new FingerprintTemplate(sampleTemplate);

            FingerprintTemplate candidate =
                    new FingerprintTemplate(storedTemplate);

            double score =
                    new FingerprintMatcher(probe).match(candidate);

            return Optional.of(score);

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}