/**
 * 
 */
package com.flicklib.folderscanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zsombor
 *
 */
public class MockAsyncMonitor implements AsyncMonitor {

    final static Logger LOG = LoggerFactory.getLogger(MockAsyncMonitor.class);
    /**
     * 
     */
    public MockAsyncMonitor() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.flicklib.folderscanner.AsyncMonitor#start()
     */
    @Override
    public void start() {
        LOG.info("start");
    }

    /* (non-Javadoc)
     * @see com.flicklib.folderscanner.AsyncMonitor#step(java.lang.String)
     */
    @Override
    public void step(String text) {
        LOG.info("step:"+text);
    }

    /* (non-Javadoc)
     * @see com.flicklib.folderscanner.AsyncMonitor#finish()
     */
    @Override
    public void finish() {
        LOG.info("finish");
    }

    /* (non-Javadoc)
     * @see com.flicklib.folderscanner.AsyncMonitor#isCanceled()
     */
    @Override
    public boolean isCanceled() {
        // TODO Auto-generated method stub
        return false;
    }

}
