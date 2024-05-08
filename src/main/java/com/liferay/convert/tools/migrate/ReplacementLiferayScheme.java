package com.liferay.convert.tools.migrate;

import com.liferay.convert.tools.exception.ReplacementException;
import com.liferay.convert.tools.exception.SQLFilesException;
import com.liferay.convert.tools.util.PrintLoggerUtil;
import com.liferay.convert.tools.util.ResultsThreadLocal;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Albert Gomes Cabral
 */
public class ReplacementLiferayScheme extends BaseReplacement {

    @Override
    public void replacement(
            String sourceFileName, String targetFileName, String newFileName)
        throws Exception {

        try {
            List<Map<String, String>> contentMapList =
                    _getInputStreamListByFileName(sourceFileName, targetFileName);

            if (contentMapList != null && contentMapList.size() == 2) {

                String sourceContent = contentMapList.get(0).get("source.key");
                String targetContent = contentMapList.get(1).get("target.key");

                if (sourceContent != null && targetContent != null) {

                    Pattern[] patternsArray = new Pattern[] {
                            _CREATE_TABLE_GROUP_ID_FIELD_PATTERN,
                            _ALTER_TABLE_DISABLE_AS_COMMENT_PATTER,
                            _ALTER_TABLE_ENABLE_AS_COMMENT_PATTER,
                            _DROP_TABLE_PATTERN, _CREATE_TABLE_PATTERN,
                            _LOCK_TABLES_PATTERN, _INSERT_INTO_PATTERN,
                            _ALTER_TABLE_DISABLE_AS_COMMENT_GROUP_ID_FIELD_PATTERN,
                            _ALTER_TABLE_ENABLE_AS_COMMENT_GROUP_ID_FIELD_PATTERN,
                            _DROP_TABLE_GROUP_ID_FIELD_PATTERN,
                            _LOCK_TABLE_GROUP_ID_FIELD_PATTERN,
                            _INSERT_TABLE_GROUP_ID_FIELD_PATTERN
                    };

                    for (Pattern pattern : patternsArray) {
                        targetContent = replaceContextPattern(
                                sourceContent, targetContent, pattern);
                    }

                    // Method to create output file and add on thread to be get in another class.

                    _createSQLFileOutput(newFileName, targetContent);

                }
                else {
                    throw new ReplacementException(
                            "Source or Target files produced null pointer.");
                }
            }
            else {
                throw new Exception("Map content list is invalid");
            }
        }
        catch (Exception exception) {
            throw new Exception(
                    "Unable to replace contents ", exception);
        }

    }

    private Map<String, String> _buildMapItem(String key, String value) {
        Map<String, String> itemMap = new HashMap<>();

        itemMap.put(key, value);

        return itemMap;
    }

    private void _createSQLFileOutput(
            String newFileName, String content) throws IOException {

        String resourceDirectory = System.getProperty("user.dir") +
                _RESOURCE_DIRECTORY;

        String filePath = resourceDirectory + newFileName;

        File file = new File(filePath);

        try (FileWriter writer = new FileWriter(file)) {
            try {
                if (file.exists()) {
                    writer.write(content);

                    ResultsThreadLocal.setResultsThreadLocal(true);

                    PrintLoggerUtil.printInfo("The " + file.getName() +
                            " was create with success.");
                }
                else {
                    throw new IOException(
                            "File with the name " + newFileName +
                                    " already exists.");
                }
            }
            catch (Exception exception) {
                throw new IOException(
                        "Unable to create SQL output file " +
                                exception.getCause());
            }
            finally {
                writer.flush();

                writer.close();
            }
        }
        catch (IOException ioException) {
            throw new IOException(ioException);
        }

    }

