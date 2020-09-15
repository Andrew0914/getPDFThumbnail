package academy.tich;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;

public class Handler implements RequestHandler<S3Event, String> {

  // private final String PDF_TYPE = (String) "application/pdf";

  public String handleRequest(S3Event s3event, Context context) {
    try {
      S3EventNotificationRecord record = s3event.getRecords().get(0);

      String srcBucket = record.getS3().getBucket().getName();
      String srcKey = record.getS3().getObject().getUrlDecodedKey();

      // String dstBucket = srcBucket + "-resized";
      // String dstKey = "resized-" + srcKey;

      S3Management s3Management = new S3Management();

      System.out.println(s3Management.getObject(srcBucket, srcKey).getObjectMetadata().getContentType());

      return "SUCCESS";

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
