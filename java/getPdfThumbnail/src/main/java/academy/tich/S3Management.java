package academy.tich;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;

/**
 * S3Management.
 */
public class S3Management {

  private AmazonS3 s3Client;

  public S3Management() {
    this.s3Client = AmazonS3ClientBuilder.defaultClient();
  }

  public S3Object getObject(String srcBucket, String srcKey) {
    return s3Client.getObject(new GetObjectRequest(srcBucket, srcKey));
  }

}