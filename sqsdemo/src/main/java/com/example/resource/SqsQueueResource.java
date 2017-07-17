package com.example.resource;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.example.domain.SqsQueueListener;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jackson on 2017/7/17.
 */
@RestController
@RequestMapping(value = "/api/aws")
public class SqsQueueResource {
  
  private final static String queueUrl ="https://sqs.us-east-2.amazonaws.com/486517773544/MyQueue1500128534912";
  
  @Autowired
  AmazonSQS amazonSQS;
  
  @Autowired
  SqsQueueListener sqsQueueListener;
  
  @RequestMapping(value = "myQueue", method = RequestMethod.GET)
  public String getQueueUrl() {
    ListQueuesResult lq_result = amazonSQS.listQueues();
    System.out.println("Your SQS Queue URLs:");
    for (String url : lq_result.getQueueUrls()) {
      System.out.println(url);
    }
    return "-----";
  }
  
  @RequestMapping(value = "send",method = RequestMethod.GET)
  public String sendMessage() {
    System.out.println(sqsQueueListener.test()+"d");
    final String message = UUID.randomUUID().toString();
    SendMessageResult r = amazonSQS.sendMessage(queueUrl, message);
    System.out.println("[message] : "+message+" [messageId] : "+r.getMessageId()+" [sequenceNumber] : "+r.getSequenceNumber());
    return "send";
  }
  
  @RequestMapping(value = "receive",method = RequestMethod.GET)
  public String receiveMessage() {
    ReceiveMessageResult r = amazonSQS.receiveMessage(queueUrl);
    System.out.println(r);
    for (Message m : r.getMessages()) {
      System.out.println(" [messageBody] : "+m.getBody()+"  [messageId] :"+m.getMessageId());
    }
    return "dd";
  }
}
