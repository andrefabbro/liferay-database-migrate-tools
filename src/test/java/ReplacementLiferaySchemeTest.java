import com.liferay.convert.tools.migrate.ReplacementLiferayScheme;
import com.liferay.convert.tools.util.PrintLoggerUtil;
import com.liferay.convert.tools.util.ResultsThreadLocal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.List;
import java.util.Map;

/**
 * @author Albert Gomes Cabral
 */
@Testable
public class ReplacementLiferaySchemeTest extends ReplacementLiferayScheme {

    @BeforeAll
    public static void loadTemplates() {
       PrintLoggerUtil.printInfo(
              "Initializing tests to " +
                       ReplacementLiferayScheme.class.getName());
    }

    @Test
    public void testLoadingFilesCase() throws Exception {

        ReplacementLiferayScheme replacementLiferayScheme =
                new ReplacementLiferayScheme();

        replacementLiferayScheme.replacement(
                _SOURCE_LIFERAY_SCHEME_SQL, _TARGET_LIFERAY_SCHEME_SQL,
                _NEW_CUSTOMER_SCHEME_OUT_PUT_SQL);

        if (ResultsThreadLocal.getResultsThreadLocal()) {
            List<Map<String, String>> contentList =
                    _getContentsFromFiles(
                            _NEW_CUSTOMER_SCHEME_OUT_PUT_SQL,
                            _EXPECTED_CUSTOMER_SCHEME_OUT_PUT_SQL);

            Assertions.assertEquals(contentList.get(0), contentList.get(1));
        }
        else {
             PrintLoggerUtil.printError(
                     "test testLoadingFilesCase fail", null);
        }

    }

    private List<Map<String, String>> _getContentsFromFiles(
            String newFileOutput, String expectedFileOutput) {
        return null;

    }

    private static final String _EXPECTED_CUSTOMER_SCHEME_OUT_PUT_SQL =
            "expected-customer-scheme-out-put.sql";
    private static final String _NEW_CUSTOMER_SCHEME_OUT_PUT_SQL =
            "new-customer-scheme-out-put.sql";
    private static final String _SOURCE_LIFERAY_SCHEME_SQL =
            "source-liferay-scheme.sql";
    private static final String _TARGET_LIFERAY_SCHEME_SQL =
            "target-customer-scheme.sql";

}
