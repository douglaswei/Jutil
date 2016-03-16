package douglas.util.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

/**
 * Author:  wgz
 * Date:    16/2/19
 * Time:    下午1:54
 * Desc:
 */
public abstract class BasicTask extends Thread {

    protected Logger LOG = LoggerFactory.getLogger(BasicTask.class);

    protected BlockingQueue inputQueue;

    protected BlockingQueue outputQueue;

    public BlockingQueue getInputQueue() {
        return inputQueue;
    }

    public void setInputQueue(BlockingQueue inputQueue) {
        this.inputQueue = inputQueue;
    }

    public BlockingQueue getOutputQueue() {
        return outputQueue;
    }

    public void setOutputQueue(BlockingQueue outputQueue) {
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        while (true) {
            Object o = null;
            try {
                o = inputQueue.take();
            } catch (InterruptedException e) {
                LOG.warn("exception [{}]", e);
            }
            if (o instanceof EndNode) {
                return;
            }
            try {
                process(o);
            } catch (Exception e) {
                LOG.warn(e.toString());
            }
        }
    }

    protected abstract void process (Object o) throws Exception;
}
