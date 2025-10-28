package br.edu.ifpe.pontoif.pontoif.service.match;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import org.springframework.stereotype.Service;

@Service
public class SourceAfisMatchService {

    public double calculateScore(byte[] storedTemplate, byte[] sampleTemplate) {
        try {
            FingerprintTemplate probe = new FingerprintTemplate(storedTemplate);
            FingerprintTemplate candidate = new FingerprintTemplate(sampleTemplate);
            double score = new FingerprintMatcher(probe).match(candidate);
            return score;

        } catch (Exception e) {
            System.err.println("Error in score calculation:" + e.getMessage());
            e.printStackTrace();
            return 0.0;
        }
    }
}
