import com.liferay.convert.tools.migrate.ReplacementLiferayScheme;
import com.liferay.convert.tools.util.PrintLoggerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Albert Gomes Cabral
 */
@Testable
public class ReplacementLiferaySchemeTest {

    @BeforeAll
    public static void loadTemplates() {
       PrintLoggerUtil.printInfo(
              "Initializing tests to " +
                       ReplacementLiferayScheme.class.getName());
    }

    @Test
    public void testReplacementSchemeCase() throws Exception {
        ReplacementLiferayScheme replacementLiferayScheme =
                new ReplacementLiferayScheme();

        replacementLiferayScheme.replacement(
                _SOURCE_LIFERAY_SCHEME_SQL, _TARGET_LIFERAY_SCHEME_SQL,
                _NEW_CUSTOMER_SCHEME_OUT_PUT_SQL);

        boolean replace = true;

        if (replace) {
            List<String> contentList =
                    _getInputStreamTestFiles(
                            _NEW_CUSTOMER_SCHEME_OUT_PUT_SQL,
                            _EXPECTED_CUSTOMER_SCHEME_OUT_PUT_SQL);

            if (!contentList.isEmpty()) {
                Assertions.assertEquals(contentList.get(0), contentList.get(1),
                        "test testReplacementSchemeCase passed.");
            }
            else {
                Consumer consumer = (exception) -> {
                    if (exception instanceof Exception) {
                        try {
                            throw new Exception(String.valueOf(exception));
                        }
                        catch (Exception runtimeException) {
                            throw new RuntimeException(runtimeException);
                        }
                    }
                };
            }
        }
        else {
            Assertions.assertThrows(Exception.class, () -> {
                System.out.println("test testReplacementSchemeCase fail");
            });
        }
    }

    private List<String> _getInputStreamTestFiles(
            String actual, String expected) throws Exception {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        InputStream sourceInputStream = classLoader.getResourceAsStream(actual);

        InputStream targetInputStream = classLoader.getResourceAsStream(expected);

        return new ArrayList<>();
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
