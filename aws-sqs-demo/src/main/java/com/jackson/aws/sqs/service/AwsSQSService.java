package com.jackson.aws.sqs.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jackson on 2017/7/16.
 */
@Service
public class AwsSQSService {

    @Autowired
    private AmazonSQS amazonSQS;

    public CreateQueueResult createAwsQueue(String queueName) {
        // Creating a Queue
        CreateQueueResult createQueueResult = null;
        try {
            CreateQueueRequest create_request = new CreateQueueRequest(queueName)
                    .addAttributesEntry("DelaySeconds", "60")
                    .addAttributesEntry("MessageRetentionPeriod", "86400");
            createQueueResult = amazonSQS.createQueue(create_request);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }
        return createQueueResult;
    }

    public DeleteQueueResult deleteQueue(String queueName) {
        String queueUrl = amazonSQS.getQueueUrl(queueName).getQueueUrl();
        return  amazonSQS.deleteQueue(queueUrl);
    }

    public ListQueuesResult listQueuesResult() {
        ListQueuesResult listQueuesResult = amazonSQS.listQueues();
        System.out.println("Your SQS Queue URLs:");
        for (String url : listQueuesResult.getQueueUrls()) {
            System.out.println(url);
        }
        return listQueuesResult;
    }
}
