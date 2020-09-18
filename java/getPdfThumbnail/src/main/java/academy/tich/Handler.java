package academy.tich;

import java.io.IOException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.S3Object;

public class Handler implements RequestHandler<S3Event, String> {

  private final String PDF_TYPE = (String) "application/pdf";

  public String handleRequest(S3Event s3event, Context context) {
    try {

      S3EventNotificationRecord record = s3event.getRecords().get(0);

      String srcBucket = record.getS3().getBucket().getName();
      String srcKey = record.getS3().getObject().getUrlDecodedKey();

      S3Management s3Management = new S3Management(srcBucket, srcKey);

      S3Object s3FileObject = s3Management.getObject();

      System.out.println(s3FileObject.getKey() + " has been gotten and its type is "
          + s3FileObject.getObjectMetadata().getContentType());

      if (s3FileObject.getObjectMetadata().getContentType().equals(PDF_TYPE)) {

        try {

          Thumbnail thumbnail = new Thumbnail();

          thumbnail.setImageFromPdfFile(s3FileObject.getObjectContent(), 0);
          System.out.println("A thumbnail has been generated: " + thumbnail.toString());
          s3Management.putThumbnail(thumbnail);
          System.out.println("A thumbnail has been saved on S3");
        } catch (IOException e) {
          e.printStackTrace();
          // throw new RuntimeException(e);
          return e.getMessage();
        }

      }
      return "SUCCESS";

    } catch (Exception e) {
      e.printStackTrace();
      // throw new RuntimeException(e);
      return e.getMessage();
    }
  }
}
