//package br.edu.ifpe.pontoif.pontoif.service.futronic;
//
//import com.futronic.SDKHelper.FtrIdentifyRecord;
//import com.futronic.SDKHelper.FtrIdentifyResult;
//import com.futronic.SDKHelper.FutronicException;
//import com.futronic.SDKHelper.FutronicIdentification;
//import com.futronic.SDKHelper.FutronicSdkBase;
//
//public class FutronicMatchServiceLinux {
//
//    static {
//        // Carrega APENAS em Linux
//        String os = System.getProperty("os.name").toLowerCase();
//        if (!os.contains("linux")) {
//            throw new RuntimeException("Esta build é apenas para Linux. OS detectado: " + os);
//        }
//        try {
//            // nomes SEM 'lib' e SEM '.so'
//            System.loadLibrary("ftrapi");   // -> libftrapi.so
//            System.loadLibrary("ScanAPI");  // -> libScanAPI.so
//            System.out.println("✅ libs Futronic (Linux) carregadas.");
//        } catch (UnsatisfiedLinkError e) {
//            throw new RuntimeException(
//                    "Não consegui carregar as libs nativas da Futronic. " +
//                            "Verifique LD_LIBRARY_PATH ou -Djava.library.path. Detalhe: " + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * Verificação 1:1 — compara dois templates (sem sensor).
//     */
//    public boolean verify(byte[] enrolledTemplate, byte[] capturedTemplate) {
//        FutronicIdentification identification;
//        try {
//            identification = new FutronicIdentification(); // non-interactive
//        } catch (FutronicException e) {
//            throw new RuntimeException("Erro ao inicializar FutronicIdentification", e);
//        }
//
//        identification.setFakeDetection(false);
//        identification.setFARN(1); // mais rigoroso (ajuste conforme seu FAR/FRR)
//
//        try {
//            // vetor com 1 template "cadastrado"
//            FtrIdentifyRecord[] records = new FtrIdentifyRecord[1];
//            records[0] = new FtrIdentifyRecord(enrolledTemplate, 0);
//
//            FtrIdentifyResult result = new FtrIdentifyResult();
//            int ret = identification.Identification(records, result);
//
//            return (ret == FutronicSdkBase.RETCODE_OK && result.m_Index >= 0);
//        } finally {
//            identification.Dispose();
//        }
//    }
//
//    /**
//     * Identificação 1:N — procura o capturedTemplate em um conjunto de templates cadastrados.
//     * Retorna o índice do match ou -1 se não houver correspondência.
//     */
//    public int identify(byte[] capturedTemplate, java.util.List<byte[]> registeredTemplates) {
//        FutronicIdentification identification;
//        try {
//            identification = new FutronicIdentification(); // non-interactive
//        } catch (FutronicException e) {
//            throw new RuntimeException("Erro ao inicializar FutronicIdentification", e);
//        }
//
//        identification.setFakeDetection(false);
//        identification.setFARN(1);
//
//        try {
//            FtrIdentifyRecord[] records = new FtrIdentifyRecord[registeredTemplates.size()];
//            for (int i = 0; i < registeredTemplates.size(); i++) {
//                records[i] = new FtrIdentifyRecord(registeredTemplates.get(i), i);
//            }
//
//            FtrIdentifyResult result = new FtrIdentifyResult();
//            int ret = identification.Identification(records, result);
//
//            if (ret == FutronicSdkBase.RETCODE_OK && result.m_Index >= 0) {
//                return result.m_Index;
//            }
//            return -1;
//        } finally {
//            identification.Dispose();
//        }
//    }
//}
