package com.liferay.convert.tools.migrate;

/**
 * @author Albert Gomes Cabral
 */
public abstract class BaseReplacement {

    protected abstract void replacement(
            String sourceFileName, String targetFileName, String newFileName)
        throws Exception;

}