    private String _getContentByInputStream(InputStream inputStream) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream))) {

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            return stringBuilder.toString();
        }
    }

    private List<Map<String, String>> _getInputStreamListByFileName(
            String sourceFileName, String targetFileName) throws SQLFilesException {

        try {
            if (!sourceFileName.endsWith(".sql") && targetFileName.endsWith(".sql")) {
                throw new SQLFilesException("Extension file must be .sql");
            }

            Thread thread = Thread.currentThread();

            ClassLoader classLoader = thread.getContextClassLoader();

            InputStream sourceInputStream =
                    classLoader.getResourceAsStream(sourceFileName);

            InputStream targetInputStream =
                    classLoader.getResourceAsStream(targetFileName);

            if (sourceInputStream == null) {
                throw new SQLFilesException(
                        "Source file with the name " + sourceFileName +
                                " not found.");
            }
            else if (targetInputStream == null) {
                throw new SQLFilesException(
                        "Target file with the name " + targetFileName +
                                " not found.");
            }
            else {
                String source = _getContentByInputStream(sourceInputStream);
                String target = _getContentByInputStream(targetInputStream);

                if (source.isEmpty() && target.isEmpty() ||
                        source.isBlank() && target.isBlank()) {

                    PrintLoggerUtil.printError(
                            "Cannot convert input stream to string!" +
                                    " Invalid input file.", null);

                    return null;
                }

                List<Map<String, String>> contentMapList = new ArrayList<>(2);

                contentMapList.add(_buildMapItem("source.key", source));
                contentMapList.add(_buildMapItem("target.key", target));

                return contentMapList;
            }
        }
        catch (Exception exception) {
            throw new SQLFilesException("Unable to load files ", exception);
        }

    }

    protected String replaceContextPattern(
            String sourceContent, String targetContent, Pattern pattern)
        throws ReplacementException {

        try {
            String patternDefinition = pattern.toString();

            if (Objects.equals(pattern, _INSERT_INTO_PATTERN)) {

                Matcher matcherTarget = pattern.matcher(targetContent);

                while (matcherTarget.find()) {
                    Matcher matcherSource =
                            _CREATE_TABLE_PATTERN.matcher(sourceContent);

                    while (matcherSource.find()) {
                        if (Objects.equals(
                                matcherTarget.group(1),
                                matcherSource.group(1).toLowerCase())) {

                            targetContent = targetContent.replace(
                                    matcherTarget.group(1), matcherSource.group(1));

                            PrintLoggerUtil.printReplacement(
                                    matcherTarget.group(1), matcherSource.group(1),
                                    pattern);
                        }
                    }
                }

                return targetContent;

            }
            else if (patternDefinition.contains("(([A-Za-z]+)(_[a-zA-Z]+_)([0-9]+))")) {

                Matcher matcherSource = pattern.matcher(sourceContent);

                while (matcherSource.find()) {
                    Matcher matcherTarget = pattern.matcher(targetContent);

                    while (matcherTarget.find()) {

                        if (Objects.equals(
                                matcherSource.group(2).toLowerCase(),
                                matcherTarget.group(2))) {

                            String camelCaseName = matcherSource.group(2);
                            String groupId = matcherTarget.group(4);

                            String camelCaseConcatGroupId =
                                    camelCaseName + matcherSource.group(3) + groupId;

                            if (Objects.equals(pattern, _CREATE_TABLE_GROUP_ID_FIELD_PATTERN)) {

                                // Replace name concat group id

                                targetContent = targetContent.replace(
                                        matcherTarget.group(1), camelCaseConcatGroupId);

                                PrintLoggerUtil.printReplacement(
                                        matcherTarget.group(1), camelCaseConcatGroupId,
                                        pattern);

                                // Replace table definitions

                                targetContent = targetContent.replace(
                                        matcherTarget.group(5), matcherSource.group(5));

                                PrintLoggerUtil.printReplacement(
                                        matcherTarget.group(5), matcherSource.group(5),
                                        pattern);

                            }
                            else {
                                targetContent = targetContent.replace(
                                        matcherTarget.group(1), camelCaseConcatGroupId);

                                PrintLoggerUtil.printReplacement(
                                        matcherTarget.group(1), camelCaseConcatGroupId,
                                        pattern);
                            }
                        }
                    }
                }

                return targetContent;

            }
            else {

                Matcher matcherSource = pattern.matcher(sourceContent);

                while (matcherSource.find()) {
                    Matcher matcherTarget = pattern.matcher(targetContent);

                    while (matcherTarget.find()) {
                        if (Objects.equals(
                                matcherSource.group(1).toLowerCase(),
                                matcherTarget.group(1))) {

                            targetContent = targetContent.replace(
                                    matcherTarget.group(), matcherSource.group());

                            PrintLoggerUtil.printReplacement(matcherTarget.group(),
                                    matcherSource.group(), pattern);
                        }
                    }
                }

                return targetContent;

            }
        }
        catch (Exception exception) {
            throw new ReplacementException(
                    "Unable to process " + pattern.pattern(),
                            exception);
        }

    }
    
    // Patterns variables

    private static final Pattern _ALTER_TABLE_DISABLE_AS_COMMENT_PATTER = Pattern.compile(
            "/*!\\w+\\s+ALTER\\s+TABLE\\s+(`[^`]+`)\\s+DISABLE\\s+KEYS\\s+\\*/\\s*?;");

    private static final Pattern _ALTER_TABLE_ENABLE_AS_COMMENT_PATTER = Pattern.compile(
            "/*!\\w+\\s+ALTER\\s+TABLE\\s+(`[^`]+`)\\s+ENABLE\\s+KEYS\\s+\\*/\\s*?;");

    private static final Pattern _CREATE_TABLE_PATTERN = Pattern.compile(
            "CREATE\\s+TABLE\\s+(`[^`]+`)\\s*\\((?:[^)(]+|\\([^)(]*\\))*\\)\\s*" +
                    "ENGINE=InnoDB\\s*DEFAULT\\s*CHARSET=utf8mb4\\s*COLLATE=utf8mb4_unicode_ci;");

    private static final Pattern _DROP_TABLE_PATTERN = Pattern.compile(
            "DROP\\s+TABLE\\s+IF\\s+EXISTS\\s+(`[^`]+`);");

    private static final Pattern _LOCK_TABLES_PATTERN = Pattern.compile(
            "LOCK\\s+TABLES\\s+(`[^`]+`)\\s+WRITE;");

    private static final Pattern _INSERT_INTO_PATTERN = Pattern.compile(
            "INSERT\\s+INTO\\s+(`[^`]+`)\\s+VALUES\\s+\\(");

    // Concat with group id patterns variables

    private static final Pattern _ALTER_TABLE_DISABLE_AS_COMMENT_GROUP_ID_FIELD_PATTERN =
            Pattern.compile("\\*!\\w+\\s+ALTER\\s+TABLE\\s+`(([A-Za-z]+)(_[a-zA-Z]+_)([0-9]+))`" +
                    "\\s+DISABLE\\s+KEYS\\s+\\*");

    private static final Pattern _ALTER_TABLE_ENABLE_AS_COMMENT_GROUP_ID_FIELD_PATTERN =
            Pattern.compile("\\*!\\w+\\s+ALTER\\s+TABLE\\s+`(([A-Za-z]+)(_[a-zA-Z]+_)([0-9]+))`" +
                    "\\s+ENABLE\\s+KEYS\\s+\\*");

    private static final Pattern _CREATE_TABLE_GROUP_ID_FIELD_PATTERN = Pattern.compile(
            "CREATE\\s+TABLE\\s+`(([A-Za-z]+)(_[a-zA-Z]+_)([0-9]+))`\\s*(\\((?:[^)(]+" +
                    "|\\([^)(]*\\))*\\))\\s*ENGINE=InnoDB\\s*DEFAULT\\s*CHARSET=utf8mb4" +
                    "\\s*COLLATE=utf8mb4_unicode_ci;");

    private static final Pattern _DROP_TABLE_GROUP_ID_FIELD_PATTERN = Pattern.compile(
            "DROP\\s+TABLE\\s+IF\\s+EXISTS\\s+`(([A-Za-z]+)(_[a-zA-Z]+_)([0-9]+))`");

    private static final Pattern _LOCK_TABLE_GROUP_ID_FIELD_PATTERN = Pattern.compile(
            "LOCK\\s+TABLES\\s+`([A-Za-z]+)_[a-zA-Z]+_([0-9]+)`\\s+WRITE;");

    private static final Pattern _INSERT_TABLE_GROUP_ID_FIELD_PATTERN = Pattern.compile(
            "INSERT\\s+INTO\\s+`(([A-Za-z]+)(_[a-zA-Z]+_)([0-9]+))`\\s+VALUES\\s+\\(`");

    // Utilities variables

    private static final String _RESOURCE_DIRECTORY = "/src/main/resources/";

}
