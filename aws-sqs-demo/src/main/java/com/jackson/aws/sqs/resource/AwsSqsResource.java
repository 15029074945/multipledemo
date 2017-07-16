package com.jackson.aws.sqs.resource;

import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.jackson.aws.sqs.service.AwsSQSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jackson on 2017/7/16.
 */
@RestController
@RequestMapping(value = "/api/aws/sqs")
public class AwsSqsResource {

    @Autowired
    AwsSQSService awsSQSService;

    @RequestMapping(value = "queue",method = RequestMethod.POST)
    public void createSqsQueue() {
        CreateQueueResult createQueueResult = awsSQSService.createAwsQueue("jackson-sqs-test");
        System.out.println(createQueueResult.getQueueUrl());
    }
}
