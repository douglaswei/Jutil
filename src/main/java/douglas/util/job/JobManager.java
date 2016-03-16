package douglas.util.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Author:  wgz
 * Date:    16/2/19
 * Time:    下午1:47
 * Desc:
 */
public class JobManager {
    private static Logger LOG = LoggerFactory.getLogger(JobManager.class);

    public static int DFT_QUEUE_SIZE = 10000;
    public static int MAX_END_NODE_NUM = 100;

    public class BasicJob extends Thread {

        private List<BasicTask> tasks = null;
        private int threadNum = 0;
        private BlockingQueue inputQueue;
        private BlockingQueue outputQueue;

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

        public <T extends BasicTask> BasicJob(Class<T> clz, int threadNum, BlockingQueue inputQueue, BlockingQueue outputQueue) throws IllegalAccessException, InstantiationException {
            this.threadNum = threadNum;
            this.inputQueue = inputQueue;
            this.outputQueue = outputQueue;
            tasks = new ArrayList<>(threadNum);
            for (int idx = 0; idx < threadNum; ++idx) {
                T task = clz.newInstance();
                task.setInputQueue(inputQueue);
                task.setOutputQueue(outputQueue);
                tasks.add(task);
            }
        }

        @Override
        public void run() {
            for (BasicTask task : tasks) {
                task.start();
            }

            for (BasicTask task : tasks) {
                try {
                    task.join();
                } catch (InterruptedException e) {
                    LOG.warn(e.toString());
                }
            }

            if (outputQueue != null) {
                for (int idx = 0; idx < MAX_END_NODE_NUM; ++idx) {
                    try {
                        outputQueue.put(new EndNode());
                    } catch (InterruptedException e) {
                        LOG.warn("exception", e);
                    }
                }
            }
        }
    }

    private List<BasicJob> jobList = new ArrayList<>();

    public <T extends BasicTask> void addJob(Class<T> clz, int threadNum) throws InstantiationException, IllegalAccessException {
        BlockingQueue inputQueue = null;
        BlockingQueue outputQueue = new ArrayBlockingQueue(DFT_QUEUE_SIZE);
        if (jobList.size() != 0) {
            inputQueue = jobList.get(jobList.size() - 1).getOutputQueue();
        }

        jobList.add(new BasicJob(clz, threadNum, inputQueue, outputQueue));
    }

    public void start() throws InterruptedException {
        for (BasicJob job : jobList) {
            job.start();
        }

        for (BasicJob job : jobList) {
            job.join();
        }
    }
}